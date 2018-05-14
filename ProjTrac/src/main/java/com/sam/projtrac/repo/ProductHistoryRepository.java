package com.sam.projtrac.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sam.projtrac.entity.ProductDetails;
import com.sam.projtrac.entity.ProductHistory;

@RepositoryRestResource(collectionResourceRel = "prd_dtls_his", path = "prd_dtls_his")
public interface ProductHistoryRepository extends CrudRepository<ProductHistory, Integer> {
	List<ProductHistory> findByPrdtlId(@Param("prdtlId") Integer prdtlId);
	

}
