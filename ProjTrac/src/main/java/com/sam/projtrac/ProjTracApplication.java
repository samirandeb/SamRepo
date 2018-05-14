package com.sam.projtrac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ProjTracApplication {
 public static void main(String[] args) {
  SpringApplication.run(ProjTracApplication.class, args);
	 /*new SpringApplicationBuilder(ProjTracApplication.class)
	    .properties("spring.config.name=band")
	    .run(args);*/
 }
}
