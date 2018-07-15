package com.ag2m.gestimmo.metier.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dao.BienDao;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.entite.Bien;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.BienCriteria;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.BienService;

/**
 * @author mombaye
 *
 */
@Service("bienService")
public class BienServiceImpl implements BienService {

	private Logger logger = Logger.getLogger(BienServiceImpl.class);

	@Autowired
	BienDao bienDao;

	@Autowired
	Mapper mapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ag2m.gestimmo.metier.service.BienService#findById(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	public BienDto findBienById(Long id) throws TechnicalException {


		logger.info("Methode de recherche de bien par id " + id);
		Optional.ofNullable(id).orElseThrow(() 
				 -> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ID_NULL));

		final Bien bien = bienDao.findById(Bien.class, id);

		if (bien != null) {
			return mapper.bienToBienDto(bien);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ag2m.gestimmo.metier.service.BienService#findAll()
	 */
	@Transactional(readOnly = true)
	public List<BienDto> loadAllBien() {

		logger.debug("Chargement de tous les biens");

		List<Bien> biens = bienDao.findAll(Bien.class);
		return biens.stream().map(bien 
				-> mapper.bienToBienDto(bien))
				.collect(Collectors.<BienDto>toList());
	}

	/**
	 * Map le BienDto en entité Bien 
	 * puis sauvegarde celle-ci en BDD
	 * 
	 * @param bienDto
	 * @return
	 */
	private BienDto mapAndSave(BienDto bienDto) {
			// Transformation en entité Bien
			Bien bien = mapper.bienDtoToBien(bienDto);
			// Appel du service
			bienDao.saveOrUpdate(bien);
			return mapper.bienToBienDto(bien);
	}

	
	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.BienService#createBien(com.ag2m.gestimmo.metier.dto.BienDto)
	 */
	@Override
	@Transactional
	public BienDto createBien(BienDto bienDto) throws TechnicalException {

		logger.debug("Creation bien");

		// Le bien à créer ne peut pas être null
		Optional.ofNullable(bienDto).
		orElseThrow(() 
				 -> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ID_NULL));
		// map and save
		return mapAndSave(bienDto);
	}

	
	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.BienService#updateBien(com.ag2m.gestimmo.metier.dto.BienDto)
	 */
	@Override
	@Transactional
	public BienDto updateBien(BienDto bienDto) throws TechnicalException {

		logger.debug("Mise à jour Bien");
		// Le bien à modifier doit exister en BDD
		Optional.ofNullable(bienDto)
		.filter(dto -> dto.getId() != null)
		.orElseThrow(() 
		 -> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_MODIFICATION_NULL));

		// map and save
		return mapAndSave(bienDto);
	}

	@Override
	@Transactional
	public boolean deleteBien(BienDto bienDto) throws TechnicalException {
		
		logger.debug("SUppression Bien");
		// Bien à supprimer ne peut pas être null
		Optional.ofNullable(bienDto).orElseThrow(() 
				 -> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ID_NULL));
		// Transformation en entité Bien
		Bien bien = mapper.bienDtoToBien(bienDto);

		// Appel du service
			return bienDao.delete(bien);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.BienService#findBienByCriteria(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public List<BienDto> findBienByCriteria(BienCriteria bienCriteria) throws FunctionalException {
		
		logger.debug("Recherche Par critere");
		//Chargement des biens en fonction des critères d'entrée.
		List<Bien> biens = bienDao.findBienByCriteria(bienCriteria);
		//Transformation de tous les biens en BienDto
		return biens.stream()
							.map(bien 
							-> mapper.bienToBienDto(bien))
							.collect(Collectors.<BienDto> toList());
	}
}
