package com.cts.migration.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cts.migration.config.MigrationDBConfig;
import com.cts.migration.entity.IvsRule;
import com.cts.migration.entity.IvsVersion;
import com.cts.migration.entity.IvsVersionField;
import com.cts.migration.entity.RuleXml;
import com.cts.migration.entity.rowMapper.IvsVersionFieldRowMapper;
import com.cts.migration.entity.rowMapper.IvsVersionRowMapper;
import com.cts.migration.entity.rowMapper.RuleXmlRowMapper;
import com.cts.migration.model.Region;
import com.cts.migration.model.Table;

@Component
@Scope("session")
public class MigrationDao {
	private static final String SELECT_HIGHEST_VERSION = "Select * from (SELECT IVSVERSION.*,(VERSIONNUMBER - MAX(VERSIONNUMBER) OVER (partition by ruleguid) )DIFF FROM IVSVERSION WHERE RULEGUID IN (:ruleGuids)) where DIFF=0";
	private static final String GET_IVS_DATA = "select V.RULEGUID,V.VERSIONNUMBER,F.XMLDATAVALUE from IVSVERSION v inner join IVSVERSIONFIELD f on F.VERSIONGUID = V.VERSIONGUID  and F.FIELDNAME=:FIELDNAME where v.RULEGUID = :RULEGUID and V.VERSIONNUMBER = :VERSIONNUMBER ";
	private static final String GET_RULETABLE_DATA = "";

	@Autowired
	private ApplicationContext context;

	public List<IvsVersion> getIvsVersions(String ruleGuid) {
		return ((Region) context.getBean(MigrationDBConfig.SOURCE_REGION_BEAN)).getIvsJdbcTemplate().query("SELECT * FROM IVSVERSION WHERE RULEGUID = ? ORDER BY VERSIONNUMBER DESC", new IvsVersionRowMapper(), ruleGuid);
	}

	public IvsRule getRuleFromIvs(String versionGuid) {
		return new IvsRule(versionGuid, ((Region) context.getBean(MigrationDBConfig.SOURCE_REGION_BEAN)).getIvsJdbcTemplate().query("SELECT * FROM IVSVERSIONFIELD WHERE VERSIONGUID = ?", new IvsVersionFieldRowMapper(), versionGuid));
	}

	public List<Map<String, Object>> getAllRules(Table table) {
		return ((Region) context.getBean(MigrationDBConfig.SOURCE_REGION_BEAN)).getPasJdbcTemplate().queryForList(table.getEffectiveQuery());
	}

	public Map<String, Object> getHighestVersion(String ruleGuid) {
		return getHighestVersion(ruleGuid,true);
	}
	
	public Map<String, Object> getHighestVersion(String ruleGuid,boolean isSourceRegion) {
		if(isSourceRegion){
			return ((Region) context.getBean(MigrationDBConfig.SOURCE_REGION_BEAN)).getIvsJdbcTemplate().queryForMap("SELECT RULEGUID,VERSIONGUID,VERSIONNUMBER,RULETYPECODE,LASTMODIFIEDBY,LASTMODIFIEDGMT FROM IVSVERSION WHERE RULEGUID=? ORDER BY VERSIONNUMBER DESC FETCH FIRST 1 ROW ONLY", new Object[] { ruleGuid });
		}
		else{
			return ((Region) context.getBean(MigrationDBConfig.DESTINATION_REGION_BEAN)).getIvsJdbcTemplate().queryForMap("SELECT RULEGUID,VERSIONGUID,VERSIONNUMBER,RULETYPECODE,LASTMODIFIEDBY,LASTMODIFIEDGMT FROM IVSVERSION WHERE RULEGUID=? ORDER BY VERSIONNUMBER DESC FETCH FIRST 1 ROW ONLY", new Object[] { ruleGuid });
		}
		
	}

	public List<IvsVersion> getHighestVersion(List<String> ruleGuids) {

		NamedParameterJdbcTemplate jdbcTemplate = ((Region) context.getBean(MigrationDBConfig.SOURCE_REGION_BEAN)).getIvsNamedJdbcTemplate();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ruleGuids", ruleGuids);
		return jdbcTemplate.query(SELECT_HIGHEST_VERSION, parameters, new IvsVersionRowMapper());
	}

	public int updateRule(Table table, Object[] params) {
		return ((Region) context.getBean(MigrationDBConfig.DESTINATION_REGION_BEAN)).getPasJdbcTemplate().update(table.getUpdateQuery(), params);
	}

