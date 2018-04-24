/**
 * 
 */
package com.ag2m.gestimmo.metier.serviceImpl;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import com.ag2m.gestimmo.metier.dto.UtilisateurDto;

import static org.hamcrest.Matchers.*;

/**
 * Class de test des services de UtilisateurService.
 * @author mombaye
 *
 */
public class UtilisateurServiceImplTest extends AbstractCommonTest{

	

	@Test
	public void testSaveOrUpdate() {
		
		//Création des utilisateurs
		UtilisateurDto utilisateurAdmin = createUtilisateur("asouane", "asouane", true);
		UtilisateurDto utilisateurReceptionniste = createUtilisateur("mombaye", "mombaye", true);
		UtilisateurDto utilisateurCuisinier = createUtilisateur("ammaiga", "ammaiga", true);
		
		
		//Création des Rôles
		createRole("ADMIN", utilisateurAdmin);
		createRole("RECEPTIONNISTE", utilisateurAdmin);
		createRole("RECEPTIONNISTE", utilisateurReceptionniste);
		createRole("CUISINIER", utilisateurCuisinier);
		
		//charger les users via username
		 UserDetails admin = utilisateurService.loadUserByUsername("asouane");
		 UserDetails receptionniste = utilisateurService.loadUserByUsername("mombaye");
		 UserDetails cuisinier = utilisateurService.loadUserByUsername("ammaiga");
		 
		 //vérifications
		 List<String> result = Arrays.asList("ROLE_RECEPTIONNISTE", "ROLE_ADMIN");
		 
		 assertThat(admin.getUsername(), equalTo("asouane"));
		 admin.getAuthorities().forEach(role -> {
			   assertTrue(result.contains(role.getAuthority())) ;
		 });
		 
		 assertThat(receptionniste.getUsername(), equalTo("mombaye"));
		 receptionniste.getAuthorities().forEach(role -> {
			 Assert.assertThat(role.getAuthority(), equalTo("ROLE_RECEPTIONNISTE"));
		 });
		 
		 assertThat(cuisinier.getUsername(), equalTo("ammaiga"));
		 cuisinier.getAuthorities().forEach(role -> {
			 Assert.assertThat(role.getAuthority(), equalTo("ROLE_CUISINIER"));
		 });
	}

	
	
}
