package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dao.DevisDao;
import com.ag2m.gestimmo.metier.dto.DevisDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.entite.Devis;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.CommonFactureCriteria;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.DevisService;
import com.ag2m.gestimmo.metier.utils.NumeroFactureUtil;

import lombok.extern.log4j.Log4j;

@Service("devisService")
@Log4j
public class DevisServiceImpl implements DevisService {

	@Autowired
	DevisDao devisDao;
	
	@Autowired
	Mapper mapper;
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	
	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.DevisService#findDevisById(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public DevisDto findDevisById(Long id) throws TechnicalException {
		
		log.info("Service findDevisById : id= " + id);
		
		DevisDto devisDto = null;
		//Vérification du paramètre d'entrée
		Optional.ofNullable(id).orElseThrow(() 
				-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ID_NULL));
				
		//Appel du service
		Devis devis =  devisDao.findById(Devis.class, id);
		if(devis != null) {
			devisDto = mapper.devisToDevisDto(devis);
		}
		return devisDto;
	}
	
	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.DevisService#findDevisByCriteria(com.ag2m.gestimmo.metier.ioparam.AbstractFactureCriteria)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<DevisDto> findDevisByCriteria(CommonFactureCriteria devisCriteria){
		
		log.debug("Service findAppartementByCriteria");
		
		//Chargement des Devis en fonction des critères d'entrée.
		List<Devis> devis = devisDao.findDevisByCriteria(devisCriteria);
		//Transformation de tous les Devis en DevisDTO
		return devis.stream()
							.map(currentDdevis 
							-> mapper.devisToDevisDto(currentDdevis))
							.collect(Collectors.<DevisDto> toList());
	}
	
	
	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.DevisService#findAllDevis()
	 */
	@Transactional(readOnly = true)
	@Override
	public List<DevisDto> findAllDevis() {
		
		log.info("Service findAllDevis ");
		
		//Chargement de tous les devis en BDD
		List<Devis> results = devisDao.findAll(Devis.class);
		
		//Transformation de tous les devis en DevisDTO
		return results.stream().map(devis -> 
			mapper.devisToDevisDto(devis))
				.collect(Collectors.<DevisDto> toList());
	}
	

	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.DevisService#createDevis(com.ag2m.gestimmo.metier.dto.DevisDto)
	 */
	@Transactional
	public DevisDto createDevis(DevisDto entiteDto) throws TechnicalException {
		
		log.debug("Service createDevis");

		//Le devis à créer ne peut pas être null
		Optional.ofNullable(entiteDto).orElseThrow(() 
						-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_CREATION_NULL));
		
		//La facture de simulation du devis doit être présente dans le devis
		Optional.ofNullable(entiteDto.getFacture()).orElseThrow(() 
				-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_DEVIS_FACTURE_NULL));
		
		//Les reservations simulées pour le devis doivent être présentes.
		List<ReservationDto> reservations = entiteDto.getFacture().getReservations();
		Optional.ofNullable(reservations).filter(resa -> !resa.isEmpty()).orElseThrow(() 
				-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_DEVIS_RESERVATION_NULL));
		
		//Récupération du dernier numéro de devis en base.
		String numeroDevis = devisDao.findLastNumDevis();
		
		//Génération du numéro de devis suivant, à associer avec le devis en cours de création.
		String nextNumDevis = NumeroFactureUtil
					.generateNexFactureNumberByActual(numeroDevis, NumeroFactureUtil.SUFFIXE_DV);
		
		/*** Initialiser la période de réservation 
		 * 	 celle-ci correspond à la plus petite 
		 * 	 date check-in et à la plus grande date de check-out.
		 **/
		List<LocalDateTime> ascSortedCheckinDate = getAscSortedCheckinDates(reservations);
		List<LocalDateTime> descSortedCheckoutDate = getDescSortedCheckoutDates(reservations);
		
		entiteDto.setDateCheckin(ascSortedCheckinDate.get(0));
		entiteDto.setDateCheckout(descSortedCheckoutDate.get(0));
		
		//Transformation du devis Dto en entité Devis.
		Devis entite = mapper.devisDtoToDevis(entiteDto);
		entite.setNumeroDevis(nextNumDevis);
		
		//La date de création est mise à la date courante.
		entite.setDateCreation(LocalDateTime.now());
		
		//Sauvegarde du devis en BDD.
		devisDao.saveOrUpdate(entite);
		
		//Transformation de l'entité sauvegardée en dto avant de la retourner.
		return mapper.devisToDevisDto(entite);
	}

	
	
	
	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.DevisService#updateDevis(com.ag2m.gestimmo.metier.dto.DevisDto)
	 */
	@Transactional
	public DevisDto updateDevis(DevisDto entiteDto) throws TechnicalException {

		log.debug("Service updateDevis");
		
		//Le devis à modifier doit exister en BDD
		Optional.ofNullable(entiteDto)
				.filter(dto -> dto.getId() != null)
				.orElseThrow(() -> 
					new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_MODIFICATION_NULL));
		
		//Transformation du devis Dto en entité Devis.
		Devis entite = mapper.devisDtoToDevis(entiteDto);
		
		//Sauvegarde du devis en BDD.
		devisDao.saveOrUpdate(entite);
				
		//Transformation de l'entité sauvegardée en dto avant de la retourner.
		return mapper.devisToDevisDto(entite);
	}

	
	/* (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.service.DevisService#deleteDevis(com.ag2m.gestimmo.metier.dto.DevisDto)
	 */
	@Transactional
	@Override
	public boolean deleteDevis(DevisDto entiteDto) throws TechnicalException {
		
		log.debug("Service deleteDevis");
	
		//Le devis à supprimer ne peut pas être null
		Optional.ofNullable(entiteDto).orElseThrow(() 
				-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_SUPP_NULL));
		//Transformation en entité DevisDto
		Devis entite = mapper.devisDtoToDevis(entiteDto);
		return devisDao.delete(entite);
	}
	
	
	
	/**
	 * Récupère la liste des dates checkin trié par ordre croissant
	 * 
	 * @param reservations
	 * @return
	 */
	private List<LocalDateTime> getAscSortedCheckinDates(List<ReservationDto> reservations) {
		return reservations.stream().map(resa -> resa.getDateCheckin()).sorted(
										new Comparator<LocalDateTime>() {
				
												@Override
												public int compare(LocalDateTime date1, LocalDateTime date2) {
													return date1.compareTo(date2);
												}
								}).collect(Collectors.toList());
	}
	
	
	/**
	 * Récupère la liste des dates checkout trié par ordre décroissant
	 * 
	 * @param reservations
	 * @return
	 */
	private List<LocalDateTime> getDescSortedCheckoutDates(List<ReservationDto> reservations) {
		return reservations.stream().map(resa -> resa.getDateCheckout()).sorted(
										new Comparator<LocalDateTime>() {
				
												@Override
												public int compare(LocalDateTime date1, LocalDateTime date2) {
													return date2.compareTo(date1);
												}
								}).collect(Collectors.toList());
	}

}
