package com.ag2m.gestimmo.metier.mapper;

import java.util.Arrays;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ag2m.gestimmo.metier.config.ParamConfig;
import com.ag2m.gestimmo.metier.dto.AdresseDto;
import com.ag2m.gestimmo.metier.dto.AnomalieDto;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.BienDto;
import com.ag2m.gestimmo.metier.dto.ClientDto;
import com.ag2m.gestimmo.metier.dto.FactureDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.dto.RoleDto;
import com.ag2m.gestimmo.metier.dto.UtilisateurDto;
import com.ag2m.gestimmo.metier.entite.Adresse;
import com.ag2m.gestimmo.metier.entite.Anomalie;
import com.ag2m.gestimmo.metier.entite.Appartement;
import com.ag2m.gestimmo.metier.entite.Bien;
import com.ag2m.gestimmo.metier.entite.Client;
import com.ag2m.gestimmo.metier.entite.Facture;
import com.ag2m.gestimmo.metier.entite.Reservation;
import com.ag2m.gestimmo.metier.entite.Role;
import com.ag2m.gestimmo.metier.entite.Utilisateur;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {

	
	/**
	 * Map un Objet Utilisateur en UtilisateurDto
	 * Ignore le mapping de Utilisateur dans les objets Role afin d'éviter 
	 * un mapping cyclique.
	 * Ce mapping sera géré par processRoleForUtilisateurDto
	 * 
	 * @param utilisateur
	 * @return
	 */
	@Mapping(target = "roles", ignore = true)
	public abstract UtilisateurDto utilisateurToUtilisateurDto(Utilisateur utilisateur);
	
	/**
	 * Gère le mapping de l'objet Utilisateur contenu dans Role.
	 * Elle sera appelée par utilisateurToUtilisateurDto
	 * 
	 * @param utilisateurDto
	 */
	 @AfterMapping
	default void processRoleForUtilisateurDto(@MappingTarget UtilisateurDto utilisateurDto) {
		 	if(utilisateurDto.getRoles() != null) {
		 		
		 		utilisateurDto.getRoles().forEach(role -> role.setUtilisateur(utilisateurDto));
		 		
		 	}
	  }
	
 	/**
	 * Map un Objet UtilisateurDto en Utilisateur
	 * Ignore le mapping de UtilisateurDto dans les objets RoleDto afin d'éviter 
	 * un mapping cyclique.
	 * Ce mapping sera géré par processRoleForUtilisateur
	 * 
	 * @param utilisateur
	 * @return
	 */
	@Mapping(target = "roles", ignore = true)
	Utilisateur utilisateurDtoToUtilisateur(UtilisateurDto utilisateur);
	
	/**
	 * Gère le mapping de l'objet Utilisateur contenu dans Role.
	 * Elle sera appelée par utilisateurToUtilisateurDto
	 * 
	 * @param utilisateur
	 */
	 @AfterMapping
		default void processRoleForUtilisateur(@MappingTarget Utilisateur utilisateur) {
			 if(utilisateur.getRoles() != null) {
				 utilisateur.getRoles().forEach(app -> app.setUtilisateur(utilisateur));
			 }
			        
		 }
	 
	 
	 /**
		 *  Map un Objet Role en RoleDto
		 *  
		 * @param role
		 * @return
		 */
		RoleDto roleToRoleDto(Role role);
		
		/**
		 *  Map un Objet RoleDto en Role
		 *  
		 * @param roleDto
		 * @return
		 */
		Role roleDtoToRole(RoleDto roleDto);
	 
		
		/**
		 * Map un Objet Client en ClientDto
		 * Ignore le mapping de Client dans les objets Reservations afin d'éviter 
		 * un mapping cyclique.
		 * Ce mapping sera géré par processReservationForClientDto
		 * 
		 * @param client
		 * @return
		 */
		@Mapping(target = "reservations", ignore = true)
		@Mapping(target = "factures", ignore = true)
		public abstract ClientDto clientToClientDto(Client client);
		
		/**
		 * Gère le mapping de l'objet Client contenu dans Reservation.
		 * Elle sera appelée par clientToClientDto
		 * 
		 * @param clientDto
		 */
		 @AfterMapping
		default void processReservationForClientDto(@MappingTarget ClientDto clientDto) {
			 
			 //Mapping des réservations
			 if(clientDto.getReservations() != null) {
			 		
			 		clientDto.getReservations().forEach(resa -> resa.setClient(clientDto));
			 	}
			 
			//Mapping des factures
			 if(clientDto.getFactures() != null) {
			 		
			 		clientDto.getFactures().forEach(facture -> facture.setClient(clientDto));
			 	}
		  }
		
	 	/**
		 * Map un Objet ClientDto en Client
		 * Ignore le mapping de ClientDto dans les objets ReservationDto afin d'éviter 
		 * un mapping cyclique.
		 * Ce mapping sera géré par processReservationForClient
		 * 
		 * @param utilisateur
		 * @return
		 */
		@Mapping(target = "reservations", ignore = true)
		@Mapping(target = "factures", ignore = true)
		Client clientDtoToClient(ClientDto client);
		
		/**
		 * Gère le mapping de l'objet Client contenu dans Reservation.
		 * Elle sera appelée par clientToClientDto
		 * 
		 * @param utilisateur
		 */
		 @AfterMapping
			default void processReservationForClient(@MappingTarget Client client) {
			 //Mapping des réservations
				 if(client.getReservations() != null) {
					 client.getReservations().forEach(resa -> resa.setClient(client));
				 }
				 
			 //Mapping des factures
			 if(client.getFactures() != null) {
				 client.getFactures().forEach(facture -> facture.setClient(client));
			 }
				        
			 }
		 
			/**
			 * Map un Objet Adresse en AdresseDto
			 * Ignore le mapping de Adresse dans les objets Client, Facture et Bien afin d'éviter 
			 * un mapping cyclique.
			 * Ce mapping sera géré par processClientAndBienForAdressetDto
			 * 
			 * @param adresse
			 * @return
			 */
			@Mapping(target = "clients", ignore = true)
			@Mapping(target = "biens", ignore = true)
			@Mapping(target = "factures", ignore = true)
			public abstract AdresseDto adresseToAdresseDto(Adresse adresse);
			
			/**
			 * Gère le mapping de l'objet Adresse contenu dans Client Facture et Bien.
			 * Elle sera appelée par adresseToAdresseDto
			 * 
			 * @param adresseDto
			 */
			 @AfterMapping
			default void processClientAndBienForAdressetDto(@MappingTarget AdresseDto adresseDto) {
				 //Mapper adresse dans chaque client	
				 if(adresseDto.getClients()!= null) {
				 		
				 		adresseDto.getClients().forEach(adresse -> adresse.setAdresse(adresseDto));
				 	}
				 
				 //Mapper adresse dans chaque Bien
				 if(adresseDto.getBiens()!= null) {
				 		
				 		adresseDto.getBiens().forEach(bien -> bien.setAdresse(adresseDto));
				 	}
				 
				//Mapper adresse dans chaque Facture
				 if(adresseDto.getFactures()!= null) {
				 		
				 		adresseDto.getFactures().forEach(facture -> facture.setAdresseFacturation(adresseDto));
				 	}
			  }
			
		 	/**
			 * Map un Objet AdresseDto en Adresse
			 * Ignore le mapping de AdresseDto dans les objets ClientDto Fature et BienDto afin d'éviter 
			 * un mapping cyclique.
			 * Ce mapping sera géré par processClientAndBienForAdresse
			 * 
			 * @param adresse
			 * @return
			 */
			 @Mapping(target = "clients", ignore = true)
			 @Mapping(target = "biens", ignore = true)
			 @Mapping(target = "factures", ignore = true)
			 Adresse adresseDtoToAdresse(AdresseDto adresse);
			
			/**
			 * Gère le mapping de l'objet Adresse contenu dans Client Facture et Bien.
			 * Elle sera appelée par adresseToAdresseDto
			 * 
			 * @param adresse
			 */
			 @AfterMapping
				default void processClientAndBienForAdresse(@MappingTarget Adresse adresse) {
				
				 //Mapper adresse dans chaque client	
					 if(adresse.getClients() != null) {
						 adresse.getClients().forEach(adr -> adr.setAdresse(adresse));
					 }
					 
				//Mapper adresse dans chaque Bien
				 if(adresse.getBiens()!= null) {
				 		
				 		adresse.getBiens().forEach(bien -> bien.setAdresse(adresse));
				 	}
				 
				//Mapper adresse dans chaque Facture
				 if(adresse.getFactures()!= null) {
				 		
				 		adresse.getFactures().forEach(facture -> facture.setAdresseFacturation(adresse));
				 	}
				 }
	
			 
			/**
			 * Map un Objet Facture en FactureDto
			 * Ignore le mapping de Facture dans les objets Reservation afin d'éviter 
			 * un mapping cyclique.
			 * Ce mapping sera géré par processReservationForFactureDto
			 * 
			 * @param facture
			 * @return
			 */
			@Mapping(target = "reservations", ignore = true)
			public abstract FactureDto factureToFactureDto(Facture facture);
			
			/**
			 * Gère le mapping de l'objet Facture contenu dans Reservation.
			 * Elle sera appelée par factureToFactureDto
			 * 
			 * @param factureDto
			 */
			 @AfterMapping
			default void processReservationForFactureDto(@MappingTarget FactureDto factureDto) {
				 
				 // Init tva et taxe de séjour
				 factureDto.setTaxeSejour(ParamConfig.TAXE_SEJOUR);
				 factureDto.setTva(ParamConfig.TVA);
				 
				 	if(factureDto.getReservations() != null) {
				 		
				 		factureDto.getReservations().forEach(resa -> resa.setFacture(factureDto));
				 		
				 	}
			  }
			
		 	/**
			 * Map un Objet FactureDto en Facture
			 * Ignore le mapping de FactureDto dans les objets ReservationDto afin d'éviter 
			 * un mapping cyclique.
			 * Ce mapping sera géré par processReservationForFacture
			 * 
			 * @param facture
			 * @return
			 */
			@Mapping(target = "reservations", ignore = true)
			Facture factureDtoToFacture(FactureDto facture);
			
			/**
			 * Gère le mapping de l'objet Facture contenu dans Reservation.
			 * Elle sera appelée par factureToFactureDto
			 * 
			 * @param facture
			 */
			 @AfterMapping
				default void processReservationForFacture(@MappingTarget Facture facture) {
				 
					// Init tva et taxe de séjour
					 facture.setTaxeSejour(ParamConfig.TAXE_SEJOUR);
					 facture.setTva(ParamConfig.TVA);
					 
					 if(facture.getReservations() != null) {
						 facture.getReservations().forEach(resa -> resa.setFacture(facture));
					 }
					        
				 }
			 
	/**
	 * Map un Objet Bien en BienDto
	 * Ignore le mapping de Bien dans les objets Appartement afin d'éviter 
	 * un mapping cyclique.
	 * Ce mapping sera géré par processAppartementForBienDto
	 * 
	 * @param bien
	 * @return
	 */
	@Mapping(target = "appartements", ignore = true)
	public abstract BienDto bienToBienDto(Bien bien);
	
	/**
	 * Gère le mapping de l'objet Bien contenu dans Appartement.
	 * Elle sera appelée par bienToBienDto
	 * 
	 * @param bienDto
	 */
	 @AfterMapping
	default void processAppartementForBienDto(@MappingTarget BienDto bienDto) {
		 	if(bienDto.getAppartements() != null) {
		 		
		 		bienDto.getAppartements().forEach(app -> app.setBien(bienDto));
		 	}
	  }
	
 	/**
	 * Map un Objet BienDto en Bien
	 * Ignore le mapping de BienDto dans les objets AppartementDto afin d'éviter 
	 * un mapping cyclique.
	 * Ce mapping sera géré par processAppartementForBien
	 * 
	 * @param bien
	 * @return
	 */
	@Mapping(target = "appartements", ignore = true)
	Bien bienDtoToBien(BienDto bien);
	
	/**
	 * Gère le mapping de l'objet Bien contenu dans Appartement.
	 * Elle sera appelée par bienToBienDto
	 * 
	 * @param bien
	 */
	 @AfterMapping
		default void processAppartementForBien(@MappingTarget Bien bien) {
			 if(bien.getAppartements() != null) {
				 bien.getAppartements().forEach(app -> app.setBien(bien));
			 }
			        
		 }
	
	 	/**
		 * Map un Objet Appartement en AppartementDto
		 * Ignore le mapping de l'objet Appartement dans les objets Anomalie afin d'éviter 
		 * un mapping cyclique.
		 * Ce mapping sera géré par processAnomalieForAppartDto
		 * 
		 * @param appartement
		 * @return
		 */
	@Mapping(target = "anomalies", ignore = true)
	AppartementDto appartementToAppartementDto(Appartement appartement);
	
	 	/**
		 * Gère le mapping de l'objet Appartement contenu dans Anomalie.
		 * Elle sera appelée par appartementToAppartementDto
		 * 
		 * @param appartementDto
		 */
	 @AfterMapping
	default void processAnomalieForAppartDto(@MappingTarget AppartementDto appartementDto) {
		 	if(appartementDto.getAnomalies()!= null) {
		 		appartementDto.getAnomalies().forEach(ano -> ano.setAppartement(appartementDto));
		 	}
		 	
	  }

	 
	 	/**
	 	 * Map un Objet AppartementDto en Appartement
		 * Ignore le mapping de l'objet AppartementDto dans les objets AnomalieDto afin d'éviter 
		 * un mapping cyclique.
		 * Ce mapping sera géré par processAnomalieForAppart
	 	 * 
	 	 * @param appartementDto
	 	 * @return
	 	 */
	@Mapping(target = "anomalies", ignore = true)
	Appartement appartementDtoToAppartement(AppartementDto appartementDto);
	
	 /**
		 * Gère le mapping de l'objet AppartementDto contenu dans AnomalieDto.
		 * Elle sera appelée par appartementDtoToAppartement
		 * 
		 * @param appartementDto
		 */
	 @AfterMapping
		default void processAnomalieForAppart(@MappingTarget Appartement appartement) {
			if(appartement.getAnomalies()!= null) {
				appartement.getAnomalies().forEach(ano -> ano.setAppartement(appartement));
		 	}
			
		 }

	 
	/**
	 *  Map un Objet Anomalie en AnomalieDto
	 *  
	 * @param anomalie
	 * @return
	 */
	AnomalieDto anomalieToAnomalieDto(Anomalie anomalie);
	
	/**
	 *  Map un Objet AnomalieDto en Anomalie
	 *  
	 * @param anomalieDto
	 * @return
	 */
	Anomalie anomalieDtoToAnomalie(AnomalieDto anomalieDto);
	
	/**
	 *  Map un Objet Reservation en ReservationDto
	 *  
	 * @param reservation
	 * @return
	 */
	@Mapping(target = "appartements", ignore = true)
	ReservationDto reservationToReservationDto(Reservation reservation);
	
	@AfterMapping
	default void mapAppartIntoResaDto(@MappingTarget AppartementDto appartementDto) {
		
		if(appartementDto.getReservations() != null) {
			
			appartementDto.getReservations().forEach(resa -> {
				
				if(resa.getAppartements() != null) {
					resa.getAppartements().add(appartementDto);
				}else {
					resa.setAppartements(Arrays.asList(appartementDto));
				}
			});
		}
			
 	}
	
	
	/**
	 *  Map un Objet ReservationDto en Reservation
	 *  
	 * @param anomalieDto
	 * @return
	 */
	@Mapping(target = "appartements", ignore = true)
	Reservation reservationDtoToReservation(ReservationDto reservationDto);
	
	
	@AfterMapping
	default void mapAppartIntoResa(@MappingTarget Appartement appartement) {
		
		if(appartement.getReservations() != null) {
			
			appartement.getReservations().forEach(resa -> {
				
				if(resa.getAppartements() != null) {
					resa.getAppartements().add(appartement);
				}else {
					resa.setAppartements(Arrays.asList(appartement));
				}
			});
		}
			
 	}
}
