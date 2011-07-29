package com.vmware.entertainmentetc.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.vmware.entertainmentetc.services.bestbuy.BestBuyService;

@Configuration
public class BestBuyConfig {
	@Inject
	private Environment environment;
	
	@Bean
	public BestBuyService bestBuyService() {
		return new BestBuyService(environment.getProperty("bestbuy.apiKey"));
	}
}
