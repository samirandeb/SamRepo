package com.sam.admin.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sam.admin.client.model.Person;

@EnableCircuitBreaker
@SpringBootApplication
public class SpringBootAdminClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAdminClientApplication.class, args);
	}
	
	List<Person> personList = new ArrayList<>();
	List<String> personName = Arrays.asList("Sam,Jhon,Arnold".split(","));
	
	@RestController
	@RequestMapping("/")
	class root {
		
		@HystrixCommand()		
		@RequestMapping(method = RequestMethod.GET)
		public String rootService(){
			return "Root Service()";
		}
		
	}
	
	@RestController
	@RequestMapping("/hello")
	class hello {
		
		@HystrixCommand()
		@RequestMapping(path="/service1",method = RequestMethod.GET)
		public String sayHello(){
			return "Hello world";
		}
		
		@HystrixCommand()		
		@RequestMapping(path="/service2",method = RequestMethod.GET)
		public String sayHi(){
			return "Hi world";
		}
	}
	
	@RestController
	@RequestMapping("/sample")
	class sampleApi {
		
		
		@HystrixCommand()		
		@RequestMapping(method = RequestMethod.GET)
		public List<Person> getsampleApi(){
			createRandomPerson();			
			return personList;
		}
	}
	
	void createRandomPerson() {
		String lNames[] = "Deb,Sarkar,Roy".split(",");
		personName.forEach(personName -> {
            personList.add(new Person(personName, lNames[new Random().nextInt(lNames.length)],new Random().nextLong()));
        });
    }
}
