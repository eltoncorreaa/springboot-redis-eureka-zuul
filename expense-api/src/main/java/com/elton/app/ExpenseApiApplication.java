package com.elton.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableDiscoveryClient
public class ExpenseApiApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ExpenseApiApplication.class, args);
	}
}
