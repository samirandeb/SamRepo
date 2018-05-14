package com.cts.migration.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cts.migration.config.MigrationEnvironmentConfigurator;
import com.cts.migration.entity.CustomUser;
import com.cts.migration.entity.Role;
import com.cts.migration.entity.rowMapper.CustomUserRowMapper;
import com.cts.migration.entity.rowMapper.RoleRowMapper;
import com.cts.migration.model.OmtRegion;

@Repository
public class UserManagementDao {

	@Autowired
	private OipaRegionDao oipaRegionDao;

	@Autowired
	@Qualifier("appJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private MigrationEnvironmentConfigurator configurator;

	private static final Logger log = LoggerFactory.getLogger(UserManagementDao.class);

	private static final String USER_TABLE = "OMTUSER";
	private static final String ROLE_TABLE = "OMTROLE";
	private static final String USER_ROLE_TABLE = "OMTUSERROLE";
	private static final String USER_REGION_TABLE = "OMTUSERREGION";

	private static final String GET_USER_BY_LOGINID = "SELECT * FROM " + USER_TABLE + " WHERE LOGINID = :LOGINID";
	private static final String GET_USER_BY_USERID = "SELECT * FROM " + USER_TABLE + " WHERE USERID = :USERID";
	private static final String GET_ALL_USER = "SELECT * FROM " + USER_TABLE + " WHERE LOGINID != 'superadmin'";
	private static final String CREATE_USER = "INSERT INTO " + USER_TABLE + " (USERID,LOGINID , FIRSTNAME,LASTNAME ,EMAIL ,PASSWORD ,ENABLED) VALUES (NEXT_USER_ID,:LOGINID,:FIRSTNAME,:LASTNAME,:EMAIL,:PASSWORD,:ENABLED)";
	private static final String CREATE_USER_ROLE = "INSERT INTO " + USER_ROLE_TABLE + " VALUES (NEXT_USER_ROLE_ID,:USERID, :ROLEID)";
	private static final String CREATE_USER_REGION = "INSERT INTO " + USER_REGION_TABLE + " VALUES (:ID,:REGIONID, :USERID)";
	private static final String GET_ALL_ROLE = "SELECT * FROM " + ROLE_TABLE;
	private static final String GET_USER_ROLE_BY_USERID = "SELECT RL.* FROM " + USER_ROLE_TABLE + " UR INNER JOIN " + ROLE_TABLE + " RL ON RL.ROLEID = UR.ROLEID WHERE UR.USERID = :USERID ORDER BY RL.ROLENAME";

	private static final String DELETE_ROLE_BY_USERROLE = "DELETE FROM " + USER_ROLE_TABLE + " WHERE USERID = :USERID AND ROLEID = :ROLEID";

	private static final String UPDATE_USER = "UPDATE " + USER_TABLE + " SET FIRSTNAME = :FIRSTNAME, LASTNAME = :LASTNAME, EMAIL = :EMAIL WHERE USERID = :USERID";

	private static final String GET_PASSWORD = "SELECT PASSWORD FROM " + USER_TABLE + " WHERE USERID = :USERID";
	private static final String UPDATE_PASSWORD = "UPDATE "+USER_TABLE+" SET PASSWORD = :NEWPASSWORD WHERE USERID = :USERID";


	public List<CustomUser> getAllUser() {
		List<CustomUser> users = jdbcTemplate.query(GET_ALL_USER, new CustomUserRowMapper());
		if (users != null) {
			for (CustomUser user : users) {
				user.setPassword("");
				SqlParameterSource rolesParam = new MapSqlParameterSource().addValue("USERID", user.getUserId());
				List<Role> roles = jdbcTemplate.query(GET_USER_ROLE_BY_USERID, rolesParam, new RoleRowMapper());
				List<OmtRegion> regions = oipaRegionDao.getAllSource(user.getUserId());
				for (OmtRegion region : regions) {
					user.getSourceRegions().add(region.getIdentifier());
				}
				user.setAuthorities(roles);
			}
		}
		return users;
	}

	public List<Role> getAllRoles() {
		List<Role> role = jdbcTemplate.query(GET_ALL_ROLE, new RoleRowMapper());
		//System.out.println("ROLES FETCHED : " + role.size());
		return role;
	}

	public CustomUser loadUserByUsername(String username) {
		// Write your DB call code to get the user details from DB,But I am just
		// hard coding the user
		//log.info("Get user detail for user {}", username);
		CustomUser user = null;
		List<CustomUser> users = null;
		SqlParameterSource userParam = new MapSqlParameterSource().addValue("LOGINID", username);
		users = jdbcTemplate.query(GET_USER_BY_LOGINID, userParam, new CustomUserRowMapper());
		//log.info("No of users returned : " + users.size());
		if (users != null && users.size() > 0) {
			user = users.get(0);
			//log.info("User id : " + user.getUserId());
			SqlParameterSource rolesParam = new MapSqlParameterSource().addValue("USERID", user.getUserId());
			List<Role> roles = jdbcTemplate.query(GET_USER_ROLE_BY_USERID, rolesParam, new RoleRowMapper());
			user.setAuthorities(roles);
			//log.info("Roles associated with user : " + user.getAuthorities());
			List<OmtRegion> regions = oipaRegionDao.getAllSource(user.getUserId());
			for (OmtRegion region : regions) {
				user.getSourceRegions().add(region.getIdentifier());
			}
		}

		//System.out.println("USER : " + user);
		return user;
	}

	@Transactional("appTransactionManager")
	public void updateUser(CustomUser user) {
		SqlParameterSource userParam = new MapSqlParameterSource().addValue("USERID", user.getUserId()).addValue("FIRSTNAME", user.getFirstName()).addValue("LASTNAME", user.getLastName()).addValue("EMAIL", user.getEmail());
		jdbcTemplate.update(UPDATE_USER, userParam);

		List<Role> newRoles = user.getAuthorities();
		SqlParameterSource rolesParam = new MapSqlParameterSource().addValue("USERID", user.getUserId());
		List<Role> existingRoles = jdbcTemplate.query(GET_USER_ROLE_BY_USERID, rolesParam, new RoleRowMapper());

		// delete all the non existing roles
		for (Role role : existingRoles) {
			if (newRoles.contains(role)) {
				newRoles.remove(role);
			} else {
				rolesParam = new MapSqlParameterSource().addValue("USERID", user.getUserId()).addValue("ROLEID", role.getRoleId());
				//System.out.println("ROLES TO BE DELETED : " + role.getName());
				jdbcTemplate.update(DELETE_ROLE_BY_USERROLE, rolesParam);
			}
		}

		// add new Roles
		for (Role role : newRoles) {
			rolesParam = new MapSqlParameterSource().addValue("USERID", user.getUserId()).addValue("ROLEID", role.getRoleId());
			//System.out.println("ROLES TO BE ADDED : " + role.getRoleId());
			jdbcTemplate.update(CREATE_USER_ROLE, rolesParam);
		}
		// update regions
		updateRegions(user.getSourceRegions(), user.getUserId());
	}

	@Transactional("appTransactionManager")
	public CustomUser createUser(CustomUser user) {
		// (LOGINID , FIRSTNAME,LASTNAME ,EMAIL ,PASSWORD ,ENABLED)

		//log.info("CREATE USER CALLED : ");
		List<Role> roles = user.getAuthorities();
		List<String> sourceRegions = user.getSourceRegions();

		//log.info("Role Id : " + roles);
		SqlParameterSource userParam = new MapSqlParameterSource().addValue("LOGINID", user.getUsername()).addValue("FIRSTNAME", user.getFirstName()).addValue("LASTNAME", user.getLastName()).addValue("EMAIL", user.getEmail()).addValue("PASSWORD", user.getPassword()).addValue("ENABLED", user.isEnabled() ? 1 : 0);
		jdbcTemplate.update(CREATE_USER, userParam);

		user = loadUserByUsername(user.getUsername());
		//log.info("Newly created user id : " + user.getUserId());
		for (Role role : roles) {
			SqlParameterSource roleParam = new MapSqlParameterSource().addValue("USERID", user.getUserId()).addValue("ROLEID", role.getRoleId());
			jdbcTemplate.update(CREATE_USER_ROLE, roleParam);

		}

		// update the region
		updateRegions(sourceRegions, user.getUserId());

		//log.info("USER HAS BEEN CREATED : ");
		return user;
	}

	@Transactional("appTransactionManager")
	private void updateRegions(List<String> regions, String userId) {
//		if (regions != null) {
			SqlParameterSource paramMap = new MapSqlParameterSource().addValue("USERID", userId);
			jdbcTemplate.update("DELETE FROM " + USER_REGION_TABLE + " WHERE USERID = :USERID", paramMap);

			for (String region : regions) {
				paramMap = new MapSqlParameterSource().addValue("USERID", userId).addValue("REGIONID", configurator.getRegionMap().get(region)).addValue("ID", UUID.randomUUID().toString());
				jdbcTemplate.update(CREATE_USER_REGION, paramMap);

			}
//		}

	}

	@Transactional("appTransactionManager")
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		if (!"".equals(userId) && null != userId) {
			SqlParameterSource paramMap = new MapSqlParameterSource().addValue("USERID", userId);
			jdbcTemplate.update("DELETE FROM OMTUSER WHERE USERID = :USERID", paramMap);
			jdbcTemplate.update("DELETE FROM OMTUSERROLE WHERE USERID = :USERID", paramMap);
			jdbcTemplate.update("DELETE FROM OMTUSERREGION WHERE USERID = :USERID", paramMap);
		} else {
			throw new RuntimeException("User id can not be null or empty");
		}

	}

