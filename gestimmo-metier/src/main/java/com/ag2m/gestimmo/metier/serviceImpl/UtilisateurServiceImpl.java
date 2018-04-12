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
import com.ag2m.gestimmo.metier.entite.Utilisateur;
import com.ag2m.gestimmo.metier.service.UtilisateurService;

/**
 * @author mombaye
 *
 */
@Service("utilisateurService")
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	UtilisateurDao utilisateurDao;
	
//	@Autowired
//	Mapper mapper;
	
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		Utilisateur utilisateur = utilisateurDao.findByUsername(username);
		
		 if (utilisateur == null) {
	            throw new UsernameNotFoundException(username);
	        }
		 
		 UserBuilder userDetailBuilder = User.withUsername(username);
		 userDetailBuilder.password(utilisateur.getPassword());
		 userDetailBuilder.disabled(!utilisateur.isEnabled());
		 List<String> roles = utilisateur.getRoles().stream().map(role -> role.getRole()).collect(Collectors.toList());
		 userDetailBuilder.roles((String[]) roles.toArray());
	       
		 return userDetailBuilder.build();
	}		
}
