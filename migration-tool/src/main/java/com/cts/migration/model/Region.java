package com.cts.migration.model;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class Region {
	
	
	private String regionIdentifier;

	private DataSource pasDataSource;
	private DataSource ivsDataSource;
	private JdbcTemplate pasJdbcTemplate;
	private JdbcTemplate ivsJdbcTemplate;

	private NamedParameterJdbcTemplate ivsNamedJdbcTemplate;
	private NamedParameterJdbcTemplate pasNamedJdbcTemplate;

	public DataSource getPasDataSource() {
		return pasDataSource;
	}

	public void setPasDataSource(DataSource pasDataSource) {
		this.pasDataSource = pasDataSource;
		if (pasDataSource != null) {
			if (pasJdbcTemplate == null) {
				pasJdbcTemplate = new JdbcTemplate(pasDataSource);
				
			} else {
				pasJdbcTemplate.setDataSource(pasDataSource);
				
			}
			pasNamedJdbcTemplate = new NamedParameterJdbcTemplate(pasDataSource);
		}else{
			pasNamedJdbcTemplate = null;
		}
	}

	public DataSource getIvsDataSource() {
		return ivsDataSource;
	}

	public void setIvsDataSource(DataSource ivsDataSource) {
		this.ivsDataSource = ivsDataSource;
		if (ivsDataSource != null) {
			if (ivsJdbcTemplate == null) {
				ivsJdbcTemplate = new JdbcTemplate(ivsDataSource);
			} else {
				ivsJdbcTemplate.setDataSource(ivsDataSource);
			}
			ivsNamedJdbcTemplate = new NamedParameterJdbcTemplate(ivsDataSource);
		} else {
			ivsNamedJdbcTemplate = null;
		}
	}

	public JdbcTemplate getPasJdbcTemplate() {
		return pasJdbcTemplate;
	}

	public JdbcTemplate getIvsJdbcTemplate() {
		return ivsJdbcTemplate;
	}

	public NamedParameterJdbcTemplate getIvsNamedJdbcTemplate() {
		return ivsNamedJdbcTemplate;
	}

	public NamedParameterJdbcTemplate getPasNamedJdbcTemplate() {
		return pasNamedJdbcTemplate;
	}

	public boolean hasIVS() {
		return ivsDataSource==null?false:true;
	}

	public String getRegionIdentifier() {
		return regionIdentifier;
	}

	public void setRegionIdentifier(String regionIdentifier) {
		this.regionIdentifier = regionIdentifier;
	}
}
