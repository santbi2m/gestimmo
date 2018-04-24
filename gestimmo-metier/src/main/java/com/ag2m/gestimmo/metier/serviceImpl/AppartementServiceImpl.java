package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.dao.AppartementDao;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.entite.Appartement;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.AppartementService;

import lombok.extern.log4j.Log4j;

/**
 * @author mombaye
 *
 */
@Service("appartementService")
@Log4j
public class AppartementServiceImpl implements AppartementService{

	@Autowired
	AppartementDao appartementDao;
	
	@Autowired
	Mapper mapper;
	
	@Transactional(readOnly = true)
	public AppartementDto findById(Long id) {
		
		//TODO Ajouter les logs
		// ----------   Exemple d'utilisation du logger avec lombok   ---------- 
		//
		Appartement appartement = appartementDao.findById(Appartement.class, id);
		return mapper.appartementToAppartementDto(appartement);
	}

	@Transactional(readOnly = true)
	public List<AppartementDto> findAll() {
		List<Appartement> appartements = appartementDao.findAll(Appartement.class);
		List<AppartementDto> results =  appartements.stream()
							.map(appartement 
							-> mapper.appartementToAppartementDto(appartement))
							.collect(Collectors.<AppartementDto> toList());
		
		return results;
	}

	@Transactional
	public AppartementDto saveOrUpdate(AppartementDto entiteDto) {
		Appartement entite = mapper.appartementDtoToAppartement(entiteDto);
		appartementDao.saveOrUpdate(entite);
		return mapper.appartementToAppartementDto(entite);
	}

	@Transactional
	public boolean delete(AppartementDto entiteDto) {
		Appartement entite = mapper.appartementDtoToAppartement(entiteDto);
		return appartementDao.delete(entite);
	}	
}
