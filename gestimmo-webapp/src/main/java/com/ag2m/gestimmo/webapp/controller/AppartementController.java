package com.ag2m.gestimmo.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.service.AppartementService;
import com.ag2m.gestimmo.metier.service.BienService;

@RestController
public class AppartementController {

	@Autowired
	private AppartementService appartementService;
	
	@Autowired
	private BienService bienService;
	
	
	/**
	 * Retourne tous les appartements sous format Json
	 * 
	 * @return
	 */
		@RequestMapping(value = "/appartements", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody List<AppartementDto> findAllAppartement() {
	        List<AppartementDto> appartements = appartementService.findAll();
	        return appartements;
	    }
	  
	     
		/**
		 * Retourne l'appartement dont l'id est en paramètre, sous format Json
		 * 
		 * @return
		 */
	    @RequestMapping(value = "/appartements/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody AppartementDto getAppartement(@PathVariable("id") long id) {

	    	AppartementDto appartement = appartementService.findById(id);
	     
	    	return appartement;
	    }
	    
	    /**
		 * Crée un nouvel appartement
		 * 
		 */
	    @RequestMapping(value = "/appartements/save/idBien/{id}", method = RequestMethod.POST)
	    public @ResponseBody void saveAppartement(@PathVariable("id") long idBien) {

//	    	Bien bien = bienService.findById(idBien);
//	    	Appartement appartement = new Appartement();
//	    	appartement.setLibelle("Gokhou Mbath");
//	    	appartement.setBien(bien);
//	    	appartement.setType(EnumTypeAppartement.T2.getType());
//	    	appartementService.saveOrUpdate(appartement);
	     
	    }
	    
	    	/**
			 * supprime l'appartement dont l'id est en paramètre
			 * 
			 */
		    @RequestMapping(value = "/appartements/delete/id/{id}", method = RequestMethod.DELETE)
		    public @ResponseBody void deleteAppartement(@PathVariable("id") long id) {
		    	AppartementDto appartement = appartementService.findById(id);
		    	appartementService.delete(appartement);
		     
		    }
}
