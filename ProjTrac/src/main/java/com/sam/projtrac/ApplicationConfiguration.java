package com.sam.projtrac;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		
        registry.addResourceHandler("/custom/css/**").addResourceLocations("classpath:/css/");
        registry.addResourceHandler("/custom/js/**").addResourceLocations("classpath:/js/");
        registry.addResourceHandler("/custom/fonts/**").addResourceLocations("classpath:/fonts/");
        registry.addResourceHandler("/custom/static/**").addResourceLocations("classpath:/static/");
	}

	@Bean
	ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}

}
