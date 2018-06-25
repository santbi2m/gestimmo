/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.constants.FunctionnalErrorMessageConstants;
import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dao.AppartementDao;
import com.ag2m.gestimmo.metier.dao.ReservationDao;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.entite.Appartement;
import com.ag2m.gestimmo.metier.entite.Reservation;
import com.ag2m.gestimmo.metier.enumeration.EnumOptionFormatDate;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutReservation;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.ReservationCriteria;
import com.ag2m.gestimmo.metier.ioparam.UniteReservation;
import com.ag2m.gestimmo.metier.ioparam.IPeriode;
import com.ag2m.gestimmo.metier.ioparam.Nuitee;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.ReservationService;
import com.ag2m.gestimmo.metier.utils.CustomDateUtil;

import lombok.extern.log4j.Log4j;

/**
 * @author mombaye
 *
 */
@Service("reservationService")
@Log4j
@CacheConfig(cacheNames={"gestimmo"})
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationDao reservationDao;
	
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private AppartementDao appartementDao;

	
	@Transactional(readOnly = true)
	public ReservationDto findReservationById(Long id) throws TechnicalException {
	
		log.debug("Service findReservationById : id= " + id);
		
		ReservationDto result = null;
		
		//Vérification du paramètre d'entrée
		Optional.ofNullable(id).orElseThrow(() 
				 -> new FunctionalException(TechnicalErrorMessageConstants.ERREUR_ID_NULL));
		//Appel du service
			Reservation reservation =  reservationDao.findById(Reservation.class, id);
			
		//Si l'objet est chargé, faire le mapping en ReservationDto
		if(reservation != null) {
			result = mapper.reservationToReservationDto(reservation);
		}
		
		return result;
	}

	@Transactional(readOnly = true)
	public List<ReservationDto> loadAllReservation() {
		
		log.debug("Service loadAllReservation ");

		//Chargement de toutes les reservations en BDD
		List<Reservation> results = reservationDao.findAll(Reservation.class);
		//Transformation de toutes les reservations en ReservationDTO
		return results.stream().map(reservation -> 
		mapper.reservationToReservationDto(reservation))
				.collect(Collectors.<ReservationDto> toList());
	}

	@Transactional
	public ReservationDto createReservation(ReservationDto reservationDto) throws FunctionalException, TechnicalException{
		
		log.debug("Service createReservation");
		
		//Validation des paramètre d'entrée, mapper le dto en entité 
		// et sauvegarde la réservation
		return controlMapAndSave(reservationDto);
	}


	@Transactional
	public boolean deleteReservation(ReservationDto reservationDto) throws TechnicalException {
		
		log.debug("Service deleteReservation");
		
		
		//La réservation à supprimer ne peut pas être null
		Optional.ofNullable(reservationDto).orElseThrow(() 
				 -> new FunctionalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_SUPP_NULL));
		
		//Transformation en entité Reservation
		Reservation entite = mapper.reservationDtoToReservation(reservationDto);
		//Supprimer la réservation
		return reservationDao.delete(entite);
	
	}	
	
	
	@Transactional
	public ReservationDto updateReservation(ReservationDto reservationDto) throws TechnicalException {

		log.debug("Service updateReservation");
		
		//La réservation à modifier doit exister en BDD
		Optional.ofNullable(reservationDto)
				.filter(dto -> dto.getId() != null)
				.orElseThrow(() 
				 -> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_MODIFICATION_NULL));
		
		return controlMapAndSave(reservationDto);
	}

	
	@Transactional(readOnly = true)
	public List<ReservationDto> findReservationByCriteria(ReservationCriteria reservationCriteria) 
			throws TechnicalException,FunctionalException{
		
		log.debug("Service findReservationByCriteria");
		
		Optional.ofNullable(reservationCriteria)
				.filter(criteria -> (criteria.getDateCheckin() != null && criteria.getDateCheckout() != null))
				.orElseThrow(() 
				 -> new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_PERIODE_INCORRECTE));
		
			//Chargement des réservations en fonction des critères d'entrée.
		List<Reservation> reservations = reservationDao.findReservationByCriteria(reservationCriteria);
		//Transformation de toutes les réservation en ReservationDto
		return reservations.stream()
							.map(reservation 
							-> mapper.reservationToReservationDto(reservation))
							.collect(Collectors.<ReservationDto> toList());
			
		}

	
	@Transactional(readOnly = true)
	@Override
	public  Map<Long, List<Reservation>> findReservationByPeriodAndBien(LocalDateTime dateDebut, LocalDateTime dateFin, Long idBien) 
			throws TechnicalException,FunctionalException{
		
		log.debug("Service findReservationByPeriodAndBien");
		
		Map<Long, List<Reservation>> results = new HashMap<>();
		//Validation des paramètres d'entrée.
		validateScheduleParam(dateDebut, dateFin, idBien);
		
		//Chargement des réservations en fonction des critères d'entrée.
		List<Reservation> reservations = reservationDao.findReservationByPeriodAndBien(dateDebut, dateFin, idBien);
		//Transformation du résultat en Map de id appartement -> id reservations
		 reservations.forEach(resa -> {
			  resa.getAppartements().forEach(app -> {
				  List<Reservation> resaList = results.get(app.getId());
				  if(resaList == null) {
					  resaList = new ArrayList<>();
				  }
				  resaList.add(resa);
				  results.put(app.getId(), resaList);
				 
			  });
		});
			
		return results;
	}

	
	@Override
	@Cacheable
	@Transactional(readOnly = true)
	public Map<Long, List<UniteReservation>> loadSchedule(LocalDateTime dateDebut, 
			LocalDateTime dateFin, Long idBien) throws FunctionalException, TechnicalException{
		
		log.debug("Service loadSchedule");
		
		List<Nuitee> nuitees = new ArrayList<>();
		Map<Long, List<UniteReservation>> planningParAppart = new HashMap<>();
		
		//Récupération de tous les appartements associés au bien
		List<Appartement> appartements = appartementDao.findAppartementByCriteria(null, null, idBien);
		
		//Récupérer la liste de réservations de chaque appartement du Bien en entrée
		Map<Long, List<Reservation>> reservationParAppart = 
				findReservationByPeriodAndBien(dateDebut, dateFin, idBien);
		
		/************************************************************
		 * Construction d'une liste de nuitées à partir de la période
		 * Il faut une nuitée pour chaque jour entre
		 * la dateDebut et la dateFin
		 ************************************************************/
		//Génération pour le premier jour
		generateUniteReservation(dateDebut, nuitees);
		
		//Génération pour les jours suivants.
		while(!CustomDateUtil.isSameDay(dateDebut, dateFin)) {
			generateUniteReservation(dateDebut.plusDays(1), nuitees);
			dateDebut = dateDebut.plusDays(1);
		}
		/***********************************************
		 * Construire le planning de réservation
		 * On crée une unité de réservation pour chaque
		 * nuitée contenant l'appart, la nuitée et 
		 * toutes les réservations de la nuitée.
		 * 
		 ***********************************************/
		reservationParAppart.forEach((key, values) -> {
			List<UniteReservation> planning = new ArrayList<>();
			
			//Génération du planning pour les nuitées
			createScheduleForNuitee(nuitees, key, values, planning);
			
			/***********************************************
			 * Gestion des day-uses:
			 * On parcourt les unités de réservation
			 * dont la liste de réservations est vide.
			 * Elles correspondent potentiellement aux 
			 * day-uses.
			 ***********************************************/
			createScheduleForDayUse(values, planning);
			
			
			//S'il existe un appartement qui ne figure pas sur le 
			//planning, il faut l'ajouter avec une liste de réservation vide.
			
			planningParAppart.put(key, planning);
		});
		
		if(CollectionUtils.isNotEmpty(appartements)) {
			Set<Long> keysSet = planningParAppart.keySet();
			
			createScheduleForFreeApart(nuitees, planningParAppart, appartements, keysSet);
		}
		

		return planningParAppart;
	}

	
	@Override
	@Transactional
	public ReservationDto cancelReservation(ReservationDto reservationDto) throws TechnicalException {
		
		log.debug("Service cancelReservation");

		//statut autorisé pour annuler une réservation
		List<String> statutAutorisee = Arrays.asList(EnumStatutReservation.ENREGISTREE.getStatut() , 
				EnumStatutReservation.CONFIRMEE.getStatut(), EnumStatutReservation.EN_ATTENTE.getStatut());
		
		//Validations des paramètres d'entrée
		
		//L'entité doit exister en BDD
		Optional.ofNullable(reservationDto)
		.filter(dto -> dto.getId() != null)
		.orElseThrow(() 
		 -> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_MODIFICATION_NULL));
		
		//La note est obligatoire lors de l'annulation d'une réservation
		Optional.ofNullable(reservationDto).filter(resa -> StringUtils.isNotEmpty(resa.getNote())).orElseThrow(() 
					 -> new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_ANNULATION_NON_JUSTIFIEE));
		
		// statut de résa différent de « Enregistrée » , « Confirmée » et « En attente »
		//pour pouvoir être annulé.
		if(!statutAutorisee.contains(reservationDto.getStatut())) {
			throw new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_ANNULATION_RESERVATION_STATUT_INCORRECT);
		}
		
		//Si tous les paramètres sont ok, on passe à l'annulation 
		reservationDto.setStatut(EnumStatutReservation.ANNULEE.getStatut());
		
		//Positionner une date d'annulation à la date du jour
		reservationDto.setDateAnnulation(LocalDateTime.now());
		
		//Transformation en entité Reservation
		Reservation entite = mapper.reservationDtoToReservation(reservationDto);
		
		//Sauvegarde de l'annulation de la réservation
		reservationDao.saveOrUpdate(entite);
		
		//Transformation de l'entité enresgistré en ReservationDto
		return mapper.reservationToReservationDto(entite);
	}
	
	
	/**
	 * Création de cellules du planning pour 
	 * les dates sans réservations
	 * 
	 * @param nuitees
	 * @param planningParAppart
	 * @param appartements
	 * @param keysSet
	 */
	private void createScheduleForFreeApart(List<Nuitee> nuitees, Map<Long, List<UniteReservation>> planningParAppart,
			List<Appartement> appartements, Set<Long> keysSet) {
		appartements.stream().map(app -> app.getId())
			.filter(idApp -> !keysSet.contains(idApp))
			.collect(Collectors.toList())
			.forEach(idAppartementManquant -> {
				List<UniteReservation> planning = new ArrayList<>();
				nuitees.forEach(nuitee -> {
					// Création d'une unité de réservation qui correspond 
					// à une cellule dans le tableau de réservation
					UniteReservation cellule = new UniteReservation();
					cellule.setIdAppartement(idAppartementManquant);
					cellule.setNuite(nuitee);
					cellule.setJournee(nuitee.dateCheckin().toLocalDate());
					cellule.setIdReservations(Arrays.asList());
					planning.add(cellule);	
					});
				
				planningParAppart.put(idAppartementManquant, planning);
			});
	}

	
	/**
	 * Création d'une cellule de planning pour chaque
	 * chaque day-use.
	 * 
	 * @param values
	 * @param planning
	 */
	private void createScheduleForDayUse(List<Reservation> values, List<UniteReservation> planning) {
		planning.stream().filter(plng -> plng.getIdReservations().isEmpty()).forEach(cellule ->
		{
			List<Long> dayUses = values
					 .stream()
					 .filter(resa -> 
							 	CustomDateUtil.isPeriodBetweenInclusive(
					 			cellule.getNuite().dateCheckin(), 
					 			cellule.getNuite().dateCheckout(),
								resa.getDateCheckin(), 
								resa.getDateCheckout())) 
					.map(reservation -> reservation.getId())
					.collect(Collectors.toList());
			
			cellule.setIdReservations(dayUses);
			cellule.setJournee(cellule.getNuite().dateCheckin().toLocalDate());
		});
	}
	

	/**
	 * Création d'une cellule de planning pour chaque
	 * chaque nuitées
	 * 
	 * @param nuitees
	 * @param key
	 * @param values
	 * @param planning
	 */
	private void createScheduleForNuitee(List<Nuitee> nuitees, Long key, List<Reservation> values,
			List<UniteReservation> planning) {
		
		nuitees.forEach(nuitee -> {
		 List<Long> dailyResa = values
				 .stream()
				 .filter(resa -> 
						 	CustomDateUtil.isPeriodBetweenInclusive(
							resa.getDateCheckin(), 
							resa.getDateCheckout(), 
							nuitee.dateCheckin(), 
							nuitee.dateCheckout()))
				.map(reservation -> reservation.getId())
				.collect(Collectors.toList());
		// Création d'une unité de réservation qui correspond 
		// à une cellule dans le tableau de réservation
		UniteReservation cellule = new UniteReservation();
		cellule.setIdAppartement(key);
		cellule.setNuite(nuitee);
		cellule.setIdReservations(dailyResa);
		cellule.setJournee(nuitee.dateCheckin().toLocalDate());
		planning.add(cellule);	
		});
	}

	/**
	 * Créer une nouvelle nuitée 
	 * 
	 * @param dateDebut
	 * @param uniteResas
	 * @throws TechnicalException
	 */
	private void generateUniteReservation(LocalDateTime dateDebut, List<Nuitee> uniteResas)
			throws TechnicalException {
		
		//Formatage en date chechin (positionnement de l'heure à 12h00
		LocalDateTime checkin = CustomDateUtil
				.formatDateByFormatOption(dateDebut, EnumOptionFormatDate.CHECKIN_FORMAT);
		//Formatage en date chechout (positionnement de l'heure à 11h00
		LocalDateTime checkout = CustomDateUtil
				.formatDateByFormatOption(dateDebut.plusDays(1), EnumOptionFormatDate.CHECKOUT_FORMAT);
		//Initialisation d'une unité de résa
		uniteResas.add(new Nuitee()
				.dateCheckin(checkin)
				.dateCheckout(checkout));
	}
	
	
	/**
	 * <p>
	 * Gestion des day-uses et des nuitée
	 * pour rappel, un day-use est une réservation de
	 * 4h maximun. Une nuitée est une réservation qui 
	 * commence à 12h et se termine le lendemain à 11h.
	 * 
	 * Set fonction permet de positionner les dates checkin et 
	 * checkout à la bonne heure.
	 * </p>
	 * 
	 * @param <T>
	 * @param reservationDto
	 * @throws TechnicalException
	 */
	private <T extends IPeriode> void manageOvernightStayAndDayUse(T reservationDto) throws TechnicalException {
		//Vérifier si la réservation est un day-use 
		//c'est à dire moins d'une nuité.
		boolean isDayUse = CustomDateUtil.isSameDay(reservationDto.getDateCheckin(),
				reservationDto.getDateCheckout());
		
		if(isDayUse) {
			//Une day-use dure 4h
			reservationDto.setDateCheckout(reservationDto.getDateCheckin().plusHours(5));
		}else {
			//Il s'agit là d'une réservation d'une ou plusieurs nuitées.
			//Une réservation commence à 12h, le jour du checkin
			LocalDateTime dateChekin = CustomDateUtil.formatDateByFormatOption(reservationDto.getDateCheckin(),
					EnumOptionFormatDate.CHECKIN_FORMAT);
			reservationDto.setDateCheckin(dateChekin);
			
			//Une réservation se termine à 11h, le jour du checkout
			LocalDateTime dateChekout = CustomDateUtil.formatDateByFormatOption(reservationDto.getDateCheckout(),
					EnumOptionFormatDate.CHECKOUT_FORMAT);
			reservationDto.setDateCheckout(dateChekout);
		}
	}
	
	
	/**
	 * Vérifier les paramètre d'entrée, 
	 * Mapper le dto en entité et sauvegarde la réservation
	 * 
	 * @param reservationDto
	 * @return ReservationDto
	 * @throws TechnicalException 
	 */
	private ReservationDto controlMapAndSave(ReservationDto reservationDto) throws TechnicalException {
		
		//statut autorisé pour la réservation
		List<String> statutAutorisee = Arrays.asList(EnumStatutReservation.ENREGISTREE.getStatut() , 
				EnumStatutReservation.CONFIRMEE.getStatut(), EnumStatutReservation.EN_ATTENTE.getStatut());
		
		//Vérification de la validité de la réservation
		validateReservation(reservationDto, statutAutorisee);
		
		//Vérifier s'il existe une réservation enregistrée, Confirmée ou en attente dans la période
		boolean hasValideBookingInPeriod = hasValideBookingInPeriod(reservationDto, statutAutorisee);
		
		//En cas de conflit, la réservation en cours passe en liste d'attente.
		if(hasValideBookingInPeriod) {
			reservationDto.setStatut(EnumStatutReservation.EN_ATTENTE.getStatut());
		}
		//En mode création la date de création doit être initialisée à la date du jour.
		if(reservationDto.getId() == null) {
			reservationDto.setDateCreation(LocalDateTime.now());
		}
		//Gérer les day-use et les nuitées.
		manageOvernightStayAndDayUse(reservationDto);
		
		//Transformation en entité Reservation
		Reservation entite = mapper.reservationDtoToReservation(reservationDto);
		
		//Sauvegarde de la réservation
		reservationDao.saveOrUpdate(entite);
		
		//Transformation de l'entité enresgistré en ReservationDto
		return mapper.reservationToReservationDto(entite);
	}
	
	
	/**
	 * <p>
	 * Vérification de la réservation.
	 * Les points suivants sont vérifiés:
	 * 
	 * - Paramètres obligatoires non nulls
	 * - statut de résa doit être « Enregistrée » ou « Confirmée »
	 * - « dateCheckin » >= date du jour
	 * - « dateChekout » >  « dateCheckin ».
	 * - Non existance d'une résa « Enregistrée » ou « Confirmée » dans la période.
	 * - Note différent de null, si le prix de la réservation < à celui de l’appartement. 
	 * </p>
	 * 
	 * <p>
	 * @param reservationDto
	 * @param statutAutorisee
	 * @throws TechnicalException 
	 * </p>
	 */
	private void validateReservation(ReservationDto reservationDto, 
			List<String> statutAutorisee) throws FunctionalException, TechnicalException {
		
		//Vérification des paramètres obligatoires.
		checkParamsNull(reservationDto);
		//Vérification de la validité des paramétres
		checkBadParameters(reservationDto, statutAutorisee);
	}

	/**
	 * Contrôle de la validité fonctionnelle des
	 * des paramètres
	 * 
	 * @param reservationDto
	 * @param statutAutorisee
	 * 
	 * @throws FunctionalException
	 * @throws TechnicalException
	 */
	private void checkBadParameters(ReservationDto reservationDto, List<String> statutAutorisee)
			throws FunctionalException, TechnicalException {
		
		// date du jour à minuit 00:00:00
		LocalDateTime now = CustomDateUtil.formatDateByFormatOption(LocalDateTime.now(),
				EnumOptionFormatDate.START_OF_DAY_FROMAT);
		
		if(reservationDto.getDateAnnulation() != null){
			throw new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_ANNULATION_NON_NULL);
		}
		
		// statut de résa différent de « Enregistrée » , « Confirmée » et « En attente »
		if(!statutAutorisee.contains(reservationDto.getStatut())) {
			throw new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_STATUT_INCORRECT);
		}
		//dateCheckin doit être supérieure à date du jour
		if(now.isAfter(reservationDto.getDateCheckin())) {
			throw new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKIN_INCORRECT);
		}
		// dateChekout doit être supérieure dateCheckin.
		if(reservationDto.getDateCheckin().isAfter(reservationDto.getDateCheckout())) {
			throw new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKOUT_INCORRECT);		
		}
		
		// La note est obligatoire, si le prix de la réservation n'est pas cohérent
		boolean isPriceConsistent = isPriceConsistent(reservationDto);
		if(!isPriceConsistent && StringUtils.isEmpty(reservationDto.getNote())) {
			throw new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_REMISE_NON_JUSTIFIEE);
		}
		
	}
	
	/**
	 * Vérifie que les paramètres obligatoire
	 * ne sont pas null
	 * 
	 * @param reservationDto
	 * @throws FunctionalException
	 */
	private void checkParamsNull(ReservationDto reservationDto) throws FunctionalException {
		
		// Reservation ne doit pas être nulle
		Optional.ofNullable(reservationDto).orElseThrow(() 
				 -> new FunctionalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_CREATION_NULL));
		
		// Statut reservation ne doit pas être nul ou vide
		Optional.ofNullable(reservationDto.getStatut())
			.filter(statut -> !statut.isEmpty())
			.orElseThrow(() 
				 -> new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_STATUT_NULL));
		
		// Date checkin ne doit pas être nulle
		Optional.ofNullable(reservationDto.getDateCheckin()).orElseThrow(() 
					 -> new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKIN_NULL));
		
		// Date checkout ne doit pas être nulle
		Optional.ofNullable(reservationDto.getDateCheckout()).orElseThrow(() 
				 -> new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKOUT_NULL));
	}

	/**
	 * Permet de valider la période saisie
	 * 
	 * <p>
	 * @param dateDebut
	 * @param dateFin
	 * @param idBien
	 * @throws FunctionalException
	 * </p>
	 */
	private void validateScheduleParam(LocalDateTime dateDebut, 
			LocalDateTime dateFin, Long idBien) throws FunctionalException {
		
		// Date checkin ne doit pas être nulle
		 Optional.ofNullable(dateDebut).orElseThrow(() 
				 -> new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKIN_NULL));
	
		// Date checkout ne doit pas être nulle
		 Optional.ofNullable(dateFin).orElseThrow(() 
				-> new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKOUT_NULL));

		// dateChekout doit être supérieure dateCheckin.
		if(dateDebut.isAfter(dateFin)) {
			throw new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_DATE_CHECKOUT_INCORRECT);		
		}
		
		// l'id du bien ne doit pas être nulle
		 Optional.ofNullable(idBien).orElseThrow(() 
				-> new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_BIEN_NULL));
	
		// La période ne doit pas couvrir plus de 30 jours.
		if(Days.daysBetween(dateDebut, dateFin).getDays() > 30) {
			throw new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_PERIODE_SUP_30_JOURS);		
		}
	}
	
	
	@Override
	public boolean hasValideBookingInPeriod(ReservationDto reservationDto, List<String> statutAutorisee) throws FunctionalException, TechnicalException {
		
		ReservationCriteria reservationCriteria = new ReservationCriteria();
		reservationCriteria.setDateCheckin(reservationDto.getDateCheckin());
		reservationCriteria.setDateCheckout(reservationDto.getDateCheckout());
		
		//Positionnement de la période au format checkin - checkout
		manageOvernightStayAndDayUse(reservationCriteria);
		//Appel de findReservationByCriteria avec les critères 
		//Periode, statut et ids appartement
		reservationCriteria.setStatut(statutAutorisee);
		reservationCriteria.setIdAppartements(reservationDto
				.getAppartements()
				.stream()
				.map(appart 
						-> appart.getId()).collect(Collectors.toList()));
		
		List<ReservationDto> reservations = findReservationByCriteria(reservationCriteria);
		//True si résultat false sinon
		boolean isntIt = (reservations.size() > 0);
		
		return isntIt;
	}

	
	/**
	 * Permet de vérifier la cohérence du tarif
	 * de la réservation.
	 * Chaque appartement a son prix de référence. 
	 * Le prix total d'une réservation est cohérent 
 	 * s'il est supérieur ou égal à 
	 * la somme des prix de chaque appartement.
	 * 
	 * @throws FunctionalException
	 */
	private boolean isPriceConsistent(ReservationDto reservationDto) throws FunctionalException{
		//Calcul du prix total des appartements en cours de réservation
		double prixTotal = reservationDto.getAppartements()
				.stream()
				.mapToDouble(app -> app.getPrix().longValue())
				.sum();
		
		boolean isntIt = (reservationDto.getPrix() >= prixTotal);
		return isntIt;
	}
}
