package com.ag2m.gestimmo.webapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.service.BienService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
		 * @throws TechnicalException 
		 */
	    @RequestMapping(value = "/biens/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody BienDto getBien(@PathVariable("id") long id) throws TechnicalException {

	    	BienDto bien = bienService.findBienById(id);
	     
	    	return bien;
	    }
	    
	    /**
		 * Crée un nouveau bien
	     * @throws FunctionalException 
	     * @throws TechnicalException 
		 * 
		 */
	    @RequestMapping(value = "/biens/create", method = RequestMethod.POST)
	    public @ResponseBody BienDto createBien(@RequestBody BienDto bienDto) throws FunctionalException, TechnicalException {

	    	Optional.ofNullable(bienDto).orElseThrow(() 
					-> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_SUPP_NULL));
			
			return bienService.createBien(bienDto);
	     
	    }
	    
	    	/**
			 * supprime le bien dont l'id est en paramètre
	    	 * @throws FunctionalException 
	    	 * @throws TechnicalException 
			 * 
			 */
		    @RequestMapping(value = "/biens/delete/id/{id}", method = RequestMethod.DELETE)
		    public @ResponseBody void deleteBien(@PathVariable("id") long id) throws TechnicalException {
		    	BienDto bien = bienService.findBienById(id);
		    	bienService.deleteBien(bien);
		     
		    }
}
