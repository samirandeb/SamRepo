package com.cts.migration.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.cts.migration.model.UpperMigrationRegion;

@Configuration
@ConfigurationProperties(locations = "classpath:upperMigrationRegion.yml", prefix = "region")
public class UpperMigrationRegionConfig {

	private List<UpperMigrationRegion> upperMigrationRegions;
	private Map<String, UpperMigrationRegion> regions;

	public List<UpperMigrationRegion> getUpperMigrationRegions() {
		return upperMigrationRegions;
	}

	public void setUpperMigrationRegions(List<UpperMigrationRegion> upperMigrationRegions) {
		this.upperMigrationRegions = upperMigrationRegions;
	}

	public Map<String, UpperMigrationRegion> getRegions() {
		return regions;
	}

	public void setRegions(Map<String, UpperMigrationRegion> regions) {
		this.regions = regions;
	}

	@PostConstruct
	void setUp() {
		//System.out.println("Higher Regions : " + upperMigrationRegions);
		this.regions = new LinkedHashMap<String, UpperMigrationRegion>();
		for (UpperMigrationRegion region : upperMigrationRegions) {
			regions.put(region.getName(), region);
		}

		//System.out.println("Region Map : " + regions);
	}
}
