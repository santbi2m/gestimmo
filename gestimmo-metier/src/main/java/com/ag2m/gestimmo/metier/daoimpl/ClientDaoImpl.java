package com.ag2m.gestimmo.metier.daoimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ag2m.gestimmo.metier.dao.ClientDao;
import com.ag2m.gestimmo.metier.entite.Client;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.ioparam.ClientCriteria;

@Repository
public class ClientDaoImpl extends AbstractDao<Long, Client> implements ClientDao{

	/*
	 * (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.dao.ClientDao#findClientByCriteria(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	@Cacheable
	public List<Client> findClientByCriteria(ClientCriteria clientCriteria) throws FunctionalException {
		List<Predicate> predicates = new ArrayList<>();
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Client> criteria = criteriaBuilder.createQuery(Client.class);
		
		//select from appartement
		Root<Client> clients = criteria.from(Client.class);
		criteria.select(clients);
		
		//Conditions de filtre sur les paramètres d'entrée
		if(StringUtils.isNotEmpty(clientCriteria.getNom())) {
			Predicate nomCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("nom")), clientCriteria.getNom().toUpperCase());
			predicates.add(nomCondition);
		}
		
		if(StringUtils.isNotEmpty(clientCriteria.getPrenom())) {
			Predicate prenomCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("prenom")), clientCriteria.getPrenom().toUpperCase());
			predicates.add(prenomCondition);
		}
		
		if(StringUtils.isNotEmpty(clientCriteria.getAdresseEmail())) {
			Predicate adresseEmailCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("adresseEmail")), clientCriteria.getAdresseEmail().toUpperCase());
			predicates.add(adresseEmailCondition);
		}
		
		if(StringUtils.isNotEmpty(clientCriteria.getNumeroPiece())) {
			Predicate numeroPieceCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("numeroPieceIdentite")), clientCriteria.getNumeroPiece().toUpperCase());
			predicates.add(numeroPieceCondition);
		}
		
		if(StringUtils.isNotEmpty(clientCriteria.getTypePiece())) {
			Predicate typePieceCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("typePieceIdentite")), clientCriteria.getTypePiece().toUpperCase());
			predicates.add(typePieceCondition);
		}
		
		if(StringUtils.isNotEmpty(clientCriteria.getTelephone())) {
			Predicate telephoneCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("telephone")), clientCriteria.getTelephone().toUpperCase());
			predicates.add(telephoneCondition);
		}
		
		if(StringUtils.isNotEmpty(clientCriteria.getAdresse())) {
			Predicate adresseCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("adresse").get("adresse")), clientCriteria.getAdresse().toUpperCase());
			predicates.add(adresseCondition);
		}
		
		
		if(clientCriteria.getCodePostal() != null) {
			Predicate codePostalCondition = criteriaBuilder.and(criteriaBuilder.equal(clients.get("adresse").get("codePostal"), clientCriteria.getCodePostal()));
			predicates.add(codePostalCondition);
		}
		
		if(StringUtils.isNotEmpty(clientCriteria.getVille())) {
			Predicate villeCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("adresse").get("ville")), clientCriteria.getVille().toUpperCase());
			predicates.add(villeCondition);
		}
		
		if(StringUtils.isNotEmpty(clientCriteria.getPays())) {
			Predicate paysCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("adresse").get("pays")), clientCriteria.getPays().toUpperCase());
			predicates.add(paysCondition);
		}
		//where  condition1 and condition2 and condition3
		if(!predicates.isEmpty()) {
			Predicate[] allConditions = predicates.toArray(new Predicate[predicates.size()]);
			criteria.where(allConditions);
		}
		
		return getCurrentSession().createQuery(criteria).getResultList();
	}

	
}
