package com.sam.projtrac.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sam.projtrac.entity.ProductAudit;

@RepositoryRestResource(collectionResourceRel = "prdAudit", path = "prdAudit")
public interface ProductAuditRepository extends CrudRepository<ProductAudit, Integer> {
	
	List<ProductAudit> findByPrdId(@Param("prdId") Integer prdId);

}
