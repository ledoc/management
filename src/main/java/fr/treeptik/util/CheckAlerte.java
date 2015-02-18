package fr.treeptik.util;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.AlerteDescription;
import fr.treeptik.model.AlerteEmise;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.NiveauAlerte;
import fr.treeptik.service.AlerteDescriptionService;
import fr.treeptik.service.AlerteEmiseService;
import fr.treeptik.service.MesureService;

@Component
public class CheckAlerte {

	private Logger logger = Logger.getLogger(CheckAlerte.class);

	@Inject
	private AlerteDescriptionService alerteDescriptionService;
	@Inject
	private AlerteEmiseService alerteEmiseService;
	@Inject
	private EmailUtils emailUtils;
	@Inject
	private MesureService mesureService;

	public void checkAlerte(Enregistreur enregistreur, Mesure mesure)
			throws ServiceException {
		logger.info("-- checkAlerte checkAlerte-- Enregistreur mid: "
				+ enregistreur.getMid() + " mesure.getValeur() : "
				+ mesure.getValeur());

		List<AlerteDescription> alertesActives = alerteDescriptionService
				.findAlertesActivesByEnregistreurId(enregistreur.getId());

		for (AlerteDescription alerteActive : alertesActives) {
			switch (alerteActive.getTendance().getDescription()) {

			case "inférieur à":
				if (mesure.getValeur() < alerteActive.getSeuilPreAlerte()) {

					AlerteEmise alerteEmise = this
							.affectAlerteEmise(alerteActive);
					alerteEmise.setMesureLevantAlerte(mesure);
					alerteEmise.setEnregistreur(enregistreur);

					if (mesure.getValeur() < alerteActive.getSeuilAlerte()) {
						alerteEmise.setNiveauAlerte(NiveauAlerte.ALERTE);
					} else {
						alerteEmise.setNiveauAlerte(NiveauAlerte.PREALERTE);
					}
					
					mesureService.findById(mesure.getId());
					alerteEmise = alerteEmiseService.create(alerteEmise);

					try {
						emailUtils.sendAcquittementEmail(alerteEmise);
					} catch (MessagingException e) {
						logger.error(e.getMessage());
						throw new ServiceException(e.getMessage(), e);
					}
				}
				break;
			case "supérieur à":
				if (mesure.getValeur() > alerteActive.getSeuilPreAlerte()) {

					AlerteEmise alerteEmise = this
							.affectAlerteEmise(alerteActive);
					alerteEmise.setMesureLevantAlerte(mesure);
					alerteEmise.setEnregistreur(enregistreur);

					if (mesure.getValeur() > alerteActive.getSeuilAlerte()) {
						alerteEmise.setNiveauAlerte(NiveauAlerte.ALERTE);
					} else {
						alerteEmise.setNiveauAlerte(NiveauAlerte.PREALERTE);
					}
					
					mesureService.findById(mesure.getId());
					alerteEmise = alerteEmiseService.create(alerteEmise);
					
					try {
						emailUtils.sendAcquittementEmail(alerteEmise);
					} catch (MessagingException e) {
						logger.error(e.getMessage());
						throw new ServiceException(e.getMessage(), e);
					}

				}
				break;
			case "égal à":
				if (mesure.getValeur() == alerteActive.getSeuilAlerte()) {

					AlerteEmise alerteEmise = this
							.affectAlerteEmise(alerteActive);
					alerteEmise.setMesureLevantAlerte(mesure);
					alerteEmise.setEnregistreur(enregistreur);
					mesureService.findById(mesure.getId());
					alerteEmise = alerteEmiseService.create(alerteEmise);
					try {
						emailUtils.sendAcquittementEmail(alerteEmise);
					} catch (MessagingException e) {
						logger.error(e.getMessage());
						throw new ServiceException(e.getMessage(), e);
					}

				}
				break;
			case "différent de":
				if (mesure.getValeur() != alerteActive.getSeuilAlerte()) {

					AlerteEmise alerteEmise = this
							.affectAlerteEmise(alerteActive);
					alerteEmise.setMesureLevantAlerte(mesure);
					alerteEmise.setEnregistreur(enregistreur);
					mesureService.findById(mesure.getId());
					alerteEmise = alerteEmiseService.create(alerteEmise);
					try {
						emailUtils.sendAcquittementEmail(alerteEmise);
					} catch (MessagingException e) {
						logger.error(e.getMessage());
						throw new ServiceException(e.getMessage(), e);
					}

				}
				break;

			default:
				break;
			}
		}

	}

	private AlerteEmise affectAlerteEmise(AlerteDescription alerteActive) {

		AlerteEmise alerteEmise = new AlerteEmise();
		alerteEmise.setAcquittement(false);
		alerteEmise.setCodeAlerte(alerteActive.getCodeAlerte());
		alerteEmise.setEmailDEnvoi(alerteActive.getEmailDEnvoi());
		alerteEmise.setIntitule(alerteActive.getIntitule());
		alerteEmise.setSeuilAlerte(alerteActive.getSeuilAlerte());
		alerteEmise.setSeuilPreAlerte(alerteActive.getSeuilPreAlerte());
		alerteEmise.setTendance(alerteActive.getTendance());
		alerteEmise.setTypeAlerte(alerteActive.getTypeAlerte());
		alerteEmise.setDate(new Date());

		return alerteEmise;

	}

}
