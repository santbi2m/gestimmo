package com.ag2m.gestimmo.metier.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.dao.BienDao;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.entite.Bien;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.BienService;

import lombok.extern.log4j.Log4j;

/**
 * @author mombaye
 *
 */
@Service("bienService")
@Log4j
public class BienServiceImpl implements BienService {

	@Autowired
	BienDao bienDao;
	
	@Autowired
	Mapper mapper;
	
	@Transactional(readOnly = true)
	public BienDto findById(Long id) {
		
		//TODO Ajouter les logs
		// ----------   Exemple d'utilisation du logger avec lombok   ---------- 
		//
		
		Bien bien = bienDao.findById(Bien.class, id);
		BienDto bienDto = mapper.bienToBienDto(bien);
		return bienDto;
	}

	@Transactional(readOnly = true)
	public List<BienDto> findAll() {
		List<Bien> biens = bienDao.findAll(Bien.class);
		return biens.stream().map(bien -> mapper.bienToBienDto(bien)).collect(Collectors.<BienDto> toList());
	}

	@Transactional
	public BienDto saveOrUpdate(BienDto bienDto) {
		Bien bien = mapper.bienDtoToBien(bienDto);
		 bienDao.saveOrUpdate(bien);
		 return mapper.bienToBienDto(bien);
	}

	@Transactional
	public boolean delete(BienDto bienDto) {
		Bien bien = mapper.bienDtoToBien(bienDto);
		return bienDao.delete(bien);
	}		
}
