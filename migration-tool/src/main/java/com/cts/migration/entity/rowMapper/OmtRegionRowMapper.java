package com.cts.migration.entity.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cts.migration.model.OmtRegion;

public class OmtRegionRowMapper implements RowMapper<OmtRegion> {

	@Override
	public OmtRegion mapRow(ResultSet rs, int arg1) throws SQLException {
		OmtRegion region = new OmtRegion();
		region.setId(rs.getString("ID"));
		region.setIdentifier(rs.getString("IDENTIFIER"));
		region.setDescription(rs.getString("DESCRIPTION"));
		region.setDestination(rs.getInt("DESTINATION")==1?true:false);
		region.setNonIvs(rs.getInt("NONIVS")==1?true:false);
		region.setUpperRegion(rs.getInt("UPPERREGION")==1?true:false);
		region.setSandBox(rs.getInt("SANDBOX")==1?true:false);
		region.setPasHost(rs.getString("PASHOST"));
		region.setPasPort(rs.getInt("PASPORT")+"");
		region.setPasUser(rs.getString("PASUSER"));
		region.setPasPassword(rs.getString("PASPASSWORD"));
		region.setPasSid(rs.getString("PASSID"));
		region.setIvsHost(rs.getString("IVSHOST"));
		region.setIvsPort(rs.getInt("IVSPORT")+"");
		region.setIvsUser(rs.getString("IVSUSER"));
		region.setIvsPassword(rs.getString("IVSPASSWORD"));
		region.setIvsSid(rs.getString("IVSSID"));
		return region;
	}

}
