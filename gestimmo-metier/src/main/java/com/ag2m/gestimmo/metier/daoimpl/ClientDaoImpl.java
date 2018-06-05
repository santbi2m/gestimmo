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

@Repository
public class ClientDaoImpl extends AbstractDao<Long, Client> implements ClientDao{

	/*
	 * (non-Javadoc)
	 * @see com.ag2m.gestimmo.metier.dao.ClientDao#findClientByCriteria(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	@Cacheable
	public List<Client> findClientByCriteria(String nom, String prenom, String adresseEmail, String numeroPiece,
			String typePiece, String telephone, String adresse, String complement, Integer codePostal, String ville,
			String pays) throws FunctionalException {
		List<Predicate> predicates = new ArrayList<>();
		//Initialiser un builder de requête
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Client> criteria = criteriaBuilder.createQuery(Client.class);
		
		//select from appartement
		Root<Client> clients = criteria.from(Client.class);
		criteria.select(clients);
		
		//Conditions de filtre sur les paramètres d'entrée
		if(StringUtils.isNotEmpty(nom)) {
			Predicate nomCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("nom")), nom.toUpperCase());
			predicates.add(nomCondition);
		}
		
		if(StringUtils.isNotEmpty(prenom)) {
			Predicate prenomCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("prenom")), prenom.toUpperCase());
			predicates.add(prenomCondition);
		}
		
		if(StringUtils.isNotEmpty(adresseEmail)) {
			Predicate adresseEmailCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("adresseEmail")), adresseEmail.toUpperCase());
			predicates.add(adresseEmailCondition);
		}
		
		if(StringUtils.isNotEmpty(numeroPiece)) {
			Predicate numeroPieceCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("numeroPieceIdentite")), numeroPiece.toUpperCase());
			predicates.add(numeroPieceCondition);
		}
		
		if(StringUtils.isNotEmpty(typePiece)) {
			Predicate typePieceCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("typePieceIdentite")), typePiece.toUpperCase());
			predicates.add(typePieceCondition);
		}
		
		if(StringUtils.isNotEmpty(telephone)) {
			Predicate telephoneCondition = criteriaBuilder.like(criteriaBuilder.upper(clients.get("telephone")), telephone.toUpperCase());
			predicates.add(telephoneCondition);
		}
		
		if(StringUtils.isNotEmpty(adresse)) {
			Predicate adresseCondition = criteriaBuilder.and(criteriaBuilder.equal(clients.get("adresse").get("adresse"), adresse));
			predicates.add(adresseCondition);
		}
		
		if(StringUtils.isNotEmpty(complement)) {
			Predicate complementCondition = criteriaBuilder.and(criteriaBuilder.equal(clients.get("adresse").get("complement_adresse"), complement));
			predicates.add(complementCondition);
		}

		
		if(codePostal != 0) {
			Predicate codePostalCondition = criteriaBuilder.and(criteriaBuilder.equal(clients.get("adresse").get("codePostal"), codePostal));
			predicates.add(codePostalCondition);
		}
		
		if(StringUtils.isNotEmpty(ville)) {
			Predicate villeCondition = criteriaBuilder.and(criteriaBuilder.equal(clients.get("adresse").get("ville"), ville));
			predicates.add(villeCondition);
		}
		
		if(StringUtils.isNotEmpty(pays)) {
			Predicate paysCondition = criteriaBuilder.and(criteriaBuilder.equal(clients.get("adresse").get("pays"), pays));
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
