package com.cts.migration.entity.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cts.migration.entity.Role;

public class RoleRowMapper implements RowMapper<Role>{

	@Override
	public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Role rl = new Role();
		rl.setRoleId(rs.getString(1));
		rl.setName(rs.getString(2));
		rl.setRoleDescription(rs.getString(3));
		return rl;
	}

}
