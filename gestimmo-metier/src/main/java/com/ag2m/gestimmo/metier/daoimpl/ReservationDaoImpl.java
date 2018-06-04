/**
 * 
 */
package com.ag2m.gestimmo.metier.daoimpl;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDateTime;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.ReservationDao;
import com.ag2m.gestimmo.metier.entite.Reservation;
import com.ag2m.gestimmo.metier.ioparam.ReservationCriteria;

/**
 * @author mombaye
 *
 */
@Repository
public class ReservationDaoImpl extends AbstractDao<Long, Reservation> implements ReservationDao {

	
	@Override
	@Cacheable
	public List<Reservation> findReservationByCriteria(ReservationCriteria reservationCriteria) {
		
		List<Predicate> predicates = new ArrayList<>();
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Reservation> criteria = criteriaBuilder.createQuery(Reservation.class);
		
		//select from Reservation
		Root<Reservation> reservations = criteria.from(Reservation.class);
		criteria.select(reservations);
		
		//Conditions de filtre sur les paramètres d'entrée
		
		//Date check-in
		if(reservationCriteria.getDateCheckin() != null) {
			Predicate dateCheckinCondition = criteriaBuilder.greaterThanOrEqualTo(reservations.get("dateCheckout"), 
					reservationCriteria.getDateCheckin());
			predicates.add(dateCheckinCondition);
		}
		//Date check-out
		if(reservationCriteria.getDateCheckout() != null) {
			Predicate dateCheckoutCondition = criteriaBuilder
					.and(criteriaBuilder.lessThanOrEqualTo(reservations.get("dateCheckin"), 
							reservationCriteria.getDateCheckout()));
			predicates.add(dateCheckoutCondition);
		}
		//Client
		if(reservationCriteria.getIdClient() != null) {
			Predicate clientCondition = criteriaBuilder
					.and(criteriaBuilder.equal(reservations.get("client").get("id"), 
							reservationCriteria.getIdClient()));
			predicates.add(clientCondition);
		}
		//Facture
		if(reservationCriteria.getIdFacture() != null) {
			Predicate factureCondition = criteriaBuilder
					.and(criteriaBuilder.equal(reservations.get("facture").get("id"), 
							reservationCriteria.getIdFacture()));
			predicates.add(factureCondition);
		}
		//Petit Dej
		if(reservationCriteria.getPetitDej() != null) {
			Predicate petitDejCondition = criteriaBuilder
					.and(criteriaBuilder.equal(reservations.get("petitDej"), 
							reservationCriteria.getPetitDej()));
			predicates.add(petitDejCondition);
		}
		//Prix
		if(reservationCriteria.getPrix() != null) {
			Predicate prixCondition = criteriaBuilder
					.and(criteriaBuilder.equal(reservations.get("prix"), 
							reservationCriteria.getPrix()));
			predicates.add(prixCondition);
		}
		//Appartements
		if(CollectionUtils.isNotEmpty(reservationCriteria.getIdAppartements())) {
			Join<Object, Object> appartements = reservations.join("appartements");
			Predicate appartCondition = appartements.get("id").in(reservationCriteria.getIdAppartements());
			predicates.add(appartCondition);
		}
		
		//Statuts
		if(reservationCriteria.getStatut() != null) {
			Predicate appartCondition = criteriaBuilder
					.and(reservations.get("statut")
							.in(reservationCriteria.getStatut()));
			predicates.add(appartCondition);
		}
		
		//where  all conditions
		if(!predicates.isEmpty()) {
			Predicate[] allConditions = predicates.toArray(new Predicate[predicates.size()]);
			criteria.where(allConditions);
		}
		
		return getCurrentSession().createQuery(criteria).getResultList();
	}
	
	
	@Override
	public List<Reservation> findReservationByPeriodAndBien(LocalDateTime dateDebut, LocalDateTime dateFin,
			Long idBien) {
		
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Reservation> criteria = criteriaBuilder.createQuery(Reservation.class);
		
		//select from Reservation
		Root<Reservation> reservations = criteria.from(Reservation.class);
		criteria.select(reservations);
		
		//Join appartements 
		Join<Object, Object> appartements = reservations.join("appartements");
	
		//Conditions de filtre sur les paramètres d'entrée
		Predicate predicate = criteriaBuilder.and(
				criteriaBuilder.greaterThanOrEqualTo(reservations.get("dateCheckout"), 
				dateDebut), 
				criteriaBuilder.lessThanOrEqualTo(reservations
						.get("dateCheckin"), dateFin), 
				criteriaBuilder.equal(appartements
						.get("bien").get("id"), idBien));
		
		//where  all conditions
		criteria.where(predicate);
		
		return getCurrentSession().createQuery(criteria).getResultList();
		}

}
