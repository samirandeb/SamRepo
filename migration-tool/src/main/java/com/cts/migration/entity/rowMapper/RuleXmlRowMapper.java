package com.cts.migration.entity.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cts.migration.entity.RuleXml;

public class RuleXmlRowMapper implements RowMapper<RuleXml> {

	@Override
	public RuleXml mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		RuleXml obj = new RuleXml();
		obj.setRuleGuid(rs.getString(1));
		obj.setVersionNumber(rs.getString(2));
		obj.setXmlValue(rs.getString(3));
		return obj;
	}

}
