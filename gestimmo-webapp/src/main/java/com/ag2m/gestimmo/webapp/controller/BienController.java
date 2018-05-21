package com.ag2m.gestimmo.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.service.BienService;

@RestController
public class BienController {

	@Autowired
	private BienService bienService;
	
	
	/**
	 * Retourne tous les biens sous format Json
	 * 
	 * @return
	 */
		@RequestMapping(value = "/biens", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody List<BienDto> findAllBien() {
	        List<BienDto> biens = bienService.loadAllBien();
	        return biens;
	    }
	  
	     
		/**
		 * Retourne le bien dont l'id est en paramètre, sous format Json
		 * 
		 * @return
		 * @throws FunctionalException 
		 */
	    @RequestMapping(value = "/biens/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody BienDto getBien(@PathVariable("id") long id) throws FunctionalException {

	    	BienDto bien = bienService.findBienById(id);
	     
	    	return bien;
	    }
	    
	    /**
		 * Crée un nouveau bien
	     * @throws FunctionalException 
		 * 
		 */
	    @RequestMapping(value = "/biens/save", method = RequestMethod.POST)
	    public @ResponseBody void saveBien() throws FunctionalException {

//	    	BienDto bien = new BienDto();
//	    	bien.setLibelle("Résidence Kheweul");
//	    	bien.setAdresse("1 av Bourguiba");
//	    	bien.setCodePostal(99000);
//	    	bien.setVille("Saint-Louis");
//	    	bien.setPays("Sénégal");
//	    	bienService.createBien(bien);
	     
	    }
	    
	    	/**
			 * supprime le bien dont l'id est en paramètre
	    	 * @throws FunctionalException 
			 * 
			 */
		    @RequestMapping(value = "/biens/delete/id/{id}", method = RequestMethod.DELETE)
		    public @ResponseBody void deleteBien(@PathVariable("id") long id) throws FunctionalException {
		    	BienDto bien = bienService.findBienById(id);
		    	bienService.deleteBien(bien);
		     
		    }
}
