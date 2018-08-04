package com.ag2m.gestimmo.metier.daoimpl;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.FactureDao;
import com.ag2m.gestimmo.metier.entite.Facture;

@Repository
public class FactureDaoImpl extends AbstractDao<Long, Facture> implements FactureDao{

	@Override
	public String findLastNumFacture(){
		
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<String> criteria = criteriaBuilder.createQuery(String.class);
		
		//select numeroFacture from Facture
		Root<Facture> facture = criteria.from(Facture.class);
		criteria.select(facture.get("numeroFacture"));
		 
		//Récupérer uniquement la facture avec le plus grand id
		Subquery<Long> sub = criteria.subquery(Long.class);
		Root<Facture> subRoot = sub.from(Facture.class);
		sub.select(criteriaBuilder.max(subRoot.get("id")));
		 
		//condition : retourner le numéro de facture dont l'id correspond au max id
		criteria.where(criteriaBuilder.equal(facture.get("id"), sub));
		return getCurrentSession().createQuery(criteria).uniqueResult();		
	}
	
}
