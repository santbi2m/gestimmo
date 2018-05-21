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

import com.ag2m.gestimmo.metier.dao.BienDao;
import com.ag2m.gestimmo.metier.entite.Bien;
import com.ag2m.gestimmo.metier.exception.FunctionalException;

/**
 * @author mombaye
 *
 */
@Repository
public class BienDaoImpl extends AbstractDao<Long, Bien> implements BienDao {

	@Override
	@Cacheable
	public List<Bien> findBienByCriteria(String libelle, String adresse, String complement, Integer codePostal,
			String ville, String pays) throws FunctionalException {
		
			List<Predicate> predicates = new ArrayList<>();
			//Initialiser un builder de requête
			CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<Bien> criteria = criteriaBuilder.createQuery(Bien.class);
			
			//select from appartement
			Root<Bien> biens = criteria.from(Bien.class);
			criteria.select(biens);
			
			//Conditions de filtre sur les paramètres d'entrée
			if(StringUtils.isNotEmpty(libelle)) {
				Predicate libelleCondition = criteriaBuilder.like(criteriaBuilder.upper(biens.get("libelle")), libelle.toUpperCase());
				predicates.add(libelleCondition);
			}
			
			
			if(StringUtils.isNotEmpty(adresse)) {
				Predicate adresseCondition = criteriaBuilder.and(criteriaBuilder.equal(biens.get("adresse").get("adresse"), adresse));
				predicates.add(adresseCondition);
			}
			
			if(StringUtils.isNotEmpty(complement)) {
				Predicate complementCondition = criteriaBuilder.and(criteriaBuilder.equal(biens.get("adresse").get("complement_adresse"), complement));
				predicates.add(complementCondition);
			}

			
			if(codePostal != 0) {
				Predicate codePostalCondition = criteriaBuilder.and(criteriaBuilder.equal(biens.get("adresse").get("codePostal"), codePostal));
				predicates.add(codePostalCondition);
			}
			
			if(StringUtils.isNotEmpty(ville)) {
				Predicate villeCondition = criteriaBuilder.and(criteriaBuilder.equal(biens.get("adresse").get("ville"), ville));
				predicates.add(villeCondition);
			}
			
			if(StringUtils.isNotEmpty(pays)) {
				Predicate paysCondition = criteriaBuilder.and(criteriaBuilder.equal(biens.get("adresse").get("pays"), pays));
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
