package com.sam.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sam.app.model.Employee;
import com.sam.app.repo.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SamSpringDataRestApplicationTests {

	@Autowired EmployeeRepository repository;

	@Before
	@After
	public void clearDb() {
		repository.deleteAll();
	}

	@Test
	public void findsStoresByLocation() {

		Employee emp = new Employee(11L,"Test","lTest",22L);

		emp = repository.save(emp);
		//Page<Employee> empData = (Page<Employee>) repository.findByLname("lTest");
		
		//assertThat(empData.getContent());
	}

}
