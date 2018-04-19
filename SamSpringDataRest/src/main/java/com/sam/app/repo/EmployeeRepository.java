package com.sam.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.sam.app.model.Employee;

/*@RepositoryRestResource(collectionResourceRel = "employee", path = "employee")*/
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByLname(@Param("name") String name);

}
