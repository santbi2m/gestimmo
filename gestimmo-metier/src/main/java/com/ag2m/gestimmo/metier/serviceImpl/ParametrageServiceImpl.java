package com.ag2m.gestimmo.metier.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.config.ParamConfig;
import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dao.ParametrageDao;
import com.ag2m.gestimmo.metier.entite.referentiel.Parametrage;
import com.ag2m.gestimmo.metier.entite.referentiel.Remise;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeParametrage;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.service.ParametrageService;

import lombok.extern.log4j.Log4j;

@Log4j
@Service("ParametrageService")
public class ParametrageServiceImpl implements ParametrageService {

	@Autowired
	ParametrageDao parametrageDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public void loadAllRemise() {
		
		log.info("Chargement du paramétrage des remises " );
		
		List<Remise> remises = parametrageDao.loadAllRemise();
		
		//Il faut que la taxe soit paramétrée en BDD
		Optional.ofNullable(remises).filter(r -> !r.isEmpty()).orElseThrow(() 
				 -> new FunctionalException(TechnicalErrorMessageConstants.REMISE_NON_PARAMETREE));

		//initialiser la liste des remises.
		ParamConfig.REMISES = remises;
	}


	@Override
	@Transactional(readOnly = true)
	public void loadPourcentagePenanlite() throws TechnicalException {

		log.info("Chargement du paramétarge de type PENALITE");
		
		//Appel
		Parametrage parametrage = parametrageDao.loadParametrageByType(EnumTypeParametrage.POURCENTAGE_PENALITE.getType());
		
		//Exception si aucun paramétrage trouvé
		Optional.ofNullable(parametrage).orElseThrow(() 
				 -> new FunctionalException(TechnicalErrorMessageConstants.PARAMETRAGE_NON_TROUVE));
		
		//initialiser la valeur du pourcentage de pénalité.
		try{
			
			ParamConfig.POURCENTAGE_PENALITE = Integer.valueOf(parametrage.getValeur());
		
		}catch (NumberFormatException e) {
			throw new TechnicalException(TechnicalErrorMessageConstants.PARAMETRAGE_NON_VALIDE, e);
		}
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public void loadSeuilAnnulationGratuite() throws TechnicalException {

		log.info("Chargement du paramétarge de type SEUIL_NON_PENALISABLE");
		
		//Appel
		Parametrage parametrage = parametrageDao.loadParametrageByType(EnumTypeParametrage.SEUIL_NON_PENALISABLE.getType());
		
		//Exception si aucun paramétrage trouvé
		Optional.ofNullable(parametrage).orElseThrow(() 
				 -> new FunctionalException(TechnicalErrorMessageConstants.PARAMETRAGE_NON_TROUVE));
		
		//initialiser la valeur du pourcentage de pénalité.
		try{
			
			ParamConfig.SEUIL_ANNULATION_GRATUITE = Integer.valueOf(parametrage.getValeur());
		
		}catch (NumberFormatException e) {
			throw new TechnicalException(TechnicalErrorMessageConstants.PARAMETRAGE_NON_VALIDE, e);
		}
	}
	
}
