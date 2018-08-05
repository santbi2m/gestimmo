/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.dto.DevisDto;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.CommonFactureCriteria;
import com.ag2m.gestimmo.metier.service.DevisService;
import com.ag2m.gestimmo.metier.service.GestimmoServiceTest;

/**
 * Bussiness Service de test 
 * permettant de faire appel à certains services
 * métier avec une nouvelle transaction.
 * Il est exclusivement utilisé par les TU.
 * 
 * @author mombaye
 *
 */
@Service
public class GestimmoServiceImplTest implements GestimmoServiceTest{

	@Autowired
	DevisService devisService;
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public DevisDto findDevisById(Long id) throws TechnicalException {
		
		return devisService.findDevisById(id);
	}


	@Override
	public List<DevisDto> findAllDevis() {
		return devisService.findAllDevis(); 
	}



	@Override
	public boolean deleteDevis(DevisDto entiteDto) throws TechnicalException {
		return devisService.deleteDevis(entiteDto);
	}


	@Override
	public List<DevisDto> findDevisByCriteria(CommonFactureCriteria devisCriteria)
			throws FunctionalException, TechnicalException {
		return devisService.findDevisByCriteria(devisCriteria);
	}

}