	public CustomUser loadUserByUserId(String userId) {
		//log.info("Get user detail for user {}", userId);
		CustomUser user = null;
		List<CustomUser> users = null;
		SqlParameterSource userParam = new MapSqlParameterSource().addValue("USERID", userId);
		users = jdbcTemplate.query(GET_USER_BY_USERID, userParam, new CustomUserRowMapper());
		//log.info("No of users returned : " + users.size());
		if (users != null && users.size() > 0) {
			user = users.get(0);
			//log.info("User id : " + user.getUserId());
			SqlParameterSource rolesParam = new MapSqlParameterSource().addValue("USERID", user.getUserId());
			List<Role> roles = jdbcTemplate.query(GET_USER_ROLE_BY_USERID, rolesParam, new RoleRowMapper());
			user.setAuthorities(roles);
			//log.info("Roles associated with user : " + user.getAuthorities());
			List<OmtRegion> regions = oipaRegionDao.getAllSource(user.getUserId());
			for (OmtRegion region : regions) {
				user.getSourceRegions().add(region.getIdentifier());
			}
		}
		//System.out.println("USER : " + user);
		return user;
	}

	//TODO:
		public String getOldPassword(String userId){
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("USERID", userId);
			return jdbcTemplate.queryForObject(GET_PASSWORD, params, String.class);
		}

		public int updatePassword(String userId, String newPass){
			SqlParameterSource userParam = new MapSqlParameterSource()
									.addValue("USERID", userId)
									.addValue("NEWPASSWORD", newPass);
			return jdbcTemplate.update(UPDATE_PASSWORD, userParam);
		}

}
