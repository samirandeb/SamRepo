package com.cts.migration.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.migration.config.MigrationDBConfig;
import com.cts.migration.config.TableConfiguration;
import com.cts.migration.dao.MigrationDao;
import com.cts.migration.dao.OipaMigrationAuditDao;
import com.cts.migration.dao.OipaRegionDao;
import com.cts.migration.entity.IvsRule;
import com.cts.migration.entity.IvsVersion;
import com.cts.migration.entity.RuleXml;
import com.cts.migration.model.OmtRegion;
import com.cts.migration.model.Region;
import com.cts.migration.model.RuleComparison;
import com.cts.migration.model.Table;
import com.cts.migration.model.UpperMigrationRegion;
import com.cts.migration.model.ui.OipaMigrationActivity;
import com.cts.migration.model.ui.OipaMigrationAudit;
import com.cts.migration.model.ui.OipaRegion;
import com.cts.migration.model.ui.OipaRule;
import com.cts.migration.util.MigrationUtil;

@Service
@Scope("session")
public class OipaMigrationService {
	@Autowired
	private ApplicationContext context;

	@Autowired
	private MigrationDao migrationDao;

	@Autowired
	private OipaMigrationAuditDao auditDao;

	@Autowired
	private TableConfiguration tableConfig;

	@Autowired
	private OipaRegionDao oipaRegionDao;

	public List<OipaRegion> getAllSourceRegion(String userId) {
		List<OipaRegion> allSources = new ArrayList<>();
		for (OmtRegion omtRegion : oipaRegionDao.getAll(userId)) {
			OipaRegion region = new OipaRegion();
			region.setNonIvsRegion(omtRegion.isNonIvs());
			region.setRegionDescription(omtRegion.getDescription());
			region.setRegionName(omtRegion.getIdentifier());
			allSources.add(region);
		}
		return allSources;
	}

	public List<OipaRegion> getAllSourceRegion() {
		List<OipaRegion> allSources = new ArrayList<>();

		for (OmtRegion omtRegion : oipaRegionDao.getAllSourceRegion()) {
			OipaRegion region = new OipaRegion();
			region.setNonIvsRegion(omtRegion.isNonIvs());
			region.setRegionDescription(omtRegion.getDescription());
			region.setRegionName(omtRegion.getIdentifier());
			allSources.add(region);
		}

		return allSources;
	}

	public List<OipaRegion> getAllDestinationRegion(String userId) {
		List<OipaRegion> allSources = new ArrayList<>();
		OmtRegion omtRegion = oipaRegionDao.getSandboxRegion(userId);

		OipaRegion region = new OipaRegion();
		region.setNonIvsRegion(omtRegion.isNonIvs());
		region.setRegionDescription(omtRegion.getDescription());
		region.setRegionName(omtRegion.getIdentifier());
		allSources.add(region);

		return allSources;
	}

	public String getNextAuditId() {
		return auditDao.getNextAuditId();
	}

