package com.cts.migration.entity.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cts.migration.entity.IvsVersionField;

public class IvsVersionFieldRowMapper implements RowMapper<IvsVersionField> {
	public IvsVersionField mapRow(ResultSet rs, int rowNum) throws SQLException {
		IvsVersionField ivsField = new IvsVersionField();
		ivsField.setVersionGuid(rs.getString("VERSIONGUID"));
		ivsField.setFieldName(rs.getString("FIELDNAME"));
		ivsField.setFieldTypeCode(rs.getString("FIELDTYPECODE"));
		ivsField.setDateValue(rs.getTimestamp("DATEVALUE"));
		ivsField.setTextValue(rs.getString("TEXTVALUE"));
		ivsField.setIntValue(rs.getInt("INTVALUE"));
		ivsField.setFloatValue(rs.getDouble("FLOATVALUE"));
		ivsField.setXmlDataValue(rs.getString("XMLDATAVALUE"));
		return ivsField;
	}
}