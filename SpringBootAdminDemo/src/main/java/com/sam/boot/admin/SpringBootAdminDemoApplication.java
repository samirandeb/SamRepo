package com.sam.boot.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAdminDemoApplication.class, args);
	}
}
