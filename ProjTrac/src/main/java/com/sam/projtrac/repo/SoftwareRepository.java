package com.sam.projtrac.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sam.projtrac.entity.Software;

@RepositoryRestResource(collectionResourceRel = "software", path = "software")
public interface SoftwareRepository extends CrudRepository<Software, Integer>{
	
	
/*	List<Application> findByParentApp(@Param("name") String name);
	
	List<Application> findByParentId(@Param("parentId") Long parentId);
	
	@Query(value = "select ad.* from software ad)", nativeQuery=true) null as ind_prod_name , null as version, null as disposal , null as emerge,  null as select_dt,  null as ins_std,  null as contain,   null as voya_de_supp_dt,   null as retire, null as vend_n, null as vend_supp_alrt,  null as  ga_dt,  null as  end_life_dt, null as end_life_supp_lvl,  null as  obs_dt, null as obs_supp_lvl
	List<Application> findByChildApp(@Param("name") String name);*/
	@Query(value = "select round(RAND()*10000000) as prd_id,road_map, mgmt_grp, manufacturer,prod_disp_name, null as ind_prod_name , count(1) as version, null as disposal , null as emerge,  null as select_dt,  null as ins_std,  null as contain,   null as voya_de_supp_dt,   null as retire, null as vend_n, null as vend_supp_alrt,  null as  ga_dt,  null as  end_life_dt, null as end_life_supp_lvl,  null as  obs_dt, null as obs_supp_lvl, null as update_by, null as update_dt, null as voya_n, null as voya_n_1  from software group by road_map,mgmt_grp,manufacturer,prod_disp_name", nativeQuery=true)
	List<Software> findByGrouping();
	
//	@Query(value = "select prd_id,road_map, mgmt_grp, manufacturer,prod_disp_name, ind_prod_name , version, disposal ,  FORMATDATETIME(emerge, 'M/d/y')  as emerge,  FORMATDATETIME(select_dt, 'M/d/y')  as select_dt, ins_std,  contain, FORMATDATETIME(voya_de_supp_dt, 'M/d/y')  as voya_de_supp_dt, FORMATDATETIME(retire, 'M/d/y')  as retire, vend_n, vend_supp_alrt,  FORMATDATETIME(ga_dt, 'M/d/y')  as ga_dt,  FORMATDATETIME(end_life_dt, 'M/d/y')  as end_life_dt, end_life_supp_lvl, FORMATDATETIME(obs_dt, 'M/d/y')  as obs_dt, obs_supp_lvl, update_by, update_dt, voya_n, voya_n_1  from software where road_map=:roadmap and mgmt_grp=:mgmtGrp and manufacturer=:manufacturer and prod_disp_name=:prodDispName", nativeQuery=true)
	@Query(value = "select prd_id,road_map, mgmt_grp, manufacturer,prod_disp_name, ind_prod_name , version, disposal ,  emerge,   select_dt, ins_std,  contain,  voya_de_supp_dt, retire, vend_n, vend_supp_alrt,  ga_dt,  end_life_dt, end_life_supp_lvl, obs_dt, obs_supp_lvl, update_by, update_dt, voya_n, voya_n_1  from software where road_map=:roadmap and mgmt_grp=:mgmtGrp and manufacturer=:manufacturer and prod_disp_name=:prodDispName", nativeQuery=true)
	List<Software> findByGroupingRslt(@Param("roadmap") String roadmap, @Param("mgmtGrp") String mgmtGrp, @Param("manufacturer") String manufacturer, @Param("prodDispName") String prodDispName);


	@Query(value = "SELECT prd_id, prod_disp_name||DECODE(version,null,'', ' ('||version ||')') as prod_disp_name, road_map, mgmt_grp, manufacturer, null as ind_prod_name , version, disposal , emerge,  select_dt,  ins_std, contain, voya_de_supp_dt, retire, vend_n, vend_supp_alrt, ga_dt, end_life_dt, end_life_supp_lvl, obs_dt, obs_supp_lvl, update_by, update_dt, voya_n, voya_n_1 FROM SOFTWARE", nativeQuery=true)
	List<Software> findByCustSoftware();
}
