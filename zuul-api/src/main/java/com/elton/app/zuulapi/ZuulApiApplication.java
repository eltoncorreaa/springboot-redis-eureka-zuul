package com.elton.app.zuulapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ZuulApiApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ZuulApiApplication.class, args);
	}
}
