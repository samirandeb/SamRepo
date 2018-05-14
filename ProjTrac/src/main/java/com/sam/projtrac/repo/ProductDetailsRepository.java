package com.sam.projtrac.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sam.projtrac.entity.ProductDetails;

@RepositoryRestResource(collectionResourceRel = "prddtls", path = "prddtls")
public interface ProductDetailsRepository extends CrudRepository<ProductDetails, Integer> {
	List<ProductDetails> findByPrdId(@Param("prdId") Integer parentId);
	
	ProductDetails findOne(@Param("PrdtlId") Integer PrdtlId);
	
	/*@Modifying(clearAutomatically = true)
	@Query("update product_details details set details.voya_n ='N' where details.prd_Id =:prdId")
	void resetVoyaN(@Param("prdId") Integer prdId);*/
	
	/*@Modifying(clearAutomatically = true)
	@Query("update PRODUCT_DETAILS set VOYA_N ='Y' where PRDTL_ID =:prdtlId")
	void setVoyaN(@Param("prdtlId") Integer prdtlId);
	
	@Modifying(clearAutomatically = true)
	@Query("update PRODUCT_DETAILS set VOYA_N_1 ='N' where PRD_ID =:prdId")
	void resetVoyaN1(@Param("prdId") Integer prdId);
	
	@Modifying(clearAutomatically = true)
	@Query("update PRODUCT_DETAILS set VOYA_N_1 ='Y' where PRDTL_ID  in :prdtlId")
	void setVoyaN1(@Param("prdtlId") Set<Integer> prdtlId);*/

}
