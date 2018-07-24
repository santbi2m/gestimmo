package com.ag2m.gestimmo.metier.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.service.ParametrageService;
import com.ag2m.gestimmo.metier.service.TaxeService;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class GestimmoContextLoadedListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	TaxeService taxeService;
	
	@Autowired
	ParametrageService parametrageService;
	

	/**
	 * Ecoute le chargement du contexte,
	 * puis initialise les valeurs paramétrées en BDD. Exemple les taxes
	 * les remises etc...
	 * 
	 * */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		try {
				log.info("Chargement des valeurs paramétrées au démarrage de l'application");
				
				//Chargement de la taxe courrante
				taxeService.loadCurrentTaxe();
				
				//Chargement du seuil d'annulation gratuite
				parametrageService.loadSeuilAnnulationGratuite();
				
				//Chargement de la pénalité à appliquer suite à une annulation tardive de réservation 
				parametrageService.loadPourcentagePenanlite();
				
				//Chargement des remises autorisées.
				parametrageService.loadAllRemise();
				
		} catch (TechnicalException e) {
			log.error("Chargement des valeurs paramétrées au démarrage de l'application", e);
		}
	}
	
}
