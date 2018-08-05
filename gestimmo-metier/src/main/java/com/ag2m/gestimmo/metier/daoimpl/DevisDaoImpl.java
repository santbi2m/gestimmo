package com.ag2m.gestimmo.metier.daoimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.DevisDao;
import com.ag2m.gestimmo.metier.entite.Devis;
import com.ag2m.gestimmo.metier.ioparam.CommonFactureCriteria;

@Repository
public class DevisDaoImpl extends AbstractDao<Long, Devis> implements DevisDao {

	/* 
	 * @see com.ag2m.gestimmo.metier.dao.DevisDao#findLastNumDevis()
	 */
	@Override
	public String findLastNumDevis(){
		
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<String> criteria = criteriaBuilder.createQuery(String.class);
		
		//select numeroDevis from Devis
		Root<Devis> devis = criteria.from(Devis.class);
		criteria.select(devis.get("numeroDevis"));
		 
		//Récupérer uniquement la facture avec le plus grand id
		Subquery<Long> sub = criteria.subquery(Long.class);
		Root<Devis> subRoot = sub.from(Devis.class);
		sub.select(criteriaBuilder.max(subRoot.get("id")));
		 
		//condition : retourner le numéro de facture dont l'id correspond au max id
		criteria.where(criteriaBuilder.equal(devis.get("id"), sub));
		return getCurrentSession().createQuery(criteria).uniqueResult();		
	}

	
	
	/* 
	 * @see com.ag2m.gestimmo.metier.dao.DevisDao#findDevisByCriteria(com.ag2m.gestimmo.metier.ioparam.AbstractFactureCriteria)
	 */
	@Override
	public List<Devis> findDevisByCriteria(CommonFactureCriteria devisCriteria) {
		
		List<Predicate> predicates = new ArrayList<>();
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Devis> criteria = criteriaBuilder.createQuery(Devis.class);
		
		//select from Devis
		Root<Devis> devis = criteria.from(Devis.class);
		criteria.select(devis);
		
		//Conditions de filtre sur les paramètres d'entrée
		
		//Paramètre nom
		if(StringUtils.isNotEmpty(devisCriteria.getNom())) {
			Predicate nomCondition = criteriaBuilder.like(criteriaBuilder.upper(devis.get("nom")), devisCriteria.getNom().toUpperCase());
			predicates.add(nomCondition);
		}
		
		//Paramètre prénom
		if(StringUtils.isNotEmpty(devisCriteria.getPrenom())) {
			Predicate prenomCondition = criteriaBuilder.like(criteriaBuilder.upper(devis.get("prenom")), devisCriteria.getPrenom().toUpperCase());
			predicates.add(prenomCondition);
		}
		
		//Paramètre numéro de téléphone
		if(StringUtils.isNotEmpty(devisCriteria.getTelephone())) {
			Predicate phoneCondition = criteriaBuilder.like(criteriaBuilder.upper(devis.get("telephone")), devisCriteria.getTelephone().toUpperCase());
			predicates.add(phoneCondition);
		}
		
		//Paramètre adresse email
		if(StringUtils.isNotEmpty(devisCriteria.getAdresseEmail())) {
			Predicate emailCondition = criteriaBuilder.like(criteriaBuilder.upper(devis.get("adresseEmail")), devisCriteria.getAdresseEmail().toUpperCase());
			predicates.add(emailCondition);
		}
		
		//Paramètre numéro de devis
		if(StringUtils.isNotEmpty(devisCriteria.getNumero())) {
			Predicate numDevisCondition = criteriaBuilder.like(criteriaBuilder.upper(devis.get("numeroDevis")), devisCriteria.getNumero().toUpperCase());
			predicates.add(numDevisCondition);
		}
		
		//Paramètre Date check-in
		if(devisCriteria.getDateCheckin() != null) {
			Predicate dateCheckinCondition = criteriaBuilder.greaterThanOrEqualTo(devis.get("dateCheckout"), 
					devisCriteria.getDateCheckin());
			predicates.add(dateCheckinCondition);
		}
		//Paramètre Date check-out
		if(devisCriteria.getDateCheckout() != null) {
			Predicate dateCheckoutCondition = criteriaBuilder
					.and(criteriaBuilder.lessThanOrEqualTo(devis.get("dateCheckin"), 
							devisCriteria.getDateCheckout()));
			predicates.add(dateCheckoutCondition);
		}
		//where  condition1 and condition2 and condition3
		if(!predicates.isEmpty()) {
			Predicate[] allConditions = predicates.toArray(new Predicate[predicates.size()]);
			criteria.where(allConditions);
		}
		
		return getCurrentSession().createQuery(criteria).getResultList();	
		
	}
	
}
