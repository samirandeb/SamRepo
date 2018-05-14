package com.cts.migration.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cts.migration.config.MigrationEnvironmentConfigurator;
import com.cts.migration.config.TableConfiguration;
import com.cts.migration.config.UpperMigrationRegionConfig;
import com.cts.migration.entity.CustomUser;
import com.cts.migration.entity.IvsVersion;
import com.cts.migration.model.RuleComparison;
import com.cts.migration.model.Table;
import com.cts.migration.model.ui.OipaMigrationActivity;
import com.cts.migration.model.ui.OipaRegion;
import com.cts.migration.model.ui.OipaRule;
import com.cts.migration.service.OipaMigrationService;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@Controller
@Scope("session")
public class MigrationController {

	public static String TASK_SOURCE_SELECTION = "Source Selection";
	public static String TASK_RULE_SELECTION = "Rule Selection";
	public static String TASK_VERSION_SELECTION = "Version Selection";
	public static String TASK_MIGRATE = "Migrate";

	@Autowired
	private MigrationEnvironmentConfigurator envConfigurator;

//	@Autowired
//	private OipaRegionConfiguration regionConfig;

	@Autowired
	private TableConfiguration tableConfig;

	@Autowired
	private UpperMigrationRegionConfig upperRegionConfig;

	@Autowired
	private OipaMigrationActivity migrationActivity;

	@Autowired
	private OipaMigrationService oipaService;

	private List<OipaRule> parsedRules;
	private List<String> completedTasks;

	// Model attributes

	@ModelAttribute("allTasks")
	public List<String> getAllTasks() {
		List<String> tasks = new ArrayList<>();
		tasks.add(TASK_SOURCE_SELECTION);
		tasks.add(TASK_RULE_SELECTION);
		tasks.add(TASK_VERSION_SELECTION);
		tasks.add(TASK_MIGRATE);
		////System.out.println("Task bean created .. ");
		return tasks;
	}


	@ModelAttribute("allRuleTables")
	public Map<String, Table> allRuleTables() {
		////System.out.println("-- Configured Rules : " + tableConfig.getTables());
		return tableConfig.getTables();
	}


	@RequestMapping(value = "/filters/{ruleType}")
	public String showGuestList(Model model, @PathVariable("ruleType") String ruleType) {
		////System.out.println("Get the parameter list...for : " + ruleType);
		migrationActivity.setSelectedRuleTable(tableConfig.getTables().get(ruleType));
		////System.out.println("Selected Rule Type : " + migrationActivity.getSelectedRuleTable().getFilters());
		model.addAttribute("migrationActivity", migrationActivity);
		return "fragments/filterSection :: filters";
	}

	@RequestMapping(value = "/ivsVersions/{ruleGuid}/{selectedVersion}")
	public String getIvsVersions(Model model, @PathVariable("ruleGuid") String ruleGuid, @PathVariable("selectedVersion") String selectedVersion) {
		////System.out.println("Get IVS versions for : " + ruleGuid);
		List<IvsVersion> ivsVersions = oipaService.getIvsVersions(ruleGuid);
		////System.out.println("IVS VERSIONS: " + ivsVersions);
		model.addAttribute("ivsVersions", ivsVersions);
		model.addAttribute("selectedVersion", selectedVersion);
		return "fragments/partials :: ivsVersions";
	}

	@RequestMapping("/sourceSelection")
	public String sourceSelection(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userId = ((CustomUser)auth.getPrincipal()).getUserId();
		////System.out.println("Logged in user : "+userId);
		parsedRules = null;
		completedTasks = new ArrayList<>();
		model.addAttribute("currentModule", "migration");
		model.addAttribute("currentTask", TASK_SOURCE_SELECTION);
		model.addAttribute("completedTasks", completedTasks);

		migrationActivity = new OipaMigrationActivity();
		migrationActivity.setSourceRegion(new OipaRegion());
		migrationActivity.setDestinationRegion(new OipaRegion());
		migrationActivity.setSelectedRuleTable(new Table());
		model.addAttribute("fragmentName", "fragments/sourceSelection");
		model.addAttribute("migrationActivity", migrationActivity);
		model.addAttribute("allSourceRegion",oipaService.getAllSourceRegion(userId));
//		model.addAttribute("allDestinationRegion",oipaService.getAllDestinationRegion(userId));
		List<OipaRegion> destinations = new ArrayList<>();
		destinations.add(envConfigurator.getSandboxRegion());
		model.addAttribute("allDestinationRegion",destinations);
		migrationActivity.printDetail();
		return "index";
	}

