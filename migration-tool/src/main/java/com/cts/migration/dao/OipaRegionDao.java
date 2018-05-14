package com.cts.migration.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.cts.migration.entity.rowMapper.OmtRegionRowMapper;
import com.cts.migration.model.OmtRegion;

@Repository
public class OipaRegionDao {
	
	@Autowired
	@Qualifier("appJdbcTemplate")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private String GET_ALL_REGION = "Select * from OMTREGION";
	private String GET_ALL_REGION_BY_USER = "select re.* from OMTRegion re inner join OMTUSERREGION usr on USR.REGIONID = re.ID and USR.USERID = :userId";
	private String GET_SANDBOX_REGION_BY_USER = "select re.* from OMTRegion re where re.SANDBOX = 1" ;
	private String GET_ALL_SRC_REGION_BY_USER = "select re.* from OMTRegion re inner join OMTUSERREGION usr on USR.REGIONID = re.ID and USR.USERID = :userId";
	private String GET_ALL_SRC_REGION= "select re.* from OMTRegion re where re.DESTINATION != 1";
	
	public List<OmtRegion> getAll(){
		return jdbcTemplate.query(GET_ALL_REGION, new OmtRegionRowMapper());
	}
	
	public List<OmtRegion> getAllSourceRegion(){
		return jdbcTemplate.query(GET_ALL_SRC_REGION, new OmtRegionRowMapper());
	}
	
	public List<OmtRegion> getAll(String userId){
		SqlParameterSource rolesParam = new MapSqlParameterSource().addValue("userId", userId);
		return jdbcTemplate.query(GET_ALL_REGION_BY_USER,rolesParam,new OmtRegionRowMapper());
	}
	
	public List<OmtRegion> getAllSource(String userId){
		SqlParameterSource rolesParam = new MapSqlParameterSource().addValue("userId", userId);
		return jdbcTemplate.query(GET_ALL_SRC_REGION_BY_USER,rolesParam,new OmtRegionRowMapper());
	}
	
	public OmtRegion getSandboxRegion(String userId){
		return jdbcTemplate.query(GET_SANDBOX_REGION_BY_USER,new OmtRegionRowMapper()).get(0);
	}

}
