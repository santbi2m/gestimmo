package com.ag2m.gestimmo.metier.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ag2m.gestimmo.metier.constants.FunctionnalErrorMessageConstants;
import com.ag2m.gestimmo.metier.constants.TechnicalErrorMessageConstants;
import com.ag2m.gestimmo.metier.dao.FactureDao;
import com.ag2m.gestimmo.metier.dao.ReservationDao;
import com.ag2m.gestimmo.metier.dto.AppartementDto;
import com.ag2m.gestimmo.metier.dto.FactureDto;
import com.ag2m.gestimmo.metier.dto.ReservationDto;
import com.ag2m.gestimmo.metier.entite.Facture;
import com.ag2m.gestimmo.metier.entite.Reservation;
import com.ag2m.gestimmo.metier.enumeration.EnumStatutReservation;
import com.ag2m.gestimmo.metier.exception.FunctionalException;
import com.ag2m.gestimmo.metier.exception.TechnicalException;
import com.ag2m.gestimmo.metier.ioparam.FactureCriteria;
import com.ag2m.gestimmo.metier.ioparam.ReservationCriteria;
import com.ag2m.gestimmo.metier.mapper.Mapper;
import com.ag2m.gestimmo.metier.service.FactureService;
import com.ag2m.gestimmo.metier.utils.CustomDateUtil;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ag2m.gestimmo.metier.utils.NumeroFactureUtil;

import lombok.extern.log4j.Log4j;

@Service("factureService")
@Log4j
public class FactureServiceImpl implements FactureService {

	@Autowired
	FactureDao factureDao;

	@Autowired
	private ReservationDao reservationDao;

	@Autowired
	Mapper mapper;

