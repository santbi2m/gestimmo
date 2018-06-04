package com.ag2m.gestimmo.metier.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.config.ParamConfig;
import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dao.ParametrageDao;
import com.ag2m.gestimmo.metier.entite.referentiel.Taxe;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.service.ParametrageService;

import lombok.extern.log4j.Log4j;

@Log4j
@Service("ParametrageService")
public class ParametrageServiceImpl implements ParametrageService {

	@Autowired
	ParametrageDao parametrageDao;
	
	
	@Override
	@Transactional
	public void loadTaxe() throws FunctionalException {
		
		log.info("Chargement du parapétrage des Taxes " );
		
		Taxe taxe = parametrageDao.loadTaxe();
		
		//Il faut que la taxe soit paramétrée ne BDD
		if(taxe == null) {
			log.error(TechnicalErrorMessageConstants.TAXE_NON_PARAMETREE);
			throw new FunctionalException(TechnicalErrorMessageConstants.TAXE_NON_PARAMETREE);
		}
		
		//initialiser la tva et la taxe de séjour
		ParamConfig.TVA = taxe.getTva();
		ParamConfig.TAXE_SEJOUR = taxe.getTaxeSejour();
	}

}
