package com.ag2m.gestimmo.metier.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.service.ParametrageService;

@Component
public class GestimmoPostPorcessor {

	@Autowired
	ParametrageService parametrageService;
	
	@PostConstruct
	private void initTaxe() throws FunctionalException{
		parametrageService.loadTaxe();
	}
	
}
