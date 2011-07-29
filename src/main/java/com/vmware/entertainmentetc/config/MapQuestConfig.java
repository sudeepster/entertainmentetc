package com.vmware.entertainmentetc.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.vmware.entertainmentetc.services.mapquest.MapQuestService;

@Configuration
public class MapQuestConfig {
	@Inject
	private Environment environment;
	
	@Bean
	public MapQuestService mapQuestService() {
		return new MapQuestService(environment.getProperty("mapquest.apiKey"));
	}
}
