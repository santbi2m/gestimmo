package com.ag2m.gestimmo.metier.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.dao.UtilisateurDao;
import com.ag2m.gestimmo.metier.dto.RoleDto;
import com.ag2m.gestimmo.metier.dto.UtilisateurDto;
import com.ag2m.gestimmo.metier.entite.Role;
import com.ag2m.gestimmo.metier.entite.Utilisateur;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.UtilisateurService;

import lombok.extern.log4j.Log4j;

/**
 * @author mombaye
 *
 */
@Service("utilisateurService")
@Log4j
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	UtilisateurDao utilisateurDao;
	
	@Autowired
	Mapper mapper;
	
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		
		//TODO Ajouter les logs
		// ----------   Exemple d'utilisation du logger avec lombok   ---------- 
		//
		
		Utilisateur utilisateur = utilisateurDao.findByUsername(username);
		
		 if (utilisateur == null) {
	            throw new UsernameNotFoundException(username);
	        }
		 
		 UserBuilder userDetailBuilder = User.withUsername(username);
		 userDetailBuilder.password(utilisateur.getPassword());
		 userDetailBuilder.disabled(!utilisateur.isEnabled());
		 
		 /**
		  * Un user a forcément un rôle.
		  * Pas besoin de faire le test de nullité.
		  * En cas de pb il faut que ça pete.
		  */
		 List<String> roles = utilisateur.getRoles()
				 .stream().map(role -> role.getRole()).collect(Collectors.toList());
		 userDetailBuilder.roles(roles.toArray(new String[roles.size()]));
	       
		 return userDetailBuilder.build();
	}		
	
	
	@Transactional
	public UtilisateurDto saveOrUpdate(UtilisateurDto utilisateurDto) {
		Utilisateur utilisateur = mapper.utilisateurDtoToUtilisateur(utilisateurDto);
		utilisateurDao.saveOrUpdate(utilisateur);
		return mapper.utilisateurToUtilisateurDto(utilisateur);
	}


	@Override
	public UtilisateurDto findById(Long id) {
		Utilisateur user =  utilisateurDao.findById(Utilisateur.class, id);
		return mapper.utilisateurToUtilisateurDto(user);
	}


	@Override
	public List<UtilisateurDto> findAll() {
		List<Utilisateur> results = utilisateurDao.findAll(Utilisateur.class);
		return results.stream().map(utilisateur -> 
				mapper.utilisateurToUtilisateurDto(utilisateur))
				.collect(Collectors.<UtilisateurDto> toList());
	}


	@Override
	public boolean delete(UtilisateurDto entite) {
		Utilisateur user = mapper.utilisateurDtoToUtilisateur(entite);
		return utilisateurDao.delete(user);
	}
	

}
