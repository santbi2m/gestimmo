package com.ag2m.gestimmo.metier.serviceImpl;


import org.joda.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ag2m.gestimmo.metier.dao.AnomalieDao;
import com.ag2m.gestimmo.metier.dto.AnomalieDto;
import com.ag2m.gestimmo.metier.entite.Anomalie;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutAnomalie;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.AnomalieService;

import lombok.extern.log4j.Log4j;

@Service("anomalieService")
@Log4j
public class AnomalieServiceImpl implements AnomalieService {

	@Autowired
	AnomalieDao anomalieDao;

	@Autowired
	Mapper mapper;

	@Transactional(readOnly = true)
	public AnomalieDto findAnomalieById(Long id) throws FunctionalException {
		
		log.debug("Service findAnomalieById : id= " + id);

		Anomalie anomalie = null;

		//Vérification du paramètre d'entrée
		Optional.ofNullable(id).orElseThrow(() 
						 -> new FunctionalException("L'ID DE ENTITE SPECIFIER EST NULL"));
		//Appel du service
			anomalie = anomalieDao.findById(Anomalie.class, id);

			//Si l'objet est chargé, faire le mapping en AnomalieDto
			Optional.ofNullable(anomalie).filter( ano -> ano != null).map(entiteAno -> {
				return	mapper.anomalieToAnomalieDto(entiteAno);
			});
			return null;
	}

	@Transactional(readOnly = true)
	public List<AnomalieDto> findAllAnomalie() {

		    log.debug("Service findAllAnomalie");
		
			//chargement de tout mes Anomalies en base de données
			List<Anomalie> anomalies = anomalieDao.findAll(Anomalie.class);

			//Transformation de tous les Anomlies en AnomalieDTO
			return anomalies.stream().map(anomalie 
					-> mapper.anomalieToAnomalieDto(anomalie))
					.collect(Collectors.<AnomalieDto> toList());
	}

	@Transactional
	public AnomalieDto createAnomalie(AnomalieDto entiteDto) throws FunctionalException {

		log.debug("Service createAnomalie");
		
		Optional.ofNullable(entiteDto).orElseThrow(() 
						 -> new FunctionalException("L'ENTITE SPECIFIER EST NULL"));
		//map et sauvegarde l'anomalie.
		return mapAndSave(entiteDto);
	}
	
	
	@Transactional
	public AnomalieDto updateAnomalie(AnomalieDto entiteDto) throws FunctionalException {

		log.debug("Service updateAnomalie");

		 Optional.ofNullable(entiteDto).filter(dto 
				 -> dto.getId() != null ).orElseThrow(() 
						 -> new FunctionalException("L'ENTITE SPECIFIER EST NULL"));
		//map et sauvegarde l'anomalie.
		 return mapAndSave(entiteDto);
	}
	
	
/**
 * Map et sauvegarde l'enteite passer en paramétre. 
 * @param entiteDto
 * @return
 */
	private AnomalieDto mapAndSave(AnomalieDto entiteDto) {
		AnomalieDto result;
		//Transformation en entité Anomalie.
			Anomalie entite = mapper.anomalieDtoToAnomalie(entiteDto);
			//Appel du service.
			anomalieDao.saveOrUpdate(entite);
			result = mapper.anomalieToAnomalieDto(entite);
		return result;
	}

	@Transactional
	public boolean deleteAnomalie(AnomalieDto entiteDto) throws FunctionalException {

		log.debug("Service deleteAnomalie");
		//L'anomalie à supprimer ne peut pas être null.
		Optional.ofNullable(entiteDto).filter(dto 
				 -> dto.getId() != null ).orElseThrow(() 
						 -> new FunctionalException("L'ENTITE SPECIFIER EST NULL"));

		//Transformation en entité Anomalie.
		Anomalie entite = mapper.anomalieDtoToAnomalie(entiteDto);
		//Appel du service
		return anomalieDao.delete(entite);
	}

	@Transactional
	public List<AnomalieDto> findAnomalieByCriteria(String titre, 
			String statut, LocalDateTime dateOuverture, LocalDateTime dateTraitement,
			Long idAppartement) throws FunctionalException{

			log.debug("Service findAnomalieByCriteria");

			//Optional.ofNullable(statut).filter(statutAnomalie 
			//	 -> EnumStatutAnomalie.isStatutAnomalie(statutAnomalie)).orElseThrow(() 
			//			 -> new FunctionalException("LE STATUT N'EXISTE PAS"));
			
			if(statut!= null && !EnumStatutAnomalie.isStatutAnomalie(statut)) {
				throw new FunctionalException("LE STATUT N'EXISTE PAS");
			}
		 
			//Chargement des anomalies en fonction des critères d'entrée.
			List<Anomalie> anomalies = anomalieDao.findAnomalieByCriteria(titre, statut, dateOuverture, dateTraitement, idAppartement);
			//Transformation de tous les appartement en AnomalieDTO
			return  anomalies.stream()
					.map(anomalie
							-> mapper.anomalieToAnomalieDto(anomalie))
					.collect(Collectors.<AnomalieDto> toList());
	}
}
