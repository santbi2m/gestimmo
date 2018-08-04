package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.dao.FactureDao;
import com.ag2m.gestimmo.metier.dto.FactureDto;
import com.ag2m.gestimmo.metier.entite.Facture;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.FactureService;
import com.ag2m.gestimmo.metier.utils.NumeroFactureUtil;

import lombok.extern.log4j.Log4j;

@Service("factureService")
@Log4j
public class FactureServiceImpl implements FactureService {

	@Autowired
	FactureDao factureDao;
	
	@Autowired
	Mapper mapper;
	
	@Transactional(readOnly = true)
	public FactureDto findById(Long id) {
		
		//TODO Ajouter les logs
		// ----------   Exemple d'utilisation du logger avec lombok   ---------- 
		//		log.info(message);
		
		Facture facture =  factureDao.findById(Facture.class, id);
		FactureDto factureDto = mapper.factureToFactureDto(facture);
		return factureDto;
	}

	@Transactional(readOnly = true)
	public List<FactureDto> findAll() {
		List<Facture> results = factureDao.findAll(Facture.class);
		return results.stream().map(facture -> 
			mapper.factureToFactureDto(facture))
				.collect(Collectors.<FactureDto> toList());
	}

	@Transactional
	public FactureDto saveOrUpdate(FactureDto entiteDto) throws TechnicalException {
		Facture entite = mapper.factureDtoToFacture(entiteDto);
		String numeroFacture = factureDao.findLastNumFacture();
		String nextNumFacture = NumeroFactureUtil.generateNexFactureNumberByActual(numeroFacture, NumeroFactureUtil.SUFFIXE_FT);
		entite.setNumeroFacture(nextNumFacture);
		factureDao.saveOrUpdate(entite);
		return mapper.factureToFactureDto(entite);
	}

	@Transactional
	public boolean delete(FactureDto entiteDto) {
		Facture entite = mapper.factureDtoToFacture(entiteDto);
		return factureDao.delete(entite);
	}

}
