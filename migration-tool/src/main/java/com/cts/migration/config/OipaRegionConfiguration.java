package com.cts.migration.config;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.cts.migration.model.ui.OipaRegion;

//@Configuration
//@ConfigurationProperties(locations = "classpath:oipa_region.yml", prefix = "oipa")
public class OipaRegionConfiguration implements InitializingBean {

	// private String allSource;
	// private String allDestination;
	private List<OipaRegion> sourceRegions;
	private List<OipaRegion> destinationRegions;

//	public List<OipaRegion> getSourceRegions() {
//		return sourceRegions;
//	}
//
//	public void setSourceRegions(List<OipaRegion> sourceRegions) {
//		this.sourceRegions = sourceRegions;
//	}
//
//	public List<OipaRegion> getDestinationRegions() {
//		return destinationRegions;
//	}
//
//	public void setDestinationRegions(List<OipaRegion> destinationRegions) {
//		this.destinationRegions = destinationRegions;
//	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//System.out.println("Sources : " + sourceRegions);
		//System.out.println("Destination : " + destinationRegions);
	}

}
