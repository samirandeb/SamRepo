package com.cts.migration.entity.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cts.migration.entity.CustomUser;

public class CustomUserRowMapper implements RowMapper<CustomUser>{

	@Override
	public CustomUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		CustomUser user = new CustomUser();
		user.setUserId(rs.getString(1));
		user.setUsername(rs.getString(2));
		user.setFirstName(rs.getString(3));
		user.setLastName(rs.getString(4));
		user.setEmail(rs.getString(5));
		user.setPassword(rs.getString(6));
		user.setEnabled(rs.getInt(7)>0?true:false);
		return user;
	}

}
