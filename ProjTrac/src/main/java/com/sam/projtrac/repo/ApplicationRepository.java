package com.sam.projtrac.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sam.projtrac.entity.Application;

@RepositoryRestResource(collectionResourceRel = "application", path = "application")
public interface ApplicationRepository extends CrudRepository<Application, Integer>{

}
