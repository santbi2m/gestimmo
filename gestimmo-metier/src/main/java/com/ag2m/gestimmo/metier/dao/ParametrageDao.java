package com.ag2m.gestimmo.metier.dao;

import java.util.List;

import com.ag2m.gestimmo.metier.entite.referentiel.Parametrage;
import com.ag2m.gestimmo.metier.entite.referentiel.Remise;
import com.ag2m.gestimmo.metier.enumeration.EnumTypeParametrage;

/**
 * @author mombaye
 *
 * Dao pour Parametrage.
 * Contient tout le paramétrage à charger au démarage de l'application.
 */
public interface ParametrageDao {

	/**
	 * Charge toutes les remises en BDD.
	 * 
	 * @return La liste des remises disponible en BDD.
	 */
	List<Remise> loadAllRemise();

	/**
	 * Recherche et charge la paramétrage en BDD, dont le type est
	 * passé en paramètre 
	 * 
	 * @param type : type de paramétrage (la liste des types de paramétrage est disponible dans 
	 * {@link EnumTypeParametrage}
	 * 
	 * @return Le paramétrage éligible.
	 */
	Parametrage loadParametrageByType(String type);
}
