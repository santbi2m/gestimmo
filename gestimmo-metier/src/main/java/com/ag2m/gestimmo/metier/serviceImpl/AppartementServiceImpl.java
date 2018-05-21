package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.constants.MessageErrorConstants;
import com.ag2m.gestimmo.metier.dao.AppartementDao;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.entite.Appartement;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.AppartementService;

import lombok.extern.log4j.Log4j;

/**
 * Représente le business service
 * Contenant tous les services
 * métier de gestion d'un appartement
 * 
 * @author mombaye
 *
 */
@Log4j
@Service("appartementService")
public class AppartementServiceImpl implements AppartementService{

	@Autowired
	AppartementDao appartementDao;
	
	@Autowired
	Mapper mapper;
	
	
	@Transactional(readOnly = true)
	public AppartementDto findAppartementById(Long id) throws FunctionalException {
		
		log.debug("Service findAppartementById : id= " + id);
		
		Appartement appartement = null;
		AppartementDto result = null;
		
		//Vérification du paramètre d'entrée
		if(id == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ID_NULL);
		}
		
		//Appel du service
		try {
			appartement = appartementDao.findById(Appartement.class, id);
			
			//Si l'objet est chargé, faire le mapping en AppartementDto
			if(appartement != null) {
				result = mapper.appartementToAppartementDto(appartement);
			}
		} catch (Exception e) {
			StringBuilder message = new StringBuilder(MessageErrorConstants.ERREUR_AU_CHARGEMENT);
			message.append(id);
			log.error(message.toString() , e);
		}
		
		return result;
	}

	
	@Transactional(readOnly = true)
	public List<AppartementDto> loadAllAppartement() {
		
		log.debug("Service loadAllAppartement ");
		
		List<AppartementDto> results = null;
		try {
			//Chargement de tous les appartements en BDD
			List<Appartement> appartements = appartementDao.findAll(Appartement.class);
			//Transformation de tous les appartement en AppartementDTO
			results = appartements.stream()
								.map(appartement 
								-> mapper.appartementToAppartementDto(appartement))
								.collect(Collectors.<AppartementDto> toList());
		} catch (Exception e) {
			log.error(MessageErrorConstants.ERREUR_BDD  , e);
		}
		
		return results;
	}

	
	@Transactional
	public AppartementDto createAppartement(AppartementDto entiteDto) throws FunctionalException {

		log.debug("Service createAppartement");
		
		AppartementDto result = null;
		//L'appartement à créer ne peut pas être null
		if(entiteDto == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ENTREE_CREATION_NULL);
		}
		
		//map and save
		return mapAndSave(entiteDto, result);
	}
	
	
	@Transactional
	public AppartementDto updateAppartement(AppartementDto entiteDto) throws FunctionalException {

		log.debug("Service updateAppartement");
		
		AppartementDto result = null;
		//L'appartement à modifier doit exister en BDD
		if(entiteDto == null || entiteDto.getId() == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ENTREE_MODIFICATION_NULL);
		}
		
		//map and save
		return mapAndSave(entiteDto, result);
	}


	/**
	 * Map le DTO en entité, puis le sauvegarde en BDD
	 * 
	 * @param entiteDto
	 * @param result
	 * @return
	 */
	private AppartementDto mapAndSave(AppartementDto entiteDto, AppartementDto result) {
		try {
			//Transformation en entité Appartement
			Appartement entite = mapper.appartementDtoToAppartement(entiteDto);
			//Appel du service
			appartementDao.saveOrUpdate(entite);
			result = mapper.appartementToAppartementDto(entite);
			
		}catch (Exception e) {
			log.error(MessageErrorConstants.ERREUR_A_LA_SAUVEGARDE  , e);
		}
		
		return result;
	}

	
	@Transactional
	public boolean deleteAppartement(AppartementDto entiteDto) throws FunctionalException {
		
		log.debug("Service deleteAppartement");
		
		boolean result = false;
		
		//L'appartement à supprimer ne peut pas être null
		if(entiteDto == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ENTREE_SUPP_NULL);
		}
		
		//Transformation en entité Appartement
		Appartement entite = mapper.appartementDtoToAppartement(entiteDto);
		
		//Appel du service
		try {
			 result = appartementDao.delete(entite);
		} catch (Exception e) {
			log.error(MessageErrorConstants.ERREUR_A_LA_SUPPRESSION  , e);
		}
		
		return result;
	}

	@Transactional
	public List<AppartementDto> findAppartementByCriteria(String libelle, 
			String type, Long idBien) throws FunctionalException{
		
		log.debug("Service findAppartementByCriteria");
		
		List<AppartementDto> results = new ArrayList<>();
		
		try {
		//Chargement des appartement en fonction des critères d'entrée.
		List<Appartement> appartements = appartementDao.findAppartementByCriteria(libelle, type, idBien);
		//Transformation de tous les appartement en AppartementDTO
		results = appartements.stream()
							.map(appartement 
							-> mapper.appartementToAppartementDto(appartement))
							.collect(Collectors.<AppartementDto> toList());
		} catch (Exception e) {
			log.error(MessageErrorConstants.ERREUR_BDD  , e);
		}
		
		return results;
	}
	
}
