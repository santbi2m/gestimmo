package com.ag2m.gestimmo.metier.daoimpl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.DevisDao;
import com.ag2m.gestimmo.metier.entite.Devis;

@Repository
public class DevisDaoImpl extends AbstractDao<Long, Devis> implements DevisDao {

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
	
}
