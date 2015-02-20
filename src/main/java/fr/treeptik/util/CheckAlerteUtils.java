package fr.treeptik.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Administrateur;
import fr.treeptik.model.AlerteDescription;
import fr.treeptik.model.AlerteEmise;
import fr.treeptik.model.Client;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Etablissement;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.NiveauAlerte;
import fr.treeptik.service.AdministrateurService;
import fr.treeptik.service.AlerteDescriptionService;
import fr.treeptik.service.AlerteEmiseService;
import fr.treeptik.service.EtablissementService;
import fr.treeptik.service.MesureService;

@Component
public class CheckAlerteUtils {

	private Logger logger = Logger.getLogger(CheckAlerteUtils.class);

	@Inject
	private AlerteDescriptionService alerteDescriptionService;
	@Inject
	private AlerteEmiseService alerteEmiseService;
	@Inject
	private AdministrateurService administrateurService;
	@Inject
	private EtablissementService etablissementService;

	@Inject
	private EmailUtils emailUtils;
	@Inject
	private MesureService mesureService;

	public void checkAlerte(Enregistreur enregistreur, Mesure mesure)
			throws ServiceException {
		logger.info("-- checkAlerte CheckAlerteUtils-- Enregistreur mid: "
				+ enregistreur.getMid() + " mesure.getValeur() : "
				+ mesure.getValeur());

		List<AlerteDescription> alertesActives = alerteDescriptionService
				.findAlertesActivesByEnregistreurId(enregistreur.getId());

		for (AlerteDescription alerteActive : alertesActives) {
			switch (alerteActive.getTendance().getDescription()) {

			case "inférieur à":
				if (alerteActive.getSeuilPreAlerte() != null) {
					if (mesure.getValeur() < alerteActive.getSeuilPreAlerte()) {

						logger.info("-- checkAlerte : pré-alerte inférieur à levée");

						AlerteEmise alerteEmise = this
								.affectAlerteEmise(alerteActive);
						alerteEmise.setMesureLevantAlerte(mesure);
						alerteEmise.setEnregistreur(enregistreur);

						if (mesure.getValeur() < alerteActive.getSeuilAlerte()) {
							logger.info("-- checkAlerte : alerte inférieur à levée");
							alerteEmise.setNiveauAlerte(NiveauAlerte.ALERTE);
						} else {
							alerteEmise.setNiveauAlerte(NiveauAlerte.PREALERTE);
						}

						mesureService.findById(mesure.getId());
						alerteEmise = alerteEmiseService.create(alerteEmise);

						this.sendMailToAllDestinataire(alerteEmise);
					}
				} else {
					if (mesure.getValeur() < alerteActive.getSeuilAlerte()) {
						logger.info("-- checkAlerte : alerte inférieur à levée");

						AlerteEmise alerteEmise = this
								.affectAlerteEmise(alerteActive);
						alerteEmise.setMesureLevantAlerte(mesure);
						alerteEmise.setEnregistreur(enregistreur);

						alerteEmise.setNiveauAlerte(NiveauAlerte.ALERTE);
						mesureService.findById(mesure.getId());
						alerteEmise = alerteEmiseService.create(alerteEmise);

						this.sendMailToAllDestinataire(alerteEmise);

					}

				}
				break;
			case "supérieur à":
				if (alerteActive.getSeuilPreAlerte() != null) {
					if (mesure.getValeur() > alerteActive.getSeuilPreAlerte()) {
						logger.info("-- checkAlerte : pré-alerte supérieur à levée");

						AlerteEmise alerteEmise = this
								.affectAlerteEmise(alerteActive);
						alerteEmise.setMesureLevantAlerte(mesure);
						alerteEmise.setEnregistreur(enregistreur);

						if (mesure.getValeur() > alerteActive.getSeuilAlerte()) {
							logger.info("-- checkAlerte : alerte supérieur à levée");

							alerteEmise.setNiveauAlerte(NiveauAlerte.ALERTE);
						} else {
							alerteEmise.setNiveauAlerte(NiveauAlerte.PREALERTE);
						}

						mesureService.findById(mesure.getId());
						alerteEmise = alerteEmiseService.create(alerteEmise);

						this.sendMailToAllDestinataire(alerteEmise);
					}
				} else {
					if (mesure.getValeur() > alerteActive.getSeuilAlerte()) {
						logger.info("-- checkAlerte : alerte supérieur à levée");

						AlerteEmise alerteEmise = this
								.affectAlerteEmise(alerteActive);
						alerteEmise.setMesureLevantAlerte(mesure);
						alerteEmise.setEnregistreur(enregistreur);

						alerteEmise.setNiveauAlerte(NiveauAlerte.ALERTE);
						mesureService.findById(mesure.getId());
						alerteEmise = alerteEmiseService.create(alerteEmise);

						this.sendMailToAllDestinataire(alerteEmise);
					}
				}

				break;
			case "égal à":
				if (mesure.getValeur() == alerteActive.getSeuilAlerte()) {
					logger.info("-- checkAlerte : alerte égal à levée");

					AlerteEmise alerteEmise = this
							.affectAlerteEmise(alerteActive);
					alerteEmise.setMesureLevantAlerte(mesure);
					alerteEmise.setEnregistreur(enregistreur);
					mesureService.findById(mesure.getId());
					alerteEmise = alerteEmiseService.create(alerteEmise);

					this.sendMailToAllDestinataire(alerteEmise);

				}
				break;
			case "différent de":
				if (mesure.getValeur() != alerteActive.getSeuilAlerte()) {
					logger.info("-- checkAlerte : alerte différent de levée");

					AlerteEmise alerteEmise = this
							.affectAlerteEmise(alerteActive);
					alerteEmise.setMesureLevantAlerte(mesure);
					alerteEmise.setEnregistreur(enregistreur);
					mesureService.findById(mesure.getId());
					alerteEmise = alerteEmiseService.create(alerteEmise);

					this.sendMailToAllDestinataire(alerteEmise);

				}
				break;

			default:
				logger.error("ERROR --  checkAlerte checkAlerte -- la tendance de l'alerte n'a pas été trouvée");
				throw new ServiceException(
						"ERROR --  checkAlerte checkAlerte -- la tendance de l'alerte n'a pas été trouvée");
			}
		}

	}

