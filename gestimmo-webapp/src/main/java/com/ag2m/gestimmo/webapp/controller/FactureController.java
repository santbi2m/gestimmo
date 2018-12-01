package com.ag2m.gestimmo.webapp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.FactureDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.FactureCriteria;
import com.ag2m.gestimmo.metier.ioparam.ReservationCriteria;
import com.ag2m.gestimmo.metier.service.AppartementService;
import com.ag2m.gestimmo.metier.service.BienService;
import com.ag2m.gestimmo.metier.service.ClientService;
import com.ag2m.gestimmo.metier.service.FactureService;
import com.ag2m.gestimmo.metier.service.ReservationService;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
public class FactureController {

	@Autowired
	private FactureService factureService;
	
	@Autowired
	private AppartementService appartementService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private ClientService clienService;
	
	
	/**
	 * Retourne tous les factures sous format Json
	 * 
	 * @return
	 */
		@RequestMapping(value = "/factures", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody List<FactureDto> findAllFacture() {
	        List<FactureDto> factures = factureService.loadAllFactures();
	        return factures;
	    }
	  
	     
		/**
		 * Retourne le facture dont l'id est en paramètre, sous format Json
		 * 
		 * @return
		 * @throws FunctionalException 
		 * @throws TechnicalException 
		 */
	    @RequestMapping(value = "/factures/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody FactureDto getFacture(@PathVariable("id") long id) throws TechnicalException {

	    	FactureDto facture = factureService.findFactureById(id);
	     
	    	return facture;
	    }
	    
	    /**
		 * Crée un nouveau facture
	     * @return 
	     * @throws FunctionalException 
	     * @throws TechnicalException 
	     * @throws IOException 
		 * 
		 */
	    @RequestMapping(value = "/genererFacture", method = RequestMethod.GET,  
	    		produces = MediaType.APPLICATION_PDF_VALUE)
	    public void genererFacture(HttpServletResponse response ) throws FunctionalException, TechnicalException, IOException {

	    	FactureDto factureDto = new FactureDto();
	    	
	    	FactureCriteria factureCriteria = new FactureCriteria();
	    	factureCriteria.setLibelle("Dem Deloussi");
	    	factureCriteria.setNomClient("Maiga");
			//factureDto = factureService.findFactureById(2L);
	    	factureDto = factureService.findFactureByCriteria(factureCriteria).get(0);
			
			ReservationCriteria reservationCriteria = new ReservationCriteria();
			reservationCriteria.setIdFacture(factureDto.getId());
			reservationCriteria.setDateCheckin(new LocalDateTime(2018,3,2,0,0));
			reservationCriteria.setDateCheckout(new LocalDateTime(2018,10,20,0,0));
			
			List<ReservationDto> reservationDtos = new ArrayList<>();
			List<AppartementDto> appartementDtos = new ArrayList<>();
			List<AppartementDto> appartementDtos2 = new ArrayList<>();
			ReservationDto reservationDto =  reservationService.findReservationById(8L);
			ReservationDto reservationDto2 =  reservationService.findReservationById(9L);
			appartementDtos.add(appartementService.findAppartementById(8L));
			appartementDtos2.add(appartementService.findAppartementById(8L));
			reservationDto.setAppartements(appartementDtos);
			reservationDto2.setAppartements(appartementDtos2);
			
			reservationDtos.add(reservationDto);
			reservationDtos.add(reservationDto2);
			
			factureDto.setReservations(reservationDtos);

			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			String filemane = "Facture-"+sdf.format(new Date()) + ".pdf";
			
						
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename=" + filemane);
			OutputStream outputStream = response.getOutputStream();
			
			outputStream.write(factureService.genererFacture(factureDto).toByteArray());

			outputStream.flush();
			outputStream.close();
	    }
	    
		   
}
