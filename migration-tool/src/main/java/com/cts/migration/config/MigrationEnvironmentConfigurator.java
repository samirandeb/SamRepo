package com.cts.migration.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import com.cts.migration.dao.OipaRegionDao;
import com.cts.migration.model.OmtRegion;
import com.cts.migration.model.Region;
import com.cts.migration.model.ui.OipaRegion;
import com.cts.migration.util.MigrationUtil;

@Component
public class MigrationEnvironmentConfigurator implements InitializingBean {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private OipaRegionDao oipaRegionDao;

	private OipaRegion sandboxRegion;

	private Map<String, DataSource> pasDataSources = new HashMap<String, DataSource>();
	private Map<String, DataSource> ivsDataSources = new HashMap<String, DataSource>();
	private Map<String, String> regionMap = new HashMap<>();

	public void setRegion(String type, String regionName) {
		Region region = type.equalsIgnoreCase("S") ? (Region) context.getBean(MigrationDBConfig.SOURCE_REGION_BEAN)
				: (Region) context.getBean(MigrationDBConfig.DESTINATION_REGION_BEAN);

		DataSource pasDS = pasDataSources.get(regionName);
		DataSource ivsDS = ivsDataSources.get(regionName);
		region.setRegionIdentifier(regionName);
		region.setPasDataSource(pasDS);
		region.setIvsDataSource(ivsDS);
		if(type.equalsIgnoreCase("D")){
			((DataSourceTransactionManager) context.getBean(MigrationDBConfig.DESTINATION_TX_MANAGER_BEAN)).setDataSource(ivsDS);
		}
		
	}
	
	public String getRegionName(boolean isSource){
		Region region = isSource ? (Region) context.getBean(MigrationDBConfig.SOURCE_REGION_BEAN)
				: (Region) context.getBean(MigrationDBConfig.DESTINATION_REGION_BEAN);
		return region.getRegionIdentifier();
	}

	public OipaRegion getSandboxRegion() {
		return sandboxRegion;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("GENERATE ALL THE CONFIGURED DATA SOURCE ...");
		List<OmtRegion> omtRegions = oipaRegionDao.getAll();
		for (OmtRegion region : omtRegions) {
			// check whether a sandbox
			regionMap.put(region.getIdentifier(), region.getId());
			if (region.isSandBox()) {
				sandboxRegion = new OipaRegion();
				sandboxRegion.setNonIvsRegion(region.isNonIvs());
				sandboxRegion.setRegionName(region.getIdentifier());
				sandboxRegion.setRegionDescription(region.getDescription());
			}

			// create datasource and put in pas and
			String url = "jdbc:oracle:thin:@//" + region.getPasHost() + ":"	+ region.getPasPort() + "/" + region.getPasSid();
			
			
			DataSource pasDs = DataSourceBuilder.create()
					.username(MigrationUtil.decryptAES(region.getPasUser()))
					.password(MigrationUtil.decryptAES(region.getPasPassword()))
//					.url("jdbc:oracle:thin:@//10.155.140.210:1521/CIPOIPA")
					.url(url)
					.driverClassName("oracle.jdbc.OracleDriver").build();
			pasDataSources.put(region.getIdentifier(), pasDs);
			if (!region.isNonIvs()) {
				url = "jdbc:oracle:thin:@//" + region.getIvsHost() + ":"+ region.getIvsPort() + "/" + region.getIvsSid();
				DataSource ivsDs = DataSourceBuilder.create().username(MigrationUtil.decryptAES(region.getIvsUser()))
						.password(MigrationUtil.decryptAES(region.getIvsPassword()))
						.url(url)
//						.url("jdbc:oracle:thin:@//10.155.140.210:1521/CIPOIPA")
						.driverClassName("oracle.jdbc.OracleDriver").build();
				ivsDataSources.put(region.getIdentifier(), ivsDs);
			}

		}
	}

	public Map<String, String> getRegionMap() {
		return regionMap;
	}
	
	

}