	private AlerteEmise affectAlerteEmise(AlerteDescription alerteActive) {

		AlerteEmise alerteEmise = new AlerteEmise();
		alerteEmise.setAcquittement(false);
		alerteEmise.setCodeAlerte(alerteActive.getCodeAlerte());
		alerteEmise.setIntitule(alerteActive.getIntitule());
		alerteEmise.setSeuilAlerte(alerteActive.getSeuilAlerte());
		alerteEmise.setSeuilPreAlerte(alerteActive.getSeuilPreAlerte());
		alerteEmise.setTendance(alerteActive.getTendance());
		alerteEmise.setTypeAlerte(alerteActive.getTypeAlerte());
		alerteEmise.setDate(new Date());

		return alerteEmise;
	}

	private void sendMailToAllDestinataire(AlerteEmise alerteEmise)
			throws ServiceException {
		logger.info("-- checkAlerte checkAlerte-- alerteEmise : " + alerteEmise);
		try {

			Etablissement etablissement = alerteEmise.getEnregistreur()
					.getOuvrage().getSite().getEtablissement();
			etablissement = etablissementService
					.findByIdWithJoinFetchClients(etablissement.getId());

			List<String> destinataireEmails = new ArrayList<String>();
			etablissement.getClients().forEach(
					c -> destinataireEmails.add(c.getMail1()));
			List<Administrateur> administrateurs = administrateurService
					.findAll();
			administrateurs.forEach(a -> destinataireEmails.add(a.getMail1()));

			for (String mail : destinataireEmails) {
				emailUtils.sendAcquittementEmail(alerteEmise, mail);

			}
		} catch (MessagingException e) {
			logger.error(e.getMessage());
			throw new ServiceException(e.getMessage(), e);
		}
	}

}
