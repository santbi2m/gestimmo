/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ag2m.gestimmo.metier.dto.AnomalieDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.dto.RoleDto;
import com.ag2m.gestimmo.metier.dto.UtilisateurDto;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.AnomalieService;
import com.ag2m.gestimmo.metier.service.AppartementService;
import com.ag2m.gestimmo.metier.service.BienService;
import com.ag2m.gestimmo.metier.service.ReservationService;
import com.ag2m.gestimmo.metier.service.RoleService;
import com.ag2m.gestimmo.metier.service.UtilisateurService;


/**
 * @author mombaye
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring-test-config.xml"})
public abstract class AbstractCommonTest {

	@Autowired
	protected BienService bienService;
	
	@Autowired
	protected AppartementService appartementService;
	
	@Autowired
	protected ReservationService reservationService;
	
	@Autowired
	protected AnomalieService anomalieService;
	
	@Autowired
	Mapper mapper;
	
	@Autowired
	EhCacheManagerFactoryBean cacheManager;
	
	@Autowired
	protected UtilisateurService utilisateurService;
	
	@Autowired
	protected RoleService roleService;
	
	@Autowired
	protected BCryptPasswordEncoder passwordEncoder;
	
	
	/**
	 * Permet de créer un Bien 
	 * 
	 * @param libelle
	 * @param adresse
	 * @param complementAdresse
	 * @param ville
	 * @param codePostal
	 * @param pays
	 * @return
	 */
	protected BienDto createBien(String libelle, String adresse, String complementAdresse, 
			String ville, Integer codePostal, String pays) {
		BienDto bien = new BienDto();
		bien.setLibelle(libelle);
		bien.setAdresse(adresse);
		bien.setComplementAdresse(complementAdresse);
		bien.setVille(ville);
		bien.setCodePostal(codePostal);
		bien.setPays(pays);
		
		bien = bienService.saveOrUpdate(bien);
		
		return bien;
	}
	
	/**
	 * Permet de créer un appartement 
	 * 
	 * @param libelle
	 * @param bien
	 * @param type
	 * @return
	 */
	protected AppartementDto createAppartement(String libelle, BienDto bien, String type) {
		AppartementDto appartement = new AppartementDto();
		appartement.setLibelle(libelle);
		appartement.setBien(bien);
		appartement.setType(type);
		
		appartement = appartementService.saveOrUpdate(appartement);
		
		return appartement;
	}
	
	
	/**
	 * Permet de créer une réservation
	 * 
	 * @param dateCheckin
	 * @param dateCheckout
	 * @param note
	 * @param petitDej
	 * @param statut
	 * @param appartements
	 * @return
	 */
	protected ReservationDto createReservation(LocalDateTime dateCheckin, LocalDateTime dateCheckout, 
			String note, Boolean petitDej, String statut, List<AppartementDto> appartements){
		
		final ReservationDto reservation = new ReservationDto();
		
		reservation.setDateCheckin(dateCheckin);
		reservation.setDateCheckout(dateCheckout);
		reservation.setNote(note);
		reservation.setPetitDej(petitDej);
		reservation.setStatut(statut);
		reservation.setAppartements(appartements);
		
		return reservationService.saveOrUpdate(reservation); 
		
	}
	
	/**
	 * Permet de créer une anomalie
	 * 
	 * @param appartement
	 * @param commentaire
	 * @param dateOuverture
	 * @param dateTraitement
	 * @param description
	 * @param statut
	 * @param titre
	 * @return
	 */
	protected AnomalieDto createAnomalie(AppartementDto appartement, String commentaire, 
			LocalDateTime dateOuverture, LocalDateTime dateTraitement, 
			String description, String statut, String titre){
		
		AnomalieDto anomalie = new AnomalieDto();
		anomalie.setAppartement(appartement);
		anomalie.setCommentaire(commentaire);
		anomalie.setDateOuverture(dateOuverture);
		anomalie.setDateTraitement(dateTraitement);
		anomalie.setDescription(description);
		anomalie.setStatut(statut);
		anomalie.setTitre(titre);
		
		anomalie = anomalieService.saveOrUpdate(anomalie);
		 return anomalie;
	}
	
	/**
	 * Permet de créer un utilisateur.
	 * 
	 * @param username
	 * @param password
	 * @param enabled
	 * @param roles
	 * @return
	 */
	protected UtilisateurDto createUtilisateur(String username, String password, boolean enabled) {
		
		UtilisateurDto utilisateur = new UtilisateurDto();
		String encodedPassword = passwordEncoder.encode(password);
		utilisateur.setPassword(encodedPassword);
		utilisateur.setUsername(username);
		utilisateur.setEnabled(enabled);
		
		utilisateur = utilisateurService.saveOrUpdate(utilisateur);
		return utilisateur;
	}
	
	/**
	 * Permet de créer un Role.
	 * 
	 * @param role
	 * @param utilisateur
	 * @return
	 */
	protected RoleDto createRole(String role, UtilisateurDto utilisateur) {
		RoleDto roleDto = new RoleDto();
		roleDto.setRole(role);
		roleDto.setUtilisateur(utilisateur);
		
		roleDto = roleService.saveOrUpdate(roleDto);
		return roleDto;
	}
	
}
