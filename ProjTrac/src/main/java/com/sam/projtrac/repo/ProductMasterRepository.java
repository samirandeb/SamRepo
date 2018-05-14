package com.sam.projtrac.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sam.projtrac.entity.ProductMaster;

@RepositoryRestResource(collectionResourceRel = "prdMaster", path = "prdMaster")
public interface ProductMasterRepository extends CrudRepository<ProductMaster, Integer> {

}
