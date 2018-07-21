package com.ag2m.gestimmo.metier.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ag2m.gestimmo.metier.service.ParametrageService;

@Component
public class GestimmoContextLoadedListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	ParametrageService parametrageService;
	

	/**
	 * Ecoute le chargement du contexte,
	 * puis initialise les taxes après celui-ci
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		parametrageService.loadCurrentTaxe();
		
	}
	
}