	@Transactional("destTransactionManager")
	public int insertIvs(IvsVersion version) {
		Object[] params = new Object[] { version.getVersionGuid(), version.getRuleGuid(), version.getVersionNumber(), version.getRuleTypeCode(), version.getLastModifiedBy(), version.getLastModifiedGMT(), version.getComments(), version.getLabel() };
		return ((Region) context.getBean(MigrationDBConfig.DESTINATION_REGION_BEAN)).getIvsJdbcTemplate().update(version.getInsertQuery(), params);
	}

	@Transactional("destTransactionManager")
	public int insertIvsField(IvsRule rule) {
		int insertCount = 0;
		for (IvsVersionField field : rule.getFields()) {
			Object[] params = new Object[] { rule.getVersionGuid(), field.getFieldName(), field.getFieldTypeCode(), field.getDateValue(), field.getTextValue(), field.getIntValue(), field.getFloatValue(), field.getXmlDataValue() };
			insertCount = insertCount + ((Region) context.getBean(MigrationDBConfig.DESTINATION_REGION_BEAN)).getIvsJdbcTemplate().update("INSERT INTO IVSVERSIONFIELD(VERSIONGUID,FIELDNAME,FIELDTYPECODE,DATEVALUE,TEXTVALUE,INTVALUE,FLOATVALUE,XMLDATAVALUE) VALUES(?,?,?,?,?,?,?,?)", params);
		}
		return insertCount;
	}
	
	public RuleXml getXmlDataFromIVS(String ruleguid,String version,String fieldName,boolean isSourceRegion){
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("RULEGUID", ruleguid).addValue("VERSIONNUMBER", version).addValue("FIELDNAME", fieldName);
//		System.out.println("DESTINATION : "+(!isSourceRegion));
//		System.out.println("RULEGUID : "+paramMap.getValue("RULEGUID"));
//		System.out.println("FIELDNAME : "+paramMap.getValue("FIELDNAME"));
//		System.out.println("VERSIONNUMBER : "+paramMap.getValue("VERSIONNUMBER"));
		RuleXml ruleXml = null;
		List<RuleXml> rules = null;
		NamedParameterJdbcTemplate jdbcTemplate = null;
		jdbcTemplate = getNamedParameterJdbcTemplate(isSourceRegion,true);
		rules = jdbcTemplate.query(GET_IVS_DATA, paramMap, new RuleXmlRowMapper());
//		System.out.println("rules fetched : "+rules.size());
		if(rules!=null && rules.size()>0){
			ruleXml = rules.get(0);
		}
		return ruleXml;
	}
	
	
	
	public RuleXml getXmlDataFromPAS(String ruleGuid,Table ruleTable,boolean isSourceRegion){
		RuleXml ruleXML = null;
		String query = "SELECT "+ruleTable.getIdColumn()+", 'NA' as version , "+ruleTable.getUpdateColumns().get(0)+" FROM "+ruleTable.getTableName()+ " WHERE "+ruleTable.getIdColumn()+"=:RULEGUID";
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("RULEGUID", ruleGuid);
		List<RuleXml> rules = getNamedParameterJdbcTemplate(isSourceRegion, false).query(query, paramMap, new RuleXmlRowMapper());
		if(!rules.isEmpty()){
			ruleXML = rules.get(0);
		}
		return ruleXML;
	}

	public int getDestinationVersionNumber(String ruleGuid) {
		return ((Region) context.getBean(MigrationDBConfig.DESTINATION_REGION_BEAN)).getIvsJdbcTemplate().queryForObject("SELECT NVL(MAX(VERSIONNUMBER),0) FROM IVSVERSION WHERE RULEGUID = ? ", Integer.class, new Object[] { ruleGuid }) + 1;
	}

	public boolean doesExist(String ruleGuid, Table ruleTable, boolean isSourceRegion) {
		String query = " SELECT "+ruleTable.getIdColumn()+" FROM "+ruleTable.getTableName()+" WHERE "+ruleTable.getIdColumn()+" = :RULEGUID";
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("RULEGUID", ruleGuid);
		return !getNamedParameterJdbcTemplate(isSourceRegion, false).queryForList(query, paramMap).isEmpty();
	}
	
	private NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(boolean isSourceRegion,boolean isIVS){
		NamedParameterJdbcTemplate jdbcTemplate = null;
		Region region = null;
		if(isSourceRegion){
			region = ((Region) context.getBean(MigrationDBConfig.SOURCE_REGION_BEAN));
		}
		else{
			region = ((Region) context.getBean(MigrationDBConfig.DESTINATION_REGION_BEAN));
		}
		
		if(isIVS){
			jdbcTemplate = region.getIvsNamedJdbcTemplate();
		}
		else{
			jdbcTemplate = region.getPasNamedJdbcTemplate();
		}
		
		return jdbcTemplate;
	}
	

}
