package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dao.AppartementDao;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.entite.Appartement;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
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
	public AppartementDto findAppartementById(Long id) throws TechnicalException {
		
		log.debug("Service findAppartementById : id= " + id);
		
		AppartementDto result = null;
		
		//Vérification du paramètre d'entrée
		Optional.ofNullable(id).orElseThrow(() 
				-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ID_NULL));
		
		//Appel du service
		Appartement appartement = null;
		appartement = appartementDao.findById(Appartement.class, id);
		
		//Si l'objet est chargé, faire le mapping en AppartementDto
		if(appartement != null) {
			result = mapper.appartementToAppartementDto(appartement);
		}
		
		return result;
	}

	
	@Transactional(readOnly = true)
	public List<AppartementDto> loadAllAppartement() {
		
		log.debug("Service loadAllAppartement ");
		
		List<AppartementDto> results = null;
			//Chargement de tous les appartements en BDD
			List<Appartement> appartements = appartementDao.findAll(Appartement.class);
			//Transformation de tous les appartement en AppartementDTO
			results = appartements.stream()
								.map(appartement 
								-> mapper.appartementToAppartementDto(appartement))
								.collect(Collectors.<AppartementDto> toList());
		return results;
	}

	
	@Transactional
	public AppartementDto createAppartement(AppartementDto entiteDto) throws TechnicalException {

		log.debug("Service createAppartement");
		
		//L'appartement à créer ne peut pas être null
		Optional.ofNullable(entiteDto).orElseThrow(() 
				-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_CREATION_NULL));
		
		//map and save
		return mapAndSave(entiteDto);
	}
	
	
	@Transactional
	public AppartementDto updateAppartement(AppartementDto entiteDto) throws TechnicalException {

		log.debug("Service updateAppartement");
		
		//L'appartement à modifier doit exister en BDD
		Optional.ofNullable(entiteDto)
			.filter(dto -> dto.getId() != null)
			.orElseThrow(() -> 
				new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_MODIFICATION_NULL));
		
		//map and save
		return mapAndSave(entiteDto);
	}


	/**
	 * Map le DTO en entité, puis le sauvegarde en BDD
	 * 
	 * @param entiteDto
	 * @return
	 */
	private AppartementDto mapAndSave(AppartementDto entiteDto) {
		//Transformation en entité Appartement
		Appartement entite = mapper.appartementDtoToAppartement(entiteDto);
		//Appel du service
		appartementDao.saveOrUpdate(entite);
		return mapper.appartementToAppartementDto(entite);
	}

	
	@Transactional
	public boolean deleteAppartement(AppartementDto entiteDto) throws TechnicalException {
		
		log.debug("Service deleteAppartement");
		
		//L'appartement à supprimer ne peut pas être null
		Optional.ofNullable(entiteDto).orElseThrow(() 
				-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_SUPP_NULL));
		
		//Transformation en entité Appartement
		Appartement entite = mapper.appartementDtoToAppartement(entiteDto);
		
		//Appel du service
			 return appartementDao.delete(entite);
	}

	@Transactional(readOnly = true)
	public List<AppartementDto> findAppartementByCriteria(String libelle, 
			String type, Long idBien){
		
		log.debug("Service findAppartementByCriteria");
		
		//Chargement des appartement en fonction des critères d'entrée.
		List<Appartement> appartements = appartementDao.findAppartementByCriteria(libelle, type, idBien);
		//Transformation de tous les appartement en AppartementDTO
		return appartements.stream()
							.map(appartement 
							-> mapper.appartementToAppartementDto(appartement))
							.collect(Collectors.<AppartementDto> toList());
	}
	
	@Transactional(readOnly = true)
	public List<AppartementDto> findAppartByReservation( Long idReservation){
		
		log.debug("Service findAppartByReservation");
		
		//Chargement des appartement en fonction des critères d'entrée.
		List<Appartement> appartements = appartementDao.findAppartByReservation(idReservation);
		//Transformation de tous les appartement en AppartementDTO
		return appartements.stream()
							.map(appartement 
							-> mapper.appartementToAppartementDto(appartement))
							.collect(Collectors.<AppartementDto> toList());
	}
	
}