	@RequestMapping("/ruleSelection")
	public String statusPage(Model model, @ModelAttribute OipaMigrationActivity formModel) {
		model.addAttribute("currentModule", "migration");
		model.addAttribute("fragmentName", "fragments/ruleSelection");
		completedTasks.add(TASK_SOURCE_SELECTION);
		model.addAttribute("currentTask", TASK_RULE_SELECTION);
		model.addAttribute("completedTasks", completedTasks);

		if (formModel.getSourceRegion() != null && formModel.getDestinationRegion() != null) {
			migrationActivity.setSourceRegion(formModel.getSourceRegion());
			migrationActivity.setDestinationRegion(formModel.getDestinationRegion());
			envConfigurator.setRegion("S", migrationActivity.getSourceRegion().getRegionName());
			envConfigurator.setRegion("D", migrationActivity.getDestinationRegion().getRegionName());
		}
		migrationActivity.setSelectedRuleGuidList(formModel.getSelectedRuleGuidList());

		////System.out.println("INPUT TYPE : " + formModel.getInputType());
		if (null != formModel.getInputType() && !"".equals(formModel.getInputType())) {
			if (formModel.getInputType().equalsIgnoreCase("table")) {
				// Set the filter information
				migrationActivity.setSelectedRuleTable(tableConfig.getTables().get(formModel.getSelectedRuleTable().getRuleType()));
				migrationActivity.getSelectedRuleTable().setFilters(formModel.getSelectedRuleTable().getFilters());
				migrationActivity.setRules(oipaService.getAllRules(migrationActivity));

			} else {
				migrationActivity.setSelectedRuleTable(tableConfig.getTables().get("-99"));
				migrationActivity.setRules(parsedRules);
			}
		}

		model.addAttribute("migrationActivity", migrationActivity);
		migrationActivity.printDetail();
		return "index";
	}

	@ResponseBody
	@RequestMapping("/addedInCart")
	public long addToCart(@RequestBody Collection<String> ruleCart) {
		////System.out.println("AJAX call successfull :::::::::::::::::::::::::: " + ruleCart.size());
		migrationActivity.updateRulesForMigration(ruleCart);
		
		return migrationActivity.getRulesForMigration().size();
	}
	
	@ResponseBody
	@RequestMapping("/removeFromCart")
	public String removeFromCart(@RequestBody List<String> ruleGuids) {
		////System.out.println("AJAX call successfull :::::::::::::::::::::::::: " + ruleCart.size());
		Collection<OipaRule> tobeDeleted = Collections2.filter(migrationActivity.getRulesForMigration(), filterByRuleIds(ruleGuids));
		migrationActivity.getRulesForMigration().removeAll(tobeDeleted);
		return "success";
	}
	

	@RequestMapping(value = "/getRules/{table}/{filter}")
	public String getRegionHistory(Model model, @PathVariable("table") String table, @PathVariable("filter") String filter) {
		migrationActivity.setSelectedRuleTable(tableConfig.getTables().get(table));
		String filters[] = filter.split("~");
		for (int i = 1; i < filters.length; i++) {
			if ("null".equals(filters[i])) {
				migrationActivity.getSelectedRuleTable().getFilters().get(i - 1).setValue(null);
			} else {
				migrationActivity.getSelectedRuleTable().getFilters().get(i - 1).setValue(filters[i]);
			}
		}
		migrationActivity.setRules(oipaService.getAllRules(migrationActivity));
		model.addAttribute("migrationActivity", migrationActivity);
		return "fragments/partials :: ruleSelectionData";
	}

