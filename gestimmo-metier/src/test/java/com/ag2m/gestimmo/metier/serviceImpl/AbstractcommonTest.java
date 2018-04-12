/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ag2m.gestimmo.metier.dto.AnomalieDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.AnomalieService;
import com.ag2m.gestimmo.metier.service.AppartementService;
import com.ag2m.gestimmo.metier.service.BienService;
import com.ag2m.gestimmo.metier.service.ReservationService;


/**
 * @author mombaye
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring-test-config.xml"})
public abstract class AbstractcommonTest {

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
	
	
	protected AppartementDto createAppartement(String libelle, BienDto bien, String type) {
		AppartementDto appartement = new AppartementDto();
		appartement.setLibelle(libelle);
		appartement.setBien(bien);
		appartement.setType(type);
		
		appartement = appartementService.saveOrUpdate(appartement);
		
		return appartement;
	}
	
	
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
	
}
