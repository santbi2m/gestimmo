package com.ag2m.gestimmo.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag2m.gestimmo.metier.dto.AnomalieDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.service.AnomalieService;
import com.ag2m.gestimmo.metier.service.AppartementService;

@RestController
public class AnomalieController {

	@Autowired
	private AppartementService appartementService;
	
	@Autowired
	private AnomalieService anomalieService;
	
	
		/**
		 * Retourne toutes les anomalies sous format Json
		 * 
		 * @return
		 */
		@RequestMapping(value = "/anomalies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody List<AnomalieDto> findAllAnomalie() {
	        List<AnomalieDto> anomalies = anomalieService.findAllAnomalie();
	        return anomalies;
	    }
	  
	     
		/**
		 * Retourne l'anomalie dont l'id est en paramètre, sous format Json
		 * 
		 * @return
		 * @throws FunctionalException 
		 */
	    @RequestMapping(value = "/anomalies/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody AnomalieDto getAnomalie(@PathVariable("id") long id) throws FunctionalException {

	    	AnomalieDto anomalie = anomalieService.findAnomalieById(id);
	     
	    	return anomalie;
	    }
	    
	    /**
		 * Crée un nouvel anomalie 
		 * 
		 */
//	    @RequestMapping(value = "/anomalies/save/idBien/{id}", method = RequestMethod.POST)
//	    public @ResponseBody void saveAppartement(@PathVariable("id") long idBien) {
//
//	    	Bien bien = anomalieService.findById(idBien);
//	    	Appartement appartement = new Appartement();
//	    	appartement.setLibelle("Gokhou Mbath");
//	    	appartement.setBien(bien);
//	    	appartement.setType(EnumTypeAppartement.T2.getType());
//	    	appartementService.saveOrUpdate(appartement);
//	     
//	    }
	    
	    	/**
			 * supprime l'appartement dont l'id est en paramètre
			 * 
			 */
//		    @RequestMapping(value = "/anomalies/delete/id/{id}", method = RequestMethod.DELETE)
//		    public @ResponseBody void deleteAppartement(@PathVariable("id") long id) {
//		    	Appartement appartement = appartementService.findById(id);
//		    	appartementService.delete(appartement);
//		     
//		    }
	    
}
