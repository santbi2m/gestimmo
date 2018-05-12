package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.dao.AdresseDao;
import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.entite.Adresse;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.AdresseService;

import lombok.extern.log4j.Log4j;

@Service("adresseService")
@Log4j
public class AdresseServiceImpl implements AdresseService {

	@Autowired
	AdresseDao adresseDao;
	
	@Autowired
	Mapper mapper;
	
	@Transactional(readOnly = true)
	public AdresseDto findById(Long id) {
		
		//TODO Ajouter les logs
		// ----------   Exemple d'utilisation du logger avec lombok   ---------- 
		//		log.info(message);
		
		Adresse adresse =  adresseDao.findById(Adresse.class, id);
		AdresseDto adresseDto = mapper.adresseToAdresseDto(adresse);
		return adresseDto;
	}

	@Transactional(readOnly = true)
	public List<AdresseDto> findAll() {
		List<Adresse> results = adresseDao.findAll(Adresse.class);
		return results.stream().map(adresse -> 
			mapper.adresseToAdresseDto(adresse))
				.collect(Collectors.<AdresseDto> toList());
	}

	@Transactional
	public AdresseDto saveOrUpdate(AdresseDto entiteDto) {
		Adresse entite = mapper.adresseDtoToAdresse(entiteDto);
		adresseDao.saveOrUpdate(entite);
		return mapper.adresseToAdresseDto(entite);
	}

	@Transactional
	public boolean delete(AdresseDto entiteDto) {
		Adresse entite = mapper.adresseDtoToAdresse(entiteDto);
		return adresseDao.delete(entite);
	}

}
