package com.cts.migration.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.cts.migration.model.Region;
import com.cts.migration.model.ui.OipaMigrationActivity;
import com.cts.migration.util.MigrationUtil;

@Configuration
public class MigrationDBConfig {

	public static final String SOURCE_REGION_BEAN = "sourceRegion";
	public static final String DESTINATION_REGION_BEAN = "destinationRegion";
	public static final String DESTINATION_TX_MANAGER_BEAN = "destTransactionManager";

	@Value("${spring.app.username}")
	private String appUser;
	@Value("${spring.app.password}")
	private String appPassword;
	@Value("${spring.app.url}")
	private String appDbUrl;
	@Value("${spring.app.driverClassName}")
	private String appDriverClass;

	@Bean(name = SOURCE_REGION_BEAN)
	@Scope(value = "session")
	public Region sourceRegion() {
		//System.out.println("SourceRegion");
		return new Region();
	}

	@Bean(name = DESTINATION_REGION_BEAN)
	@Scope(value = "session")
	public Region destinationRegion() {
		//System.out.println("destinationRegion");
		return new Region();
	}

	@Bean
	@Scope(value = "session")
	public OipaMigrationActivity getMigrationActivity() {
		return new OipaMigrationActivity();
	}



	@Bean(name = "appDataSource")
	public DataSource cogIvs10DataSource() throws Exception {

		DataSource db = DataSourceBuilder.create()
										.username(MigrationUtil.decryptAES(appUser))
										.password(MigrationUtil.decryptAES(appPassword))
										.url(appDbUrl)
										.driverClassName(appDriverClass)
										.build();

//		//System.out.println("DATASOURCE : "+db);
		return db;
	}

	@Bean(name = "auditJdbcTemplate")
	public JdbcTemplate getAuditJdbcTemplate(@Qualifier("appDataSource") DataSource auditDataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(auditDataSource);
		return jdbcTemplate;
	}

    @Bean(name="appJdbcTemplate")
    public NamedParameterJdbcTemplate getAuthenticationNamedJdbcTemplate(@Qualifier("appDataSource") DataSource dataSource){
    	return new NamedParameterJdbcTemplate(dataSource);
    }
    
    @Bean(name="appTransactionManager")
    public DataSourceTransactionManager getAppTransactionManager(@Qualifier("appDataSource") DataSource dataSource){
    	return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean(name="destTransactionManager")
    public DataSourceTransactionManager getAppTransactionManager(){
    	return new DataSourceTransactionManager();
    }
}