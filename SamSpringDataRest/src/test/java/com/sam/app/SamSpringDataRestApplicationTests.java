package com.sam.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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
	public void findsAllEmployee() {

		Employee emp = new Employee(11L,"Test","lTest",22L);

		emp = repository.save(emp);
		/*Employee empData = (Employee) repository.findByLname("lTest");*/
		List<Employee> empList = repository.findAll();
		
		//assertThat(empData.getSize()).isEqualTo(1);
		assertNotNull(empList.size());
	}
	
	@Test
	public void findsEmpByLatName() {

		Employee emp = new Employee(11L,"Test","lTest",22L);

		emp = repository.save(emp);
		List<Employee> empData = repository.findByLname("lTest");
		
		assertThat(empData.get(0).getLname()).isEqualTo("lTest");
	}

}
