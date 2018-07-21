package com.ag2m.gestimmo.metier.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.dao.DevisDao;
import com.ag2m.gestimmo.metier.dto.DevisDto;
import com.ag2m.gestimmo.metier.entite.Devis;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.DevisService;

import lombok.extern.log4j.Log4j;

@Service("devisService")
@Log4j
public class DevisServiceImpl implements DevisService {

	@Autowired
	DevisDao devisDao;
	
	@Autowired
	Mapper mapper;
	
//	@Transactional(readOnly = true)
//	public DevisDto findById(Long id) {
//		
//		
//		Facture facture =  factureDao.findById(Facture.class, id);
//		FactureDto factureDto = mapper.factureToFactureDto(facture);
//		return factureDto;
//	}

//	@Transactional(readOnly = true)
//	public List<DevisDto> findAll() {
//		List<Facture> results = factureDao.findAll(Facture.class);
//		return results.stream().map(facture -> 
//			mapper.factureToFactureDto(facture))
//				.collect(Collectors.<FactureDto> toList());
//	}

	@Transactional
	public DevisDto saveOrUpdate(DevisDto entiteDto) {
		Devis entite = mapper.devisDtoToDevis(entiteDto);
		devisDao.saveOrUpdate(entite);
		return mapper.devisToDevisDto(entite);
	}

//	@Transactional
//	public boolean delete(DevisDto entiteDto) {
//		Facture entite = mapper.factureDtoToFacture(entiteDto);
//		return factureDao.delete(entite);
//	}

}
