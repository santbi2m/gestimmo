package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.dao.AnomalieDao;
import com.ag2m.gestimmo.metier.dto.AnomalieDto;
import com.ag2m.gestimmo.metier.entite.Anomalie;
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
	public AnomalieDto findById(Long id) {
		
		//TODO Ajouter les logs
		// ----------   Exemple d'utilisation du logger avec lombok   ---------- 
		//		log.info(message);
		
		Anomalie anomalie =  anomalieDao.findById(Anomalie.class, id);
		AnomalieDto anomalieDto = mapper.anomalieToAnomalieDto(anomalie);
		return anomalieDto;
	}

	@Transactional(readOnly = true)
	public List<AnomalieDto> findAll() {
		List<Anomalie> results = anomalieDao.findAll(Anomalie.class);
		return results.stream().map(anomalie -> 
			mapper.anomalieToAnomalieDto(anomalie))
				.collect(Collectors.<AnomalieDto> toList());
	}

	@Transactional
	public AnomalieDto saveOrUpdate(AnomalieDto entiteDto) {
		Anomalie entite = mapper.anomalieDtoToAnomalie(entiteDto);
		anomalieDao.saveOrUpdate(entite);
		return mapper.anomalieToAnomalieDto(entite);
	}

	@Transactional
	public boolean delete(AnomalieDto entiteDto) {
		Anomalie entite = mapper.anomalieDtoToAnomalie(entiteDto);
		return anomalieDao.delete(entite);
	}

}
