package com.ag2m.gestimmo.metier.serviceImpl;

import java.util.Optional;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.config.ParamConfig;
import com.ag2m.gestimmo.metier.constants.FunctionnalErrorMessageConstants;
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
	@Transactional(readOnly = true)
	public void loadCurrentTaxe() throws FunctionalException {
		
		log.info("Chargement du paramétrage des Taxes " );
		
		Taxe taxe = parametrageDao.loadCurrentTaxe();
		
		//Il faut que la taxe soit paramétrée ne BDD
		Optional.ofNullable(taxe).orElseThrow(() 
				 -> new FunctionalException(TechnicalErrorMessageConstants.TAXE_NON_PARAMETREE));

		//initialiser la tva et la taxe de séjour
		ParamConfig.TVA = taxe.getTva();
		ParamConfig.TAXE_SEJOUR = taxe.getTaxeSejour();
	}


	/* 
	 * (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.ParametrageService#loadTaxeByDate(org.joda.time.LocalDateTime)
	 */
	@Override
	@Transactional(readOnly = true)
	public Taxe loadTaxeByDate(LocalDateTime date) throws FunctionalException {
		
		log.info("Chargement du paramétrage des Taxes à date" );
		
		Optional.ofNullable(date).orElseThrow(() 
				 -> new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_PARAMETRAGE_DATE_NULL));
		
		Taxe taxe = parametrageDao.loadTaxeByDate(date);
		
		//Il faut que la taxe soit paramétrée ne BDD
		Optional.ofNullable(taxe).orElseThrow(() 
				 -> new FunctionalException(TechnicalErrorMessageConstants.TAXE_NON_PARAMETREE));
		
		return taxe;
	}
}
