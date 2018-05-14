package com.sam.projtrac.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.sam.projtrac.entity.ApplicationInventory;


@Repository
public class ProjTracDAO {
	
	@Autowired
	DataSource dataSource;	
	ApplicationInventory appInv;
	
	
	public int insertApplication(Map<String, String> paramMap){
		//String hql = "FROM Article as atcl ORDER BY atcl.articleId";
		//System.out.println(paramMap.get("name"));
	//	 (List<Object>) entityManager.createQuery(hql).getResultList();
		 //JdbcTemplate template = new JdbcTemplate(dataSource);
        // int row = template.update(insertSql, params, types);
/*         KeyHolder keyHolder = new GeneratedKeyHolder();
         template.update("insert into application(name, lob) values(?, ?)", new Object[]{paramMap.get("name"),paramMap.get("lob")}, keyHolder);
         int keyVal =  keyHolder.getKey().intValue();
         System.out.println(" row inserted.:"+keyVal);*/
		 SimpleJdbcInsert insertApp =
	                new SimpleJdbcInsert(dataSource)
	                        .withTableName("application")
	                        .usingColumns("name", "lob","scope","technology","targetDC")
	                        .usingGeneratedKeyColumns("app_id");
		// paramMap.put("score", "0");
		 Number newId = insertApp.executeAndReturnKey(paramMap);
		// List<Map<String, Object>> list =  template.queryForList("select count(1) from software");
		return (int) newId;
	}
	
	public void updateApplication(Map<String, String> paramMap) {
		
		JdbcTemplate template = new JdbcTemplate(dataSource);
		template.update("UPDATE APPLICATION SET LOB = ?,SCOPE = ?,TARGETDC = ?,TECHNOLOGY =? WHERE APP_ID = ? ",
				paramMap.get("lob"), paramMap.get("scope"), paramMap.get("targetDC"), paramMap.get("technology"),
				paramMap.get("appid"));

	}

	public void insertApplicationDetl(int id, List<List<String>> listDetlTable) {
		 String sql = "INSERT INTO app_details " +
					"(app_id, type, prd_id, target) VALUES (?, ?, ?, select max(prd_dtls.version) as target from product_details prd_dtls where prd_dtls.disposal='Install Standard' and prd_dtls .PRD_ID in (select prd_dtls1.prd_id from product_details prd_dtls1 where prd_dtls1.prdtl_id=?))";
		 String deleteSql = "delete from app_details where app_id="+id;
		 
		 JdbcTemplate template = new JdbcTemplate(dataSource);
		 template.execute(deleteSql);
		 System.out.println("listDetlTable-----"+listDetlTable);
		 template.batchUpdate(sql, new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						List<String> row= listDetlTable.get(i);
						ps.setLong(1, id);
						ps.setString(2, row.get(0));
						ps.setLong(3, Long.parseLong(row.get(1).toString()));
						ps.setLong(4, Long.parseLong(row.get(1).toString()));
					}

					@Override
					public int getBatchSize() {
						return listDetlTable.size();
					}
				  });
		
	}
	
