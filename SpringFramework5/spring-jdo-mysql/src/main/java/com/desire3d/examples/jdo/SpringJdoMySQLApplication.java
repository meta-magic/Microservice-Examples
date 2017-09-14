package com.desire3d.examples.jdo;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * @author Mahesh Pardeshi
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringJdoMySQLApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJdoMySQLApplication.class, args);
	}
	
	@Bean
	public PersistenceManagerFactory persistenceManagerFactory() {
		PersistenceManagerFactory PERSISTENCE_MANAGER_FACTORY = JDOHelper.getPersistenceManagerFactory("PersistenceUnit");
		return PERSISTENCE_MANAGER_FACTORY;
	}
}