package com.cts.migration.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.cts.migration.config.UpperMigrationRegionConfig;
import com.cts.migration.model.UpperMigrationRegion;
import com.cts.migration.model.ui.OipaMigrationAudit;

@Component
@Scope("session")
public class OipaMigrationAuditDao {
	
	private static final String AUDIT_TABLE = "OMTAUDIT";

	@Autowired
	@Qualifier("auditJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private UpperMigrationRegionConfig upperMigrationRegionConfig;
	
	private static final String GET_AUDIT_ID = "select next_audit_id as AUDITID from dual";

	private static final String INSERT_QUERY = "Insert into "
			+ AUDIT_TABLE
			+ " (ID" + ",AUDITID" + ",RULETYPE" + ",RULEGUID" + ",RULENAME" + ",DEVELOPER " + ",SOURCEREGION" + ",SOURCEVERSION" + ",TARGETREGION" + ",TARGETVERSION" + ",MIGRATEDBY" + ",MIGRATEDON" + ",REASON" + ",QAMIGRATED" + ",QAMIGRATEDON" + ",QAMIGRATEDBY " + ",UATMIGRATED" + ",UATMIGRATEDON" + ",UATMIGRATEDBY " + ",PERFMIGRATED" + ",PERFMIGRATEDON" + ",PERFMIGRATEDBY " + ",PRODMIGRATED" + ",PRODMIGRATEDON" + ",PRODMIGRATEDBY " + ") " + "values " + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SELECT_QUERY = "SELECT " + "ID" + ",AUDITID " + ",RULETYPE " + ",RULEGUID " + ",RULENAME " + ",DEVELOPER " + ",SOURCEREGION " + ",SOURCEVERSION " + ",TARGETREGION " + ",TARGETVERSION " + ",MIGRATEDBY " + ",MIGRATEDON " + ",REASON " + ",QAMIGRATED " + ",QAMIGRATEDON " + ",QAMIGRATEDBY " + ",UATMIGRATED " + ",UATMIGRATEDON " + ",UATMIGRATEDBY " + ",PERFMIGRATED " + ",PERFMIGRATEDON " + ",PERFMIGRATEDBY " + ",PRODMIGRATED " + ",PRODMIGRATEDON " + ",PRODMIGRATEDBY " + " FROM "
			+ AUDIT_TABLE + " WHERE 1=1 ";

	private static final String WHERE_AUDIT_ID = "AND AUDITID = ? ";

	private static final String WHERE_ID = "AND ID = ? ";

	private static final String UPDATE_QUERY = "UPDATE "
			+ AUDIT_TABLE
			+ " SET QAMIGRATED = ?,QAMIGRATEDON = ?,QAMIGRATEDBY = ?,UATMIGRATED = ?,UATMIGRATEDON = ?,UATMIGRATEDBY = ?,PERFMIGRATED = ?,PERFMIGRATEDON = ?,PERFMIGRATEDBY = ?,PRODMIGRATED = ?,PRODMIGRATEDON = ?,PRODMIGRATEDBY = ? WHERE ID = ?";

	public void insert(OipaMigrationAudit auditRow) {
		jdbcTemplate.update(INSERT_QUERY, getValueMap(auditRow).values().toArray());
	}

	public void update(OipaMigrationAudit auditRow) {
		jdbcTemplate.update(UPDATE_QUERY, getParamsUR(auditRow));
	}

	public List<OipaMigrationAudit> getAll() {
		List<OipaMigrationAudit> result = new ArrayList<>();
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(SELECT_QUERY);
		for (Map<String, Object> row : resultSet) {
			result.add(auditRowMapper(row));
		}
		return result;
	}
	
	public String getNextAuditId(){
		String auditId = null;
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(GET_AUDIT_ID);
		
		for (Map<String, Object> row : resultSet) {
			auditId = row.get("AUDITID")==null?null:row.get("AUDITID")+"";
		}
		//System.out.println("==========GENERATED AUDIT ID : "+auditId);
		return auditId;
	}

	public List<OipaMigrationAudit> getForRegion(UpperMigrationRegion region, boolean migrated) {
		List<OipaMigrationAudit> result = new ArrayList<>();
		String query = null;
		if (migrated) {
			query = getHistoryQueryForRegion(region);
		} else {
			query = getQueryForRegion(region);
		}
		//System.out.println("QUERY : " + query);
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(query);
		for (Map<String, Object> row : resultSet) {
			result.add(auditRowMapper(row));
		}
		return result;
	}
	
	public List<OipaMigrationAudit> getAuditList() {
		List<OipaMigrationAudit> result = new ArrayList<>();
		String query = SELECT_QUERY+" ORDER BY PRODMIGRATEDON,UATMIGRATEDON,QAMIGRATEDON,AUDITID";
		//System.out.println("QUERY : " + query);
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(query);
		for (Map<String, Object> row : resultSet) {
			result.add(auditRowMapper(row));
		}
		return result;
	}

	private String getQueryForRegion(UpperMigrationRegion region) {
		region = upperMigrationRegionConfig.getRegions().get(region.getName());
		if (region.getSelectQuery() == null) {
			StringBuffer strb = new StringBuffer();
			if (region.getDependentOn() != null) {
				for (String regionName : region.getDependentOn()) {
					strb.append(" AND " + regionName + " = '1' ");
				}
			}
			strb.append(" AND ( " + region.getFlagColumn() + " != '1' OR " + region.getFlagColumn() + " IS NULL) ");

			region.setSelectQuery(SELECT_QUERY + strb.toString());
		}

		return region.getSelectQuery();
	}

	private String getHistoryQueryForRegion(UpperMigrationRegion region) {
		region = upperMigrationRegionConfig.getRegions().get(region.getName());
		if (region.getHistoryQuery() == null) {
			StringBuffer strb = new StringBuffer();
			strb.append(" AND " + region.getFlagColumn() + " = '1' ");
			region.setHistoryQuery(SELECT_QUERY + strb.toString());
		}
		return region.getHistoryQuery();
	}

	public List<OipaMigrationAudit> getSpecific(String auditId) {
		List<OipaMigrationAudit> result = new ArrayList<>();
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(SELECT_QUERY + WHERE_AUDIT_ID, new Object[] { auditId });
		for (Map<String, Object> row : resultSet) {
			result.add(auditRowMapper(row));
		}
		return result;
	}

	public List<OipaMigrationAudit> getSpecific(HashSet<String> seletedRuleGuids) {
		List<OipaMigrationAudit> result = new ArrayList<>();
		for (String id : seletedRuleGuids) {
			List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(SELECT_QUERY + WHERE_ID, new Object[] { id });
			for (Map<String, Object> row : resultSet) {
				result.add(auditRowMapper(row));
			}
		}
		return result;
	}

	private Map<String, Object> getValueMap(OipaMigrationAudit auditRow) {
		Map<String, Object> valueMap = new LinkedHashMap<>();
		valueMap.put("ID", auditRow.getId());
		// AUDITID"
		valueMap.put("AUDITID", auditRow.getAuditId());
		// RULETYPE"
		valueMap.put("RULETYPE", auditRow.getRuleType());
		// RULEGUID"
		valueMap.put("RULEGUID", auditRow.getRuleGuid());
		// RULENAME"
		valueMap.put("RULENAME", auditRow.getRuleName());
		// DEVELOPER"
		valueMap.put("DEVELOPER", auditRow.getDeveloper());
		// SOURCEREGION"
		valueMap.put("SOURCEREGION", auditRow.getSourceRegion());
		// SOURCEVERSION"
		valueMap.put("SOURCEVERSION", auditRow.getSourceVersion());
		// TARGETREGION"
		valueMap.put("TARGETREGION", auditRow.getTargetRegion());
		// TARGETVERSION"
		valueMap.put("TARGETVERSION", auditRow.getTargetVersion());
		// MIGRATEDBY"
		valueMap.put("MIGRATEDBY", auditRow.getMigratedBy());
		// MIGRATEDON"
		valueMap.put("MIGRATEDON", auditRow.getMigratedOn());
		// REASON"
		valueMap.put("REASON", auditRow.getReason());
		// QAMIGRATED"
		valueMap.put("QAMIGRATED", auditRow.getQaMigrated());
		// QAMIGRATEDON"
		valueMap.put("QAMIGRATEDON", (auditRow.getQaMigratedOn() != null ? new Timestamp(auditRow.getQaMigratedOn().getTime()) : null));
		// QAMIGRATEDBY"
		valueMap.put("QAMIGRATEDBY", auditRow.getQaMigratedBy());
		// UATMIGRATED"
		valueMap.put("UATMIGRATED", auditRow.getUatMigrated());
		// UATMIGRATEDON"
		valueMap.put("UATMIGRATEDON", (auditRow.getQaMigratedOn() != null ? new Timestamp(auditRow.getQaMigratedOn().getTime()) : null));
		// UATMIGRATEDBY"
		valueMap.put("UATMIGRATEDBY", auditRow.getUatMigratedBy());
		// PERFMIGRATED"
		valueMap.put("PERFMIGRATED", auditRow.getPerfMigrated());
		// PERFMIGRATEDON"
		valueMap.put("PERFMIGRATEDON", (auditRow.getPerfMigratedOn() != null ? new Timestamp(auditRow.getPerfMigratedOn().getTime()) : null));
		// PERFMIGRATEDBY"
		valueMap.put("PERFMIGRATEDBY", auditRow.getPerfMigratedBy());
		// PRODMIGRATED"
		valueMap.put("PRODMIGRATED", auditRow.getProdMigrated());
		// PRODMIGRATEDON"
		valueMap.put("PRODMIGRATEDON", (auditRow.getProdMigratedOn() != null ? new Timestamp(auditRow.getProdMigratedOn().getTime()) : null));
		// PRODMIGRATEDBY"
		valueMap.put("PRODMIGRATEDBY", auditRow.getProdMigratedBy());
		return valueMap;
	}

	private Object[] getParamsUR(OipaMigrationAudit auditRow) {
		Object params[] = new Object[13];
		// QAMIGRATED"
		params[0] = auditRow.getQaMigrated();
		// QAMIGRATEDON"
		params[1] = (auditRow.getQaMigratedOn() != null ? new Timestamp(auditRow.getQaMigratedOn().getTime()) : null);
		// QAMIGRATEDBY"
		params[2] = auditRow.getQaMigratedBy();
		// UATMIGRATED"
		params[3] = auditRow.getUatMigrated();
		// UATMIGRATEDON"
		params[4] = (auditRow.getUatMigratedOn() != null ? new Timestamp(auditRow.getUatMigratedOn().getTime()) : null);
		// UATMIGRATEDBY"
		params[5] = auditRow.getUatMigratedBy();
		// PERFMIGRATED"
		params[6] = auditRow.getPerfMigrated();
		// PERFMIGRATEDON"
		params[7] = (auditRow.getPerfMigratedOn() != null ? new Timestamp(auditRow.getPerfMigratedOn().getTime()) : null);
		// PERFMIGRATEDBY"
		params[8] = auditRow.getPerfMigratedBy();
		// PRODMIGRATED"
		params[9] = auditRow.getProdMigrated();
		// PRODMIGRATEDON"
		params[10] = (auditRow.getProdMigratedOn() != null ? new Timestamp(auditRow.getProdMigratedOn().getTime()) : null);
		// PRODMIGRATEDBY"
		params[11] = auditRow.getProdMigratedBy();
		// ID
		params[12] = auditRow.getId();
		return params;
	}

	private OipaMigrationAudit auditRowMapper(Map<String, Object> row) {
		OipaMigrationAudit auditRow = new OipaMigrationAudit();
		auditRow.setId(row.get("ID") != null ? row.get("ID").toString() : "");
		auditRow.setAuditId(row.get("AUDITID") != null ? row.get("AUDITID").toString() : "");
		auditRow.setRuleType(row.get("RULETYPE") != null ? row.get("RULETYPE").toString() : "");
		auditRow.setRuleGuid(row.get("RULEGUID") != null ? row.get("RULEGUID").toString() : "");
		auditRow.setRuleName(row.get("RULENAME") != null ? row.get("RULENAME").toString() : "");
		auditRow.setDeveloper(row.get("DEVELOPER") != null ? row.get("DEVELOPER").toString() : "");
		auditRow.setSourceRegion(row.get("SOURCEREGION") != null ? row.get("SOURCEREGION").toString() : "");
		auditRow.setSourceVersion(row.get("SOURCEVERSION") != null ? row.get("SOURCEVERSION").toString() : "");
		auditRow.setTargetRegion(row.get("TARGETREGION") != null ? row.get("TARGETREGION").toString() : "");
		auditRow.setTargetVersion(row.get("TARGETVERSION") != null ? row.get("TARGETVERSION").toString() : "");
		auditRow.setMigratedBy(row.get("MIGRATEDBY") != null ? row.get("MIGRATEDBY").toString() : "");
		auditRow.setMigratedOn(row.get("MIGRATEDON") != null ? new Date(((Timestamp) row.get("MIGRATEDON")).getTime()) : null);
		auditRow.setReason(row.get("REASON") != null ? row.get("REASON").toString() : "");
		auditRow.setQaMigrated(row.get("QAMIGRATED") != null ? row.get("QAMIGRATED").toString() : "");
		auditRow.setQaMigratedOn(row.get("QAMIGRATEDON") != null ? new Date(((Timestamp) row.get("QAMIGRATEDON")).getTime()) : null);
		auditRow.setQaMigratedBy(row.get("QAMIGRATEDBY") != null ? row.get("QAMIGRATEDBY").toString() : "");
		auditRow.setUatMigrated(row.get("UATMIGRATED") != null ? row.get("UATMIGRATED").toString() : "");
		auditRow.setUatMigratedOn(row.get("UATMIGRATEDON") != null ? new Date(((Timestamp) row.get("UATMIGRATEDON")).getTime()) : null);
		auditRow.setUatMigratedBy(row.get("UATMIGRATEDBY") != null ? row.get("UATMIGRATEDBY").toString() : "");
		auditRow.setPerfMigrated(row.get("PERFMIGRATED") != null ? row.get("PERFMIGRATED").toString() : "");
		auditRow.setPerfMigratedOn(row.get("PERFMIGRATEDON") != null ? new Date(((Timestamp) row.get("PERFMIGRATEDON")).getTime()) : null);
		auditRow.setPerfMigratedBy(row.get("PERFMIGRATEDBY") != null ? row.get("PERFMIGRATEDBY").toString() : "");
		auditRow.setProdMigrated(row.get("PRODMIGRATED") != null ? row.get("PRODMIGRATED").toString() : "");
		auditRow.setProdMigratedOn(row.get("PRODMIGRATEDON") != null ? new Date(((Timestamp) row.get("PRODMIGRATEDON")).getTime()) : null);
		auditRow.setProdMigratedBy(row.get("PRODMIGRATEDBY") != null ? row.get("PRODMIGRATEDBY").toString() : "");
		return auditRow;
	}
}