	@RequestMapping("/ivsVersion")
	public String migrationPage(Model model, @ModelAttribute OipaMigrationActivity formModel) {
		model.addAttribute("currentModule", "migration");
		model.addAttribute("fragmentName", "fragments/ivsVersion");
		model.addAttribute("ruleComparisonModel",new RuleComparison());
		
		completedTasks.clear();
		completedTasks.add(TASK_SOURCE_SELECTION);
		completedTasks.add(TASK_RULE_SELECTION);
		model.addAttribute("currentTask", TASK_VERSION_SELECTION);
		model.addAttribute("completedTasks", completedTasks);
		// migrationActivity.getr
//		migrationActivity.setSelectedRuleGuidList(formModel.getSelectedRuleGuidList());
//		migrationActivity.updateRulesForMigration(formModel.getSelectedRuleGuidList());
		migrationActivity.setVersions(oipaService.getAllVersionsNew(migrationActivity));
		model.addAttribute("migrationActivity", migrationActivity);
		long validRulesCount = Collections2.filter(migrationActivity.getRulesForMigration(), versionAvailable()).size();
		long invalidRulesCount = 0;
		if(migrationActivity.getRulesForMigration().size()>0){
			invalidRulesCount = migrationActivity.getRulesForMigration().size() - validRulesCount;
		}
//		System.out.println("valid rules count : "+validRulesCount);
//		System.out.println("Invalid rules count : "+invalidRulesCount);
		model.addAttribute("validRulesCount",validRulesCount );
		model.addAttribute("invalidRulesCount",invalidRulesCount );
		migrationActivity.printDetail();
		return "index";
	}
	
	
	@RequestMapping("/migrate")
	public String migration(Model model, @ModelAttribute OipaMigrationActivity formModel) {
		model.addAttribute("currentModule", "migration");
		model.addAttribute("fragmentName", "fragments/migrationSummary");
		completedTasks.clear();
		completedTasks.add(TASK_SOURCE_SELECTION);
		completedTasks.add(TASK_RULE_SELECTION);
		completedTasks.add(TASK_VERSION_SELECTION);
		model.addAttribute("currentTask", TASK_MIGRATE);
		model.addAttribute("completedTasks", completedTasks);
		migrationActivity.setRulesForMigration(formModel.getRulesForMigration());
		for (OipaRule rule : migrationActivity.getRulesForMigration()) {
			////System.out.println("===============================");
			////System.out.println(rule.getRuleGuid());
			////System.out.println(rule.getRuleName());
			////System.out.println(rule.getIvsVersion());
			////System.out.println(rule.getVersionGuid());
			////System.out.println(rule.getUpdatedBy());
			////System.out.println(rule.getUpdatedOn());
			////System.out.println(rule.getPlanGuid());
			////System.out.println(rule.getNote());
			////System.out.println("===============================");
		}
		if (migrationActivity.getAuditId() != null) {
			////System.out.println("Migration All Ready Complete");
			model.addAttribute("migrationActivity", migrationActivity);
			return "index";
		}
		String auditId = UUID.randomUUID().toString().toUpperCase();
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		////System.out.println("User : " + user);
		////System.out.println("Audit Id : " + auditId);
		////System.out.println("------------------------------------");
		migrationActivity.setUser(user);
		migrationActivity.setAuditId(oipaService.getNextAuditId());
		oipaService.migrateRule(migrationActivity);
		migrationActivity.setAuditList(oipaService.getLastMigrationAudit(migrationActivity));
		// reportService.generateReport(migrationActivity);
		migrationActivity.setRulesForMigration(null);
		model.addAttribute("migrationActivity", migrationActivity);
		migrationActivity.printDetail();
		return "index";
	}

	@RequestMapping("/user")
	public String userPage(Model model) {
		model.addAttribute("currentModule", "user");
		model.addAttribute("fragmentName", "fragments/user");
		return "index";
	}

	@RequestMapping("/highRegionMigration")
	public String highRegionMigrationPage(Model model) {
		model.addAttribute("currentModule", "highRegionMigration");
		model.addAttribute("upperRegions", upperRegionConfig.getUpperMigrationRegions());
		model.addAttribute("fragmentName", "fragments/highRegionMigration");
		migrationActivity.setDestinationRegion(new OipaRegion());
		model.addAttribute("migrationActivity", migrationActivity);
		model.addAttribute("ruleComparisonModel",new RuleComparison());
		return "index";
	}

	@RequestMapping(value = "/regionHistory/{region}")
	public String getRegionHistory(Model model, @PathVariable("region") String region) {
		envConfigurator.setRegion("S", "SANDBOX");
		model.addAttribute("regionDataTableID", region + "DataTable");
		migrationActivity.setAuditList(oipaService.getRulesForMigration(upperRegionConfig.getRegions().get(region)));
		model.addAttribute("migrationActivity", migrationActivity);
		boolean isConfigured = envConfigurator.getRegionMap().containsKey(region);
		if(isConfigured){
			envConfigurator.setRegion("D", region);
		}
		model.addAttribute("regionConfigured", isConfigured);
		return "fragments/partials :: upperRegionMigration";
	}

