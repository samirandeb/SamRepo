package com.sam.projtrac.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sam.projtrac.entity.Application;
import com.sam.projtrac.entity.ApplicationDetails;

@RepositoryRestResource(collectionResourceRel = "app_detl", path = "app_detl")
public interface ApplicationDetailsRepo extends CrudRepository<ApplicationDetails, Integer>{

}
