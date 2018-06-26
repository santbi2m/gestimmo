package com.ag2m.gestimmo.metier.service;

import org.joda.time.LocalDateTime;
import java.util.List;

import com.ag2m.gestimmo.metier.dto.AnomalieDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

public interface AnomalieService {

	/**
	 * Retrouve l'entité dont l'id est passé en paramètre
	 * 
	 * @param id
	 * @return
	 */
	AnomalieDto findAnomalieById(Long id) throws FunctionalException;
	
	/**
	 * Retourne une liste d'entités
	 * 
	 * @return
	 */
	List<AnomalieDto> findAllAnomalie();
	
	/**
	 * Sauvegarde ou met à jour l'entité en paramètre
	 * 
	 * @param entite
	 * @return
	 * @throws FunctionalException 
	 */
	AnomalieDto createAnomalie(AnomalieDto entite) throws FunctionalException;
	
	/**
	 * Permet de supprimer l'entité en entrée
	 * 
	 * @param entite
	 * @return
	 * @throws FunctionalException 
	 */
	boolean deleteAnomalie(AnomalieDto entite) throws FunctionalException;
	
	/**
	 * 
	 * @param titre
	 * @param statut
	 * @param dateOuverture
	 * @param dateTraitement
	 * @param idAppartement
	 * @return
	 * @throws FunctionalException
	 */
	List<AnomalieDto> findAnomalieByCriteria(String titre, 
			String statut, LocalDateTime dateOuverture, LocalDateTime dateTraitement,
			Long idAppartement) throws FunctionalException;
	/**
	 * 
	 * @param entiteDto
	 * @return
	 * @throws FunctionalException
	 */
	AnomalieDto updateAnomalie(AnomalieDto entiteDto) throws FunctionalException;
}