	@RequestMapping("/migrateUR")
	public String migrateUR(Model model, @ModelAttribute OipaMigrationActivity formModel) {
		model.addAttribute("fragmentName", "fragments/migrationSummaryUR");
		envConfigurator.setRegion("S", "SANDBOX");
		envConfigurator.setRegion("D", formModel.getDestinationRegion().getRegionName());
		migrationActivity.getDestinationRegion().setRegionName(formModel.getDestinationRegion().getRegionName());
		migrationActivity.setSelectedRuleGuidList(formModel.getSelectedRuleGuidList());
		migrationActivity.setUser(SecurityContextHolder.getContext().getAuthentication().getName());
		oipaService.migrateRuleUR(migrationActivity);
		migrationActivity.setAuditList(oipaService.getLastMigrationAuditUR(migrationActivity));
		migrationActivity.getSelectedRuleGuidList().clear();
		model.addAttribute("migrationActivity", migrationActivity);
		return "index";
	}

	@RequestMapping("/dashBoard")
	public String dashBoard(Model model) {
		model.addAttribute("upperRegions", upperRegionConfig.getUpperMigrationRegions());
		model.addAttribute("currentModule", "dashboard");
		model.addAttribute("fragmentName", "fragments/dashBoard");
//		migrationActivity.setDestinationRegion(new OipaRegion());
//		migrationActivity.setAuditList(oipaService.getAuditList());
		model.addAttribute("migrationActivity", migrationActivity);
		return "index";
	}

	@RequestMapping(value = "/regionSpecificHistory/{region}")
	public String getRegionSpecificHistory(Model model, @PathVariable("region") String region) {
		model.addAttribute("regionHistoryTableID", region + "HistoryTable");
		////System.out.println("TABLE ID : "+model.asMap().get("regionHistoryTableID"));
//		migrationActivity.setAuditList(oipaService.getMigratedRules(upperRegionConfig.getRegions().get(region)));
		migrationActivity.setAuditList(oipaService.getAuditList());
		model.addAttribute("migrationActivity", migrationActivity);
		return "fragments/partials :: dashboardTable";
	}

	@RequestMapping("/status")
	public String statusPage(Model model) {
		model.addAttribute("currentModule", "status");
		model.addAttribute("fragmentName", "fragments/niy");
		return "index";
	}

	@RequestMapping("/administration")
	public String administrationPage(Model model) {
		model.addAttribute("currentModule", "administration");
		model.addAttribute("fragmentName", "fragments/niy");
		return "index";
	}

	@RequestMapping("/uploadInputFile")
	public @ResponseBody String uploadInputFile(@RequestParam("inputFile") MultipartFile file) {
		parsedRules = null;
		////System.out.println("Original filename : " + file.getOriginalFilename());
		Table fileTable = tableConfig.getTables().get("-99");
		////System.out.println("Worksheet Name Configured : " + fileTable.getSelectQuery());
		try {
			parsedRules = oipaService.getAllRules(file.getInputStream(), fileTable.getSelectQuery());
			// ////System.out.println("Parsed Rules : "+rules);
			file.getInputStream().close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		} catch (RuntimeException e1) {
			throw e1;
		}

		return "success";
	}
	
	
	@RequestMapping("/compareRules")
	public String compareRules(Model model, @ModelAttribute RuleComparison ruleComparisonModel) {
		ruleComparisonModel.setDestinationName(envConfigurator.getRegionName(false));
		ruleComparisonModel.setSourceName(envConfigurator.getRegionName(true));
		oipaService.getComparisonDetail(ruleComparisonModel,tableConfig.getTables().get(ruleComparisonModel.getRuleType()));
		model.addAttribute("ruleComparisonModel",ruleComparisonModel);
		return "fragments/partials :: ruleComparison";
	}

	private Predicate<OipaRule> versionAvailable(){
		return new Predicate<OipaRule>() {
	        @Override public boolean apply(OipaRule t) {
	            return (null != t.getVersionGuid() && !"".equalsIgnoreCase(t.getVersionGuid()));
	        }
	    };
	}
	
	private Predicate<OipaRule> filterByRuleIds(final List<String> ruleGuids){
		return new Predicate<OipaRule>() {
	        @Override public boolean apply(OipaRule t) {
	            return ruleGuids.contains(t.getRuleGuid());
	        }
	    };
	}
	
}