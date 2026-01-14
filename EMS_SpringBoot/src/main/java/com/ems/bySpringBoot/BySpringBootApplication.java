package com.ems.bySpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BySpringBootApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		//System.setProperty("server.servlet.context-path", "/");
		SpringApplication.run(BySpringBootApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BySpringBootApplication.class);
	}
}
