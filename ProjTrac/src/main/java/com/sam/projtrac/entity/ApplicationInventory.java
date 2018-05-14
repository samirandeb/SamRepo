package com.sam.projtrac.entity;

import java.io.Serializable;

public class ApplicationInventory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String AppId;
	private String Lob;
	private String AppName;
	private String scope;
	private String technology;
	private String targetDC;
	private String roadmap;
	private String OperatingSys;
	private String ProgLug;
	private String AppServer;
	private String FrameWork;
	private String Database;
	private String Middleware;
	private String Devops;
	
	private String OperatingSysId;
	private String ProgLugId;
	private String AppServerId;
	private String FrameWorkId;
	private String DatabaseId;
	private String MiddlewareId;
	private String DevopsId;
	
	public String getMiddleware() {
		return Middleware;
	}
	public void setMiddleware(String middleware) {
		Middleware = middleware;
	}
	public String getDevops() {
		return Devops;
	}
	public void setDevops(String devops) {
		Devops = devops;
	}
	public String getMiddlewareId() {
		return MiddlewareId;
	}
	public void setMiddlewareId(String middlewareId) {
		MiddlewareId = middlewareId;
	}
	public String getDevopsId() {
		return DevopsId;
	}
	public void setDevopsId(String devopsId) {
		DevopsId = devopsId;
	}
	public String getOperatingSysId() {
		return OperatingSysId;
	}
	public void setOperatingSysId(String operatingSysId) {
		OperatingSysId = operatingSysId;
	}
	public String getProgLugId() {
		return ProgLugId;
	}
	public void setProgLugId(String progLugId) {
		ProgLugId = progLugId;
	}
	public String getAppServerId() {
		return AppServerId;
	}
	public void setAppServerId(String appServerId) {
		AppServerId = appServerId;
	}
	public String getFrameWorkId() {
		return FrameWorkId;
	}
	public void setFrameWorkId(String frameWorkId) {
		FrameWorkId = frameWorkId;
	}
	public String getDatabaseId() {
		return DatabaseId;
	}
	public void setDatabaseId(String databaseId) {
		DatabaseId = databaseId;
	}
	public String getAppId() {
		return AppId;
	}
	public void setAppId(String appId) {
		AppId = appId;
	}
	public String getLob() {
		return Lob;
	}
	public void setLob(String lob) {
		Lob = lob;
	}
	public String getAppName() {
		return AppName;
	}
	public void setAppName(String appName) {
		AppName = appName;
	}
	public String getOperatingSys() {
		return OperatingSys;
	}
	public void setOperatingSys(String operatingSys) {
		OperatingSys = operatingSys;
	}
	public String getProgLug() {
		return ProgLug;
	}
	public void setProgLug(String progLug) {
		ProgLug = progLug;
	}
	public String getAppServer() {
		return AppServer;
	}
	public void setAppServer(String appServer) {
		AppServer = appServer;
	}
	public String getFrameWork() {
		return FrameWork;
	}
	public void setFrameWork(String frameWork) {
		FrameWork = frameWork;
	}
	public String getDatabase() {
		return Database;
	}
	public void setDatabase(String database) {
		Database = database;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getTargetDC() {
		return targetDC;
	}
	public void setTargetDC(String targetDC) {
		this.targetDC = targetDC;
	}
	public String getRoadmap() {
		return roadmap;
	}
	public void setRoadmap(String roadmap) {
		this.roadmap = roadmap;
	}
	
}
