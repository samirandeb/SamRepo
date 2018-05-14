package com.cts.migration.entity.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cts.migration.entity.IvsVersion;

public class IvsVersionRowMapper implements RowMapper<IvsVersion> {
	public IvsVersion mapRow(ResultSet rs, int rowNum) throws SQLException {
		IvsVersion ivs = new IvsVersion();
		ivs.setVersionGuid(rs.getString("VERSIONGUID"));
		ivs.setRuleGuid(rs.getString("RULEGUID"));
		ivs.setVersionNumber(rs.getInt("VERSIONNUMBER"));
		ivs.setRuleTypeCode(rs.getString("RULETYPECODE"));
		ivs.setLastModifiedBy(rs.getString("LASTMODIFIEDBY"));
		ivs.setLastModifiedGMT(rs.getTimestamp("LASTMODIFIEDGMT"));
		ivs.setComments(rs.getString("COMMENTS"));
		ivs.setLabel(rs.getString("LABEL"));
		return ivs;
	}
}