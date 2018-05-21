package com.ag2m.gestimmo.metier.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.constants.MessageErrorConstants;
import com.ag2m.gestimmo.metier.dao.BienDao;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.entite.Bien;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
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
	public BienDto findBienById(Long id) throws FunctionalException {

		Bien bien = null;
		BienDto bienDto = null;

		logger.info("Methode de recherche de bien par id " + id);
		if (id == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ID_NULL);
		}
		try {

			bien = bienDao.findById(Bien.class, id);

			if (bien != null) {
				bienDto = mapper.bienToBienDto(bien);
			}

		} catch (Exception e) {
			StringBuilder message = new StringBuilder(MessageErrorConstants.ERREUR_AU_CHARGEMENT);
			message.append(id);
			logger.error(message.toString(), e);
		}
		return bienDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ag2m.gestimmo.metier.service.BienService#findAll()
	 */
	@Transactional(readOnly = true)
	public List<BienDto> loadAllBien() {

		logger.debug("Chargement de tous les biens");

		List<BienDto> listeBienDto = null;
		try {

			List<Bien> biens = bienDao.findAll(Bien.class);
			listeBienDto = biens.stream().map(bien -> mapper.bienToBienDto(bien)).collect(Collectors.<BienDto>toList());
		} catch (Exception e) {
			logger.error(MessageErrorConstants.ERREUR_BDD, e);
		}
		return listeBienDto;
	}

	private BienDto mapAndSave(BienDto bienDto, BienDto bDto) {
		try {
			// Transformation en entité Bien
			Bien bien = mapper.bienDtoToBien(bienDto);
			// Appel du service
			bienDao.saveOrUpdate(bien);
			bDto = mapper.bienToBienDto(bien);

		} catch (Exception e) {
			logger.error(MessageErrorConstants.ERREUR_A_LA_SAUVEGARDE, e);
		}

		return bDto;
	}

	@Override
	@Transactional
	public BienDto createBien(BienDto bienDto) throws FunctionalException {

		logger.debug("Creation bien");

		BienDto bDto = null;
		// Le bien à créer ne peut pas être null
		if (bienDto == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ENTREE_CREATION_NULL);
		}

		// map and save
		return mapAndSave(bienDto, bDto);
	}

	@Override
	@Transactional
	public BienDto updateBien(BienDto bienDt) throws FunctionalException {

		logger.debug("Mise à jour Bien");

		BienDto result = null;
		// Le bien à modifier doit exister en BDD
		if (bienDt == null || bienDt.getId() == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ENTREE_MODIFICATION_NULL);
		}

		// map and save
		return mapAndSave(bienDt, result);
	}

	@Override
	@Transactional
	public boolean deleteBien(BienDto bienDto) throws FunctionalException {
		
		logger.debug("SUppression Bien");

		boolean result = false;

		// Bien à supprimer ne peut pas être null
		if (bienDto == null) {
			throw new FunctionalException(MessageErrorConstants.ERREUR_ENTREE_SUPP_NULL);
		}

		// Transformation en entité Bien
		Bien bien = mapper.bienDtoToBien(bienDto);

		// Appel du service
		try {
			result = bienDao.delete(bien);
		} catch (Exception e) {
			logger.error(MessageErrorConstants.ERREUR_A_LA_SUPPRESSION, e);
		}

		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.BienService#findBienByCriteria(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public List<BienDto> findBienByCriteria(String libelle, String adresse, String complement,
			Integer codePostal, String ville, String pays) throws FunctionalException {
		
		logger.debug("Recherche Par critere");
		
		List<BienDto> listeBientDto = new ArrayList<>();
		
		try {
		//Chargement des biens en fonction des critères d'entrée.
		List<Bien> biens = bienDao.findBienByCriteria(libelle, adresse, complement, codePostal, ville, pays);
		//Transformation de tous les biens en BienDto
		listeBientDto = biens.stream()
							.map(bien 
							-> mapper.bienToBienDto(bien))
							.collect(Collectors.<BienDto> toList());
		} catch (Exception e) {
			logger.error(MessageErrorConstants.ERREUR_BDD  , e);
		}
		
		return listeBientDto;
	
	}
}