	public List<OipaRule> getAllRules(InputStream inputStream, String workSheetName) throws IOException {
		List<OipaRule> result = new ArrayList<OipaRule>();
		XSSFWorkbook myWorkBook = new XSSFWorkbook(inputStream);
		boolean matchFound = false;
		for (int i = 0; i < myWorkBook.getNumberOfSheets(); i++) {

			if (workSheetName.equalsIgnoreCase(myWorkBook.getSheetName(i))) {
				XSSFSheet mySheet = myWorkBook.getSheetAt(i);
				// Get iterator to all the rows in current sheet
				Iterator<Row> rowIterator = mySheet.iterator();
				// Traversing over each row of XLSX file
				boolean headerParesd = false;
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next(); // For each row, iterate
													// through each columns
					if (!headerParesd) {
						headerParesd = true;
						continue;
					}
					OipaRule rule = new OipaRule();
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getColumnIndex()) {
						case 0:
							rule.setRuleGuid(MigrationUtil.getCellValue(cell).toString());
							break;
						case 1:
							rule.setRuleName(MigrationUtil.getCellValue(cell).toString());
							break;
						case 2:
							rule.setRuleType(MigrationUtil.getCellValue(cell).toString());
							break;
						case 3:
							rule.setIvsVersion(((Double) MigrationUtil.getCellValue(cell)).intValue() + "");
							break;
						case 4:
							rule.setNote(MigrationUtil.getCellValue(cell).toString());
							break;
						default: // DO NOTHING
						}

					}
					result.add(rule);
				}
				matchFound = true;
				break;
			}
		}

		if (!matchFound) {
			throw new RuntimeException("No worksheet found with name : " + workSheetName);
		}
		// System.out.println("RULES : " + result);
		return result;
	}

	public List<OipaRule> getAllRules(OipaMigrationActivity migration) {

		List<OipaRule> result = new ArrayList<OipaRule>();
		List<Map<String, Object>> resultSet = null;
		// System.out.println(migration.getSelectedRuleTable());
		resultSet = migrationDao.getAllRules(migration.getSelectedRuleTable());
		for (Map<String, Object> row : resultSet) {
			OipaRule rule = new OipaRule();
			rule.setRuleGuid(row.get("RULEGUID") == null ? null : row.get("RULEGUID").toString());
			rule.setRuleName(row.get("RULENAME") == null ? null : row.get("RULENAME").toString());
			rule.setPlanGuid(row.get("PLANGUID") == null ? null : row.get("PLANGUID").toString());
			rule.setCompanyGuid(row.get("COMPANYGUID") == null ? null : row.get("COMPANYGUID").toString());
			result.add(rule);
		}
		// System.out.println("No of rules fetched : " + result.size());
		return result;
	}

	public List<Map<String, Object>> getAllVersions(OipaMigrationActivity migration) {
		List<Map<String, Object>> versions = new ArrayList<Map<String, Object>>();
		for (String ruleGuid : migration.getSelectedRuleGuidList()) {
			Map<String, Object> versionMap = null;
			OipaRule rule = new OipaRule();
			rule.setRuleGuid(ruleGuid);
			rule = migration.getRulesForMigration().get(migration.getRulesForMigration().indexOf(rule));
			try {
				versionMap = migrationDao.getHighestVersion(ruleGuid);
				// System.out.println(versionMap.get("LASTMODIFIEDGMT").getClass());
				if (rule != null) {
					rule.setVersionGuid(versionMap.get("VERSIONGUID").toString());
					rule.setIvsVersion(versionMap.get("VERSIONNUMBER").toString());
					rule.setRuleType(versionMap.get("RULETYPECODE").toString());
					rule.setUpdatedBy(versionMap.get("LASTMODIFIEDBY") != null
							? versionMap.get("LASTMODIFIEDBY").toString() : "Not Available");
					rule.setUpdatedOn(versionMap.get("LASTMODIFIEDGMT") != null
							? versionMap.get("LASTMODIFIEDGMT").toString() : "Not Available");
					// System.out.println("Rules version : " +
					// rule.getIvsVersion());
				}
			} catch (EmptyResultDataAccessException ex) {
				// System.out.println("No records found in IVS for rule : " +
				// ruleGuid);
				rule.setVersionGuid(null);
				rule.setIvsVersion("No records found");
			}
			versions.add(versionMap);
		}
		return versions;
	}

	public List<IvsVersion> getAllVersionsNew(OipaMigrationActivity migration) {
		// System.out.println(".................Calling new version
		// information.......... : " + migration.getSelectedRuleGuidList());
		List<IvsVersion> versions = new ArrayList<>();
		List<String> ruleGuids = new ArrayList<String>();
		for (OipaRule rule : migration.getRulesForMigration()) {
			ruleGuids.add(rule.getRuleGuid());
		}
		if (ruleGuids.size() > 0) {
			versions = migrationDao.getHighestVersion(ruleGuids);
			for (IvsVersion versionInfo : versions) {
				OipaRule rule = new OipaRule();
				rule.setRuleGuid(versionInfo.getRuleGuid());
				rule = migration.getRulesForMigration().get(migration.getRulesForMigration().indexOf(rule));
				if (rule != null) {
					rule.setVersionGuid(versionInfo.getVersionGuid());
					if (rule.getIvsVersion() == null || "".equalsIgnoreCase(rule.getIvsVersion())) {
						rule.setIvsVersion(versionInfo.getVersionNumber() + "");
					}
					rule.setHighestVersion(versionInfo.getVersionNumber() + "");
					rule.setRuleType(versionInfo.getRuleTypeCode());
					rule.setUpdatedBy(versionInfo.getLastModifiedBy());
					rule.setUpdatedOn(versionInfo.getLastModifiedGMT().toString());
					// System.out.println("Rules version : " +
					// rule.getIvsVersion());
				}
			}
		}
		return versions;
	}

	public List<IvsVersion> getIvsVersions(String ruleGuid) {
		return migrationDao.getIvsVersions(ruleGuid);
	}

	public List<OipaMigrationAudit> getAuditDetails(UpperMigrationRegion region) {
		// System.out.println("Fetch records for region : " + region.getName());
		List<OipaMigrationAudit> auditRecords = auditDao.getAll();
		// System.out.println("Fetched Record size : " + auditRecords.size());
		return auditRecords;
	}

	public List<OipaMigrationAudit> getRulesForMigration(UpperMigrationRegion region) {
		// System.out.println("Fetch records for region : " + region.getName());
		List<OipaMigrationAudit> audits = auditDao.getForRegion(region, false);
		// System.out.println("Record count __ : " + audits.size());
		return audits;
	}

	public List<OipaMigrationAudit> getMigratedRules(UpperMigrationRegion region) {
		// System.out.println("Fetch records for region : " + region.getName());
		List<OipaMigrationAudit> audits = auditDao.getForRegion(region, true);
		// System.out.println("Record count __ : " + audits.size());
		return audits;
	}

	public List<OipaMigrationAudit> getLastMigrationAudit(OipaMigrationActivity migration) {
		// System.out.println("Audit Guid : " + migration.getAuditId());
		return auditDao.getSpecific(migration.getAuditId());
	}

	public List<OipaMigrationAudit> getLastMigrationAuditUR(OipaMigrationActivity migration) {
		// System.out.println("Audit Guid : " + migration.getAuditId());
		return auditDao.getSpecific(migration.getSelectedRuleGuidList());
	}

	@Transactional("destTransactionManager")
	public void migrateRule(OipaMigrationActivity migration) {
		for (OipaRule oipaRule : migration.getRulesForMigration()) {
			if (oipaRule.getVersionGuid() != null && !("").equals(oipaRule.getVersionGuid())) {

				Table oipaTable = tableConfig.getTables().get(oipaRule.getRuleType());
				IvsRule rule = migrationDao.getRuleFromIvs(oipaRule.getVersionGuid());
				String ruleGuid = (String) rule.getValue(oipaTable.getIdColumn());
				String newVersionGuid = UUID.randomUUID().toString().toUpperCase();
				IvsVersion version = new IvsVersion();
				version.setVersionGuid(newVersionGuid);
				version.setRuleGuid(ruleGuid);
				version.setVersionNumber(migrationDao.getDestinationVersionNumber(ruleGuid));
				version.setRuleTypeCode(oipaRule.getRuleType());
				version.setLastModifiedBy(migration.getUser());
				version.setLastModifiedGMT(new Date());

				OipaMigrationAudit audit = new OipaMigrationAudit();

				String auditRowId = UUID.randomUUID().toString().toUpperCase();

				audit.setId(auditRowId);
				audit.setAuditId(migration.getAuditId());
				audit.setRuleType(oipaRule.getRuleType());
				audit.setRuleGuid(oipaRule.getRuleGuid());
				audit.setRuleName(oipaRule.getRuleName());
				audit.setDeveloper(oipaRule.getUpdatedBy());
				audit.setSourceRegion(migration.getSourceRegion().getRegionName());
				audit.setSourceVersion(oipaRule.getIvsVersion());
				audit.setTargetRegion(migration.getDestinationRegion().getRegionName());
				audit.setTargetVersion("" + version.getVersionNumber());
				audit.setReason(oipaRule.getNote());
				audit.setMigratedBy(migration.getUser());
				audit.setMigratedOn(new Date());

				rule.setVersionGuid(newVersionGuid);

				migrationDao.updateRule(oipaTable, rule.getUpdateParams(oipaTable));
				migrationDao.insertIvs(version);
				migrationDao.insertIvsField(rule);
				auditDao.insert(audit);
			}
		}
	}

	public void migrateRuleUR(OipaMigrationActivity migration) {
		for (OipaMigrationAudit audit : migration.getAuditList()) {
			if (migration.getSelectedRuleGuidList().contains(audit.getId())) {
				Table oipaTable = tableConfig.getTables().get(audit.getRuleType());

				IvsRule rule = migrationDao.getRuleFromIvs(audit.getTargetVersion());

				switch (migration.getDestinationRegion().getRegionName()) {
				case "QA":
					audit.setQaMigrated("1");
					audit.setQaMigratedOn(new Date());
					audit.setQaMigratedBy(migration.getUser());
					break;
				case "UAT":
					audit.setUatMigrated("1");
					audit.setUatMigratedOn(new Date());
					audit.setUatMigratedBy(migration.getUser());
					break;
				case "PERF":
					audit.setPerfMigrated("1");
					audit.setPerfMigratedOn(new Date());
					audit.setPerfMigratedBy(migration.getUser());
					break;
				case "PROD":
					audit.setProdMigrated("1");
					audit.setProdMigratedOn(new Date());
					audit.setProdMigratedBy(migration.getUser());
					break;
				default:
					throw new RuntimeException("Invalid Region");
				}

				migrationDao.updateRule(oipaTable, rule.getUpdateParams(oipaTable));
				auditDao.update(audit);
			}
		}
	}

	public List<OipaMigrationAudit> getAuditList() {
		return auditDao.getAuditList();
	}

	public RuleComparison getComparisonDetail(RuleComparison ruleComparisonModel, Table ruleTable) {
		// source XML update
		String sourceVersion = ruleComparisonModel.getSourceVersion();
		if (MigrationUtil.isEmpty(sourceVersion)) {
			throw new RuntimeException("Version information not present");
		}
		RuleXml ruleXml = migrationDao.getXmlDataFromIVS(ruleComparisonModel.getRuleGuid(), sourceVersion, "XMLData",true);
		ruleComparisonModel.setSourceXml(ruleXml.getXmlValue());
		ruleComparisonModel.setSourceVersion(ruleXml.getVersionNumber());
		// set the destination
		// step 1 : check whether the rule exists
		boolean ruleExists = migrationDao.doesExist(ruleComparisonModel.getRuleGuid(), ruleTable, false);

		if (ruleExists) {

			if (((Region) context.getBean(MigrationDBConfig.DESTINATION_REGION_BEAN)).hasIVS()) {
				String destVersion = ruleComparisonModel.getDestinationVersion();
				if (MigrationUtil.isEmpty(destVersion)) {
					destVersion = migrationDao.getHighestVersion(ruleComparisonModel.getRuleGuid(), false).get("VERSIONNUMBER").toString();
				}
				ruleXml = migrationDao.getXmlDataFromIVS(ruleComparisonModel.getRuleGuid(), destVersion, "XMLData",	false);
			}
			else{
				ruleXml = migrationDao.getXmlDataFromPAS(ruleComparisonModel.getRuleGuid(),ruleTable,false);
			}
			ruleComparisonModel.setDestinationXml(ruleXml.getXmlValue());
			ruleComparisonModel.setDestinationVersion(ruleXml.getVersionNumber());

		}
		else{
			System.out.println("Destination does not have the rule : "+ruleComparisonModel.getRuleGuid());
		}
		return ruleComparisonModel;
	}
}