	FactureDto fDto = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ag2m.gestimmo.metier.service.FactureService#findFactureById(java.lang
	 * .Long)
	 */
	@Transactional(readOnly = true)
	public FactureDto findFactureById(Long id) throws TechnicalException {

		Facture facture = null;
		FactureDto factureDto = null;

		log.info("Methode de recherche de facture par id " + id);
		Optional.ofNullable(id)
				.orElseThrow(() -> new FunctionalException(TechnicalErrorMessageConstants.ERREUR_ID_NULL));

		facture = factureDao.findById(Facture.class, id);

		if (facture != null) {
			factureDto = mapper.factureToFactureDto(facture);
		}

		return factureDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ag2m.gestimmo.metier.service.FactureService#loadAllFactures()
	 */
	@Transactional(readOnly = true)
	public List<FactureDto> loadAllFactures() {
		log.debug("Chargement de toutes les factures");

		List<FactureDto> listeFactureDto = null;

		List<Facture> factures = factureDao.findAll(Facture.class);
		listeFactureDto = factures.stream().map(facture -> mapper.factureToFactureDto(facture))
				.collect(Collectors.<FactureDto>toList());

		return listeFactureDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ag2m.gestimmo.metier.service.FactureService#createFacture(com.ag2m.
	 * gestimmo.metier.dto.FactureDto)
	 */
	@Transactional
	public FactureDto createFacture(FactureDto entiteDto) throws FunctionalException, TechnicalException {
		log.debug("Creation facture");

		// statut autorisé pour la réservation
		List<String> statutAutorisee = Arrays.asList(EnumStatutReservation.ENREGISTREE.getStatut(),
				EnumStatutReservation.CONFIRMEE.getStatut(), EnumStatutReservation.EN_ATTENTE.getStatut(),
				EnumStatutReservation.ANNULEE.getStatut());

		List<String> statutCOnfirmeEnregistre = Arrays.asList(EnumStatutReservation.ENREGISTREE.getStatut(),
				EnumStatutReservation.CONFIRMEE.getStatut());

		// Vérification de la validité de la réservation
		validateFacture(entiteDto, statutAutorisee);

		// La facture à créer ne peut pas être null
		Optional.ofNullable(entiteDto)
				.orElseThrow(() -> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ID_NULL));

		/*String numeroFacture = factureDao.findLastNumFacture();
		String nextNumFacture = NumeroFactureUtil.generateNextFactureNumberByActual(numeroFacture, NumeroFactureUtil.SUFFIXE_FT);
		entite.setNumeroFacture(nextNumFacture);*/
		// map and save
		fDto = mapAndSave(entiteDto, fDto);

		Optional.ofNullable(fDto)
				.orElseThrow(() -> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ID_NULL));

		entiteDto.getReservations().forEach(reservationDto -> {

			boolean dayUse = false;
			try {
				dayUse = CustomDateUtil.isSameDay(reservationDto.getDateCheckin(), reservationDto.getDateCheckout());
			} catch (TechnicalException e) {
				new TechnicalException(TechnicalErrorMessageConstants.ERREUR_DATES_RESERV);
			}

			if (statutCOnfirmeEnregistre.stream().anyMatch(s -> reservationDto.getStatut().contains(s))) {
				// Mise à jour du statut de la réservation à facture
				reservationDto.setStatut(EnumStatutReservation.FACTUREE.getStatut());
				// Vérifier si la réservation est un day-use c'est à dire moins
				// d'une nuité.
				if (dayUse) {
					// Mise à jour date-chechout à 1h de plus
					reservationDto.setDateCheckout(reservationDto.getDateCheckout().plusHours(1));
				}
			} else if (reservationDto.getStatut().equals(EnumStatutReservation.EN_ATTENTE.getStatut())) {
				// Mise à jour du statut de la réservation à facturé
				reservationDto.setStatut(EnumStatutReservation.EN_ATTENTE_FACTUREE.getStatut());

				// Vérifier si la réservation est un day-use c'est à dire moins
				// d'une nuité.
				if (dayUse) {
					// Mise à jour date-chechout à 1h de plus
					reservationDto.setDateCheckout(reservationDto.getDateCheckout().plusHours(1));
				}

			} else {
				// Mise à jour du statut de la réservation à facturé
				reservationDto.setStatut(EnumStatutReservation.ANNULEE_FACTUREE.getStatut());
			}
			// Mise a jour de la facture dans réservation
			reservationDto.setFacture(fDto);
			// Transformation en entité Reservation
			Reservation entite = mapper.reservationDtoToReservation(reservationDto);

			// Sauvegarde de la réservation
			reservationDao.saveOrUpdate(entite);
		});
		return fDto;
	}

	private void validateFacture(FactureDto factureDto, List<String> statutAutorisee) throws TechnicalException {

		// Facture ne doit pas être nulle
		Optional.ofNullable(factureDto)
				.orElseThrow(() -> new TechnicalException(TechnicalErrorMessageConstants.ERREUR_ENTREE_CREATION_NULL));

		// statut de résa différent de « Enregistrée » , « Confirmée » , « En
		// attente » , « Annulée »

		if (factureDto.getReservations() != null && !factureDto.getReservations().isEmpty()) {
			factureDto.getReservations().forEach(reservationDto -> {
				if (!statutAutorisee.contains(reservationDto.getStatut())) {
					throw new FunctionalException(FunctionnalErrorMessageConstants.ERREUR_RESERVATION_STATUT_INCORRECT);
				}
			});
		}

	}

	private FactureDto mapAndSave(FactureDto factureDto, FactureDto fDto) throws TechnicalException {
		// Transformation en entité facture
		Facture facture = mapper.factureDtoToFacture(factureDto);
		// Appel du service
		factureDao.saveOrUpdate(facture);
		fDto = mapper.factureToFactureDto(facture);

		return fDto;
	}

	/**
	 * Methode de recherche par criteria
	 *
	 *
	 */
	@Override
	@Transactional
	public List<FactureDto> findFactureByCriteria(final FactureCriteria factureCriteria) {
		log.debug("Recherche Par critere");

		// Chargement des factures en fonction des critères d'entrée.
		List<Facture> factures = factureDao.findFactureByCriteria(factureCriteria);
		// Transformation de tous les biens en FactureDto
		return factures.stream().map(facture -> mapper.factureToFactureDto(facture))
				.collect(Collectors.<FactureDto>toList());
	}

	@Override
	public ByteArrayOutputStream genererFacture(final FactureDto factureDto)
			throws FunctionalException, TechnicalException {

		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(2);
			PdfPTable table2 = new PdfPTable(2);
			table2.setWidthPercentage(100);
			table.setWidthPercentage(100);
			// table.setWidths(new int[] { 1, 3 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcellC;
			hcellC = new PdfPCell(new Phrase("Client", headFont));
			hcellC.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcellC.setBorder(Rectangle.NO_BORDER);
			table2.addCell(hcellC);

			hcellC = new PdfPCell(new Phrase("Adresse Facturation", headFont));
			hcellC.setHorizontalAlignment(Element.ALIGN_RIGHT);
			hcellC.setBorder(Rectangle.NO_BORDER);
			table2.addCell(hcellC);

			PdfPCell cellC;
			cellC = new PdfPCell(new Phrase(factureDto.getClient().getNom() + "\n" + factureDto.getClient().getPrenom()
					+ "\n" + factureDto.getClient().getTelephone() + "\n" + factureDto.getClient().getAdresseEmail()));
			cellC.setVerticalAlignment(Element.ALIGN_LEFT);
			cellC.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellC.setBorder(Rectangle.NO_BORDER);
			table2.addCell(cellC);

			cellC = new PdfPCell(new Phrase(factureDto.getAdresseFacturation().getAdresse() + "\n"
					+ factureDto.getAdresseFacturation().getCodePostal() + "\n"
					+ factureDto.getAdresseFacturation().getVille() + "/"
					+ factureDto.getAdresseFacturation().getPays()));
			cellC.setPaddingLeft(5);
			cellC.setVerticalAlignment(Element.ALIGN_RIGHT);
			cellC.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellC.setBorder(Rectangle.NO_BORDER);
			table2.addCell(cellC);

			if (factureDto.getReservations() != null && !factureDto.getReservations().isEmpty()) {
				for (ReservationDto reservationDto : factureDto.getReservations()) {
					String libelleAppart = null;
					StringBuilder builder = new StringBuilder();
					if (reservationDto.getAppartements() != null) {
						for (AppartementDto appartementDto : reservationDto.getAppartements()) {
							builder.append(appartementDto.getLibelle()).append("\n");
						}
						libelleAppart = builder.toString();
					}
					PdfPCell hcell;
					hcell = new PdfPCell(new Phrase("Libellé :", headFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(hcell);

					hcell = new PdfPCell(new Phrase("Prix", headFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(hcell);

					PdfPCell cell;
					cell = new PdfPCell(new Phrase(libelleAppart));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(reservationDto.getPrix().toString()));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

				}
			}

			PdfWriter.getInstance(document, out);
			document.open();
			document.addTitle("FACTURE");

			Paragraph paragraph = new Paragraph("Facture N° 1",
					FontFactory.getFont(FontFactory.COURIER, 15f, Font.BOLD));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
			document.add(Chunk.NEWLINE);
			document.add(table2);

			document.add(Chunk.NEWLINE);

			document.add(table);

			document.close();

		} catch (DocumentException ex) {

			Logger.getLogger(FactureServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
		}

		return out;

	}

}