/*	public void evaluate(String appId){
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "select  app.type, app.prd_id, decode(soft.voya_n,'y',100,decode(soft.voya_n_1,'y',80,20)) as score from app_details app  left join software soft on app.prd_id=soft.prd_id where app.app_id="+appId;
		List<Map<String,Object>> resultList = template.queryForList(sql);
		for(Map<String,Object> map: resultList){
			template.update("update app_details set score="+map.get("score")+" where type='"+map.get("type")+"' and prd_id="+map.get("prd_id")+" and app_id="+appId);
		}
		String sql1 = " update application set score =( select nvl(sum(score)/ count(1),0) from (select  app.app_id, app.type, app.prd_id, soft.voya_n, soft.voya_n_1, decode(soft.voya_n,'y',100,decode(soft.voya_n_1,'y',80,20)) as score from app_details app  left join software soft on app.prd_id=soft.prd_id where app.app_id="+appId+")) where app_id="+appId;
		template.update(sql1);
	}*/
	
	public List getAppInventoryData(String prodId) {
		ArrayList<ApplicationInventory> appInvData = new ArrayList<ApplicationInventory>();
		String appId = "0000";
		String prodName = null;
		String sql;
		appInv =  new ApplicationInventory();
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String tag1 = "<font color=\"#ff6600\">";
		String tag2 ="</font>";
		if(appId.equals(prodId)) {
			//sql = "select a.APP_ID ,a.LOB ,a.NAME ,d.TYPE ,p.IND_PROD_NAME,p.PRD_ID  from APPLICATION a,APP_DETAILS d,PRODUCT_DETAILS p where a.APP_ID = d.APP_ID  and d.PRD_ID = p.PRDTL_ID";
			sql = "select a.APP_ID ,a.LOB,a.SCOPE ,a.TECHNOLOGY ,a.TARGETDC ,a.NAME ,d.TYPE ,p.IND_PROD_NAME,p.PRD_ID,p.VERSION  from APPLICATION a  left join APP_DETAILS d on a.APP_ID = d.APP_ID left join PRODUCT_DETAILS  p on d.PRD_ID = p.PRDTL_ID";
		}else {
			sql = "select a.APP_ID ,a.LOB,a.SCOPE ,a.TECHNOLOGY ,a.TARGETDC ,a.NAME ,d.TYPE ,p.IND_PROD_NAME,p.PRDTL_ID as PRD_ID,p.VERSION from APPLICATION a,APP_DETAILS d,PRODUCT_DETAILS p where a.APP_ID = d.APP_ID  and d.PRD_ID = p.PRDTL_ID and d.APP_ID in (select distinct app_id from APP_DETAILS where prd_id ="+ prodId +")";
		}
		 System.out.println("sql---"+sql);
		List<Map<String,Object>> resultList = template.queryForList(sql);
		for(Map<String,Object> map: resultList){
			String version = map.get("VERSION")==null || map.get("VERSION").toString().equalsIgnoreCase("NA")?"":" ("+ map.get("VERSION") +")";
			if(appId.equals("0000") || appId.equals(map.get("APP_ID").toString())) {
				if(appInv.getAppId() == null) {
					appInv.setAppId(map.get("APP_ID").toString());
					appInv.setAppName(map.get("NAME").toString());
					appInv.setLob(map.get("LOB").toString());
					appInv.setScope(map.get("SCOPE") == null? "":map.get("SCOPE").toString());
					appInv.setTechnology(map.get("TECHNOLOGY") == null? "":map.get("TECHNOLOGY").toString());
					appInv.setTargetDC(map.get("TARGETDC") == null? "":map.get("TARGETDC").toString());
				}
				
				if(map.get("TYPE") != null && map.get("TYPE").toString().equals("os")){
					if(appInv.getOperatingSys() != null && appInv.getOperatingSys().length()>0) {
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setOperatingSys(appInv.getOperatingSys().toString()+ " , "+tag1+map.get("IND_PROD_NAME").toString()+version+tag2); 
						}else {
							appInv.setOperatingSys(appInv.getOperatingSys().toString()+ " , "+map.get("IND_PROD_NAME").toString()+version); 
						}
					}else {
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setOperatingSys(tag1+map.get("IND_PROD_NAME").toString()+version+tag2);
						}else {
							appInv.setOperatingSys(map.get("IND_PROD_NAME").toString()+version);
						}
					}
				}
				
				if((map.get("TYPE") != null && map.get("TYPE").toString().equals("pl"))){
					if(appInv.getProgLug() != null && appInv.getProgLug().length()>0) {
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setProgLug(appInv.getProgLug().toString()+ " , "+tag1+map.get("IND_PROD_NAME").toString()+version+tag2);
						}else {
							appInv.setProgLug(appInv.getProgLug().toString()+ " , "+map.get("IND_PROD_NAME").toString()+version); 
						}
					}else {
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setProgLug(tag1+map.get("IND_PROD_NAME").toString()+version+tag2);
						}else {
							appInv.setProgLug(map.get("IND_PROD_NAME").toString()+version);
						}
					}
				}
				
				if((map.get("TYPE") != null && map.get("TYPE").toString().equals("db"))){
					if(appInv.getDatabase() != null && appInv.getDatabase().length()>0) {
						
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setDatabase(appInv.getDatabase().toString()+ " , "+tag1+map.get("IND_PROD_NAME").toString()+version+tag2); 
						}else {
							appInv.setDatabase(appInv.getDatabase().toString()+ " , "+map.get("IND_PROD_NAME").toString()+version); 
						}
					}else {
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setDatabase(tag1+map.get("IND_PROD_NAME").toString()+version+tag2); 
							
						}else {
							appInv.setDatabase(map.get("IND_PROD_NAME").toString()+version); 
							}
					}
				}
				
				if((map.get("TYPE") != null && map.get("TYPE").toString().equals("as"))){
					if(appInv.getAppServer() != null && appInv.getAppServer().length()>0) {
						
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setAppServer(appInv.getAppServer().toString()+ " , "+tag1+map.get("IND_PROD_NAME").toString()+version+tag2); 
						}else {
							appInv.setAppServer(appInv.getAppServer().toString()+ " , "+map.get("IND_PROD_NAME").toString()+version);  
						}
					}else {
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setAppServer(tag1+map.get("IND_PROD_NAME").toString()+version+tag2); 
							
						}else {
							appInv.setAppServer(map.get("IND_PROD_NAME").toString()+version); 
						}
					}
				}
				
				if((map.get("TYPE") != null && map.get("TYPE").toString().equals("framework"))){
					if(appInv.getFrameWork() != null && appInv.getFrameWork().length()>0) {
						
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setFrameWork(appInv.getFrameWork().toString()+ " , "+tag1+map.get("IND_PROD_NAME").toString()+version+tag2); 
						}else {
							appInv.setFrameWork(appInv.getFrameWork().toString()+ " , "+map.get("IND_PROD_NAME").toString()+version);  
						}
					}else {
					 
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setFrameWork(tag1+map.get("IND_PROD_NAME").toString()+version+tag2);
							
						}else {
							appInv.setFrameWork(map.get("IND_PROD_NAME").toString()+version);
						}
					}
				}
				appId =  map.get("APP_ID").toString();
				
			}else if(!appId.equals(map.get("APP_ID").toString())) {
				
				appInvData.add(appInv);
				appInv = new ApplicationInventory();
				
				if(appInv.getAppId() == null) {
					appInv.setAppId(map.get("APP_ID").toString());
					appInv.setAppName(map.get("NAME").toString());
					appInv.setLob(map.get("LOB").toString());
					appInv.setScope(map.get("SCOPE") == null? "":map.get("SCOPE").toString());
					appInv.setTechnology(map.get("TECHNOLOGY") == null? "":map.get("TECHNOLOGY").toString());
					appInv.setTargetDC(map.get("TARGETDC") == null? "":map.get("TARGETDC").toString());
					}
				
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("os"))){
						
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setOperatingSys(tag1+map.get("IND_PROD_NAME").toString()+version+tag2);
						}else {
							appInv.setOperatingSys(map.get("IND_PROD_NAME").toString()+version);
						}
					}
					
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("pl"))){
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setProgLug(tag1+map.get("IND_PROD_NAME").toString()+version+tag2);
						}else {
							appInv.setProgLug(map.get("IND_PROD_NAME").toString()+version);
						}
					}
					
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("db"))){
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setDatabase(tag1+map.get("IND_PROD_NAME").toString()+version+tag2); 
						}else {
							appInv.setDatabase(map.get("IND_PROD_NAME").toString()+version); 
						} 
					}
					
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("as"))){
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setAppServer(tag1+map.get("IND_PROD_NAME").toString()+version+tag2); 
							
						}else {
							appInv.setAppServer(map.get("IND_PROD_NAME").toString()+version); 
						} 
					}
					
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("framework"))){
						if(prodId.equals(map.get("PRD_ID").toString())) {
							appInv.setFrameWork(tag1+map.get("IND_PROD_NAME").toString()+version+tag2);
							
						}else {
							appInv.setFrameWork(map.get("IND_PROD_NAME").toString()+version);
						} 
					}
				
				appId =  map.get("APP_ID").toString();
			}
			
		}
		appInvData.add(appInv);
		
		return appInvData;
		
	}
	
	public List getAppInventoryExistData(Map<String, String> paramMap) {
		ArrayList<ApplicationInventory> appInvData = new ArrayList<ApplicationInventory>();
		List<Map<String,Object>> resultList;
		String appId = "0000";
		appInv =  new ApplicationInventory();
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "select a.APP_ID ,a.LOB,a.SCOPE ,a.TECHNOLOGY ,a.TARGETDC ,a.NAME ,d.TYPE ,p.PRDTL_ID as PRD_ID,p.IND_PROD_NAME  from APPLICATION a,APP_DETAILS d,PRODUCT_DETAILS p where a.APP_ID = d.APP_ID  and d.PRD_ID = p.PRDTL_ID and a.APP_ID="+paramMap.get("id").toString();
		//String sql = "select a.APP_ID ,a.LOB ,a.NAME ,d.TYPE ,p.IND_PROD_NAME,p.PRD_ID  from APPLICATION a  left join APP_DETAILS d on a.APP_ID = d.APP_ID left join PRODUCT_DETAILS  p on d.PRD_ID = p.PRDTL_ID and a.APP_ID="+paramMap.get("id").toString();
		String sql1 = "select a.APP_ID ,a.LOB,a.SCOPE ,a.TECHNOLOGY ,a.TARGETDC ,a.NAME from APPLICATION a where a.app_id="+paramMap.get("id").toString();
		resultList = template.queryForList(sql);
		if(resultList.size() == 0) {
			resultList = template.queryForList(sql1);
		}
		for(Map<String,Object> map: resultList){
			
			if(appId.equals("0000") || appId.equals(map.get("APP_ID").toString())) {
				if(appInv.getAppId() == null) {
				appInv.setAppId(map.get("APP_ID").toString());
				appInv.setAppName(map.get("NAME").toString());
				appInv.setLob(map.get("LOB").toString());
				appInv.setScope(map.get("SCOPE") == null? "":map.get("SCOPE").toString());
				appInv.setTechnology(map.get("TECHNOLOGY") == null? "":map.get("TECHNOLOGY").toString());
				appInv.setTargetDC(map.get("TARGETDC") == null? "":map.get("TARGETDC").toString());
				}
				if((map.get("TYPE") != null && map.get("TYPE").toString().equals("os"))){
					if(appInv.getOperatingSys() != null && appInv.getOperatingSys().length()>0) {
						
						appInv.setOperatingSys(appInv.getOperatingSys().toString()+ " , "+map.get("IND_PROD_NAME").toString()); 
						appInv.setOperatingSysId(appInv.getOperatingSysId().toString()+ " , "+map.get("PRD_ID").toString());
					}else {
					appInv.setOperatingSys(map.get("IND_PROD_NAME").toString());
					appInv.setOperatingSysId(map.get("PRD_ID").toString()); 
					}
				}
				if((map.get("TYPE") != null && map.get("TYPE").toString().equals("pl"))){
					if(appInv.getProgLug() != null && appInv.getProgLug().length()>0) {
						appInv.setProgLug(appInv.getProgLug().toString()+ " , "+map.get("IND_PROD_NAME").toString());
						appInv.setProgLugId(appInv.getProgLugId().toString()+ " , "+map.get("PRD_ID").toString()); 
					}else {
					appInv.setProgLug(map.get("IND_PROD_NAME").toString());
					appInv.setProgLugId(map.get("PRD_ID").toString());
					}
				}
				if((map.get("TYPE") != null && map.get("TYPE").toString().equals("db"))){
					if(appInv.getDatabase() != null && appInv.getDatabase().length()>0) {
						appInv.setDatabase(appInv.getDatabase().toString()+ " , "+map.get("IND_PROD_NAME").toString());
						appInv.setDatabaseId(appInv.getDatabaseId().toString()+ " , "+map.get("PRD_ID").toString()); 
					}else {
					appInv.setDatabase(map.get("IND_PROD_NAME").toString());
					appInv.setDatabaseId(map.get("PRD_ID").toString()); 
					}
				}
				if((map.get("TYPE") != null && map.get("TYPE").toString().equals("as"))){
					if(appInv.getAppServer() != null && appInv.getAppServer().length()>0) {
						appInv.setAppServer(appInv.getAppServer().toString()+ " , "+map.get("IND_PROD_NAME").toString());
						appInv.setAppServerId(appInv.getAppServerId().toString()+ " , "+map.get("PRD_ID").toString()); 
					}else {
					appInv.setAppServer(map.get("IND_PROD_NAME").toString());
					appInv.setAppServerId(map.get("PRD_ID").toString()); 
					}
				}
				if((map.get("TYPE") != null && map.get("TYPE").toString().equals("framework"))){
					if(appInv.getFrameWork() != null && appInv.getFrameWork().length()>0) {
						appInv.setFrameWork(appInv.getFrameWork().toString()+ " , "+map.get("IND_PROD_NAME").toString());
						appInv.setFrameWorkId(appInv.getFrameWorkId().toString()+ " , "+map.get("PRD_ID").toString()); 
					}else {
					appInv.setFrameWork(map.get("IND_PROD_NAME").toString());
					appInv.setFrameWorkId(map.get("PRD_ID").toString()); 
					}
				}
				if((map.get("TYPE") != null && map.get("TYPE").toString().equals("middleware"))){
					if(appInv.getMiddleware() != null && appInv.getMiddleware().length()>0) {
						appInv.setMiddleware(appInv.getMiddleware().toString()+ " , "+map.get("IND_PROD_NAME").toString());
						appInv.setMiddlewareId(appInv.getMiddlewareId().toString()+ " , "+map.get("PRD_ID").toString()); 
					}else {
					appInv.setMiddleware(map.get("IND_PROD_NAME").toString());
					appInv.setMiddlewareId(map.get("PRD_ID").toString()); 
					}
				}
				if((map.get("TYPE") != null && map.get("TYPE").toString().equals("devops"))){
					if(appInv.getDevops() != null && appInv.getDevops().length()>0) {
						appInv.setDevops(appInv.getDevops().toString()+ " , "+map.get("IND_PROD_NAME").toString());
						appInv.setDevopsId(appInv.getDevopsId().toString()+ " , "+map.get("PRD_ID").toString()); 
					}else {
					appInv.setDevops(map.get("IND_PROD_NAME").toString());
					appInv.setDevopsId(map.get("PRD_ID").toString()); 
					}
				}

				
				appId =  map.get("APP_ID").toString();
			}else if(!appId.equals(map.get("APP_ID").toString())) {
				appInvData.add(appInv);
				appInv = new ApplicationInventory();
				
				if(appInv.getAppId() == null) {
					appInv.setAppId(map.get("APP_ID").toString());
					appInv.setAppName(map.get("NAME").toString());
					appInv.setLob(map.get("LOB").toString());
					appInv.setScope(map.get("SCOPE") == null? "":map.get("SCOPE").toString());
					appInv.setTechnology(map.get("TECHNOLOGY") == null? "":map.get("TECHNOLOGY").toString());
					appInv.setTargetDC(map.get("TARGETDC") == null? "":map.get("TARGETDC").toString());
					}
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("os"))){
						appInv.setOperatingSys(map.get("IND_PROD_NAME").toString()); 
						appInv.setOperatingSysId(map.get("PRD_ID").toString()); 
					}
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("pl"))){
						appInv.setProgLug(map.get("IND_PROD_NAME").toString()); 
						appInv.setProgLugId(map.get("PRD_ID").toString());
					}
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("db"))){
						appInv.setDatabase(map.get("IND_PROD_NAME").toString()); 
						appInv.setDatabaseId(map.get("PRD_ID").toString()); 
					}
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("as"))){
						appInv.setAppServer(map.get("IND_PROD_NAME").toString()); 
						appInv.setAppServerId(map.get("PRD_ID").toString());
					}
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("framework"))){
						appInv.setFrameWork(map.get("IND_PROD_NAME").toString()); 
						appInv.setFrameWorkId(map.get("PRD_ID").toString()); 
					}
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("middleware"))){
						
						appInv.setMiddleware(map.get("IND_PROD_NAME").toString());
						appInv.setMiddlewareId(map.get("PRD_ID").toString()); 
						
					}
					if((map.get("TYPE") != null && map.get("TYPE").toString().equals("devops"))){
						
						appInv.setDevops(map.get("IND_PROD_NAME").toString());
						appInv.setDevopsId(map.get("PRD_ID").toString()); 
						
					}
				
				appId =  map.get("APP_ID").toString();
			}
			
		}
		appInvData.add(appInv);
		System.out.println("appInv--->"+appInvData.get(0).getAppId());
		return appInvData;
	}
	
	public List<Map<String,Object>> getApplicationDetails(int appId){
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "SELECT  app_dtls.prd_id,app_dtls.app_id, app_dtls.app_dtls__id,app_dtls.type as type, prd_dtls.ind_prod_name as value, prd_dtls.ins_std as installStandard,  app_dtls.comment,  app_dtls.target, prd_dtls.version as currVer, (SELECT inr1.version FROM product_details inr1 WHERE inr1.prd_id=prd_dtls.prd_id AND inr1.voya_n='Y') AS itt_n, (SELECT GROUP_CONCAT(inr2.version ORDER BY inr2.version SEPARATOR ', ') FROM product_details inr2 WHERE inr2.prd_id=prd_dtls.prd_id and inr2.voya_n='Y' GROUP BY inr2.prd_id) AS itt_n_1, (SELECT GROUP_CONCAT(inr2.version ORDER BY inr2.version SEPARATOR ',') FROM product_details inr2 WHERE inr2.prd_id=prd_dtls.prd_id GROUP BY inr2.prd_id) AS all_ver FROM app_details app_dtls LEFT JOIN product_details prd_dtls ON app_dtls.prd_id=prd_dtls.prdtl_id WHERE app_dtls.APP_ID="+appId+" ORDER BY app_dtls.type";
		List<Map<String,Object>> resultList = template.queryForList(sql);
		/*for(Map<String,Object> map: resultList){
			
		}*/
		return resultList;
		
	}
	
	/**
	 * Retrieves the currency data for all applications
	 * @return
	 */
	public List<Map<String,Object>> getAllAppsCurrencyDetails(){
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "SELECT  app_id,lob,name,status,technology from application";
		List<Map<String,Object>> resultList = template.queryForList(sql);
		return resultList;		
	}

}
