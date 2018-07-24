package com.ag2m.gestimmo.metier.constants;

public class FunctionalErrorMessageConstants {

	/************* Réservation *************/
	
	/** Constante ERREUR_RESERVATION_STATUT_INCORRECT */
	public static final String ERREUR_RESERVATION_STATUT_INCORRECT =
			"Impossible de créer une réservation avec un statut annulé ou facturé.";
	
	/** Constante ERREUR_ANNULATION_RESERVATION_STATUT_INCORRECT */
	public static final String ERREUR_ANNULATION_RESERVATION_STATUT_INCORRECT =
			"Le statut de la réservation ne permet pas une annulation";
	
	/** Constante ERREUR_RESERVATION_DATE_CHECKIN_INCORRECT */
	public static final String ERREUR_RESERVATION_DATE_CHECKIN_INCORRECT =
			"La date de checkin ne peut pas être dans le passé";
	
	/** Constante ERREUR_RESERVATION_DATE_CHECKOUT_INCORRECT */
	public static final String ERREUR_RESERVATION_DATE_CHECKOUT_INCORRECT =
			"La date de checkout ne peut pas être antérieure à la date de checkin";
	
	/** Constante ERREUR_RESERVATION_REMISE_NON_JUSTIFIEE */
	public static final String ERREUR_RESERVATION_REMISE_NON_JUSTIFIEE =
			"Le champs note doit être renseigné afin de justifier la diminution du tarif de l'appartement.";
	
	/** Constante ERREUR_RESERVATION_REMISE_NON_JUSTIFIEE */
	public static final String ERREUR_RESERVATION_ANNULATION_NON_JUSTIFIEE =
			"Le champs note doit être renseigné afin de justifier l'annulation de la réservation.";
	
	/** Constante ERREUR_RESERVATION_REMISE_NON_JUSTIFIEE */
	public static final String ERREUR_RESERVATION_PERIODE_SUP_30_JOURS =
			"Le période saisie ne doit pas couvrir plus de 30 jours";
	
	/** Constante ERREUR_RESERVATION_DATE_CHECKIN_NULL */
	public static final String ERREUR_RESERVATION_DATE_CHECKIN_NULL =
			"La date de checkin ne peut pas être nulle";
	
	/** Constante ERREUR_RESERVATION_DATE_CHECKOUT_NULL */
	public static final String ERREUR_RESERVATION_DATE_CHECKOUT_NULL =
			"La date de checkout ne peut pas être nulle";
	
	/** Constante ERREUR_RESERVATION_DATE_CHECKOUT_NULL */
	public static final String ERREUR_RESERVATION_PERIODE_INCORRECTE =
			"Les dates de checkin et checkout sont obligatoires";
	
	/** Constante ERREUR_RESERVATION_DATE_CHECKOUT_NULL */
	public static final String ERREUR_RESERVATION_DATE_ANNULATION_NON_NULL =
			"La date d'annulation ne doit pas être renseignée lors de la reservation.";
	
	
	/** Constante ERREUR_RESERVATION_STATUT_NULL */
	public static final String ERREUR_RESERVATION_STATUT_NULL =
			"Le statut de réservation ne peut pas être nul";
	
	/** Constante ERREUR_RESERVATION_STATUT_NULL */
	public static final String ERREUR_RESERVATION_PRIX_NULL =
			"Le prix de la réservation ne peut pas être nul";
	
	/** Constante ERREUR_RESERVATION_BIEN_NULL */
	public static final String ERREUR_RESERVATION_BIEN_NULL =
			"L'id du bien ne peut pas être nul";
	
	/** Constante ERREUR_RESERVATION_BIEN_NULL */
	public static final String ERREUR_PARAMETRAGE_DATE_NULL =
			"Date nulle passée pour le chargement de la taxe.";
	
}
