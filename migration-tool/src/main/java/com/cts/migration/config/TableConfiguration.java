package com.cts.migration.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.cts.migration.model.Table;

@Configuration
@ConfigurationProperties(locations = "classpath:table.yml", prefix = "table")
public class TableConfiguration {

	private List<Table> ruleTables;
	private Map<String, Table> tables;

	public List<Table> getRuleTables() {
		return ruleTables;
	}

	public void setRuleTables(List<Table> ruleTables) {
		this.ruleTables = ruleTables;
	}

	public Map<String, Table> getTables() {
		return tables;
	}

	@PostConstruct
	void setUp() {
		//System.out.println("RuleTables : " + ruleTables);
		this.tables = new LinkedHashMap<String,Table>();
		for (Table table : ruleTables) {
			tables.put(table.getRuleType(), table);
		}
		//System.out.println("RuleMap : " + tables);
	}

}
