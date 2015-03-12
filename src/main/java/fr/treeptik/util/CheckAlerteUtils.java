package fr.treeptik.util;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Administrateur;
import fr.treeptik.model.AlerteDescription;
import fr.treeptik.model.AlerteEmise;
import fr.treeptik.model.Capteur;
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

	public void checkAlerte(Capteur capteur, Mesure mesure)
			throws ServiceException, AddressException, MessagingException {
		logger.info("-- checkAlerte CheckAlerteUtils-- Enregistreur mid: "
				+ capteur.getTypeMesureOrTrame().getDescription()
				+ " mesure.getValeur() : " + mesure.getValeur());

		List<AlerteDescription> alertesActives = alerteDescriptionService
				.findAlertesActivesByCapteurId(capteur.getId());

		for (AlerteDescription alerteActive : alertesActives) {

			switch (alerteActive.getTendance().getDescription()) {

			/**
			 * TENDANCE = INFERIEUR A
			 */
			case "inférieur à":
				/**
				 * suivi d'une alerte déjà émise
				 */
				if (alerteActive.getaSurveiller()) {

					String codeAlerte = alerteActive.getCodeAlerte();
					AlerteEmise alerteEmise = alerteEmiseService
							.findLastAlerteEmiseByCodeAlerte(codeAlerte);

					NiveauAlerte niveauAlerte = alerteEmise.getNiveauAlerte();

					// Avec seuil de pré-alerte présent
					if (alerteActive.getSeuilPreAlerte() != null) {

						// Si seuil de pré-alerte dépassé mais pas celui
						// d'ALERTE
						if (mesure.getValeur() <= alerteActive
								.getSeuilPreAlerte()
								&& mesure.getValeur() >= alerteActive
										.getSeuilAlerte()) {

							// SI lA DERNIERE ALERTE EST UNE ALERTE
							if (niveauAlerte == NiveauAlerte.ALERTE) {

								alerteActive
										.setCompteurRetourNormal(alerteActive
												.getCompteurRetourNormal() + 1);

								if (alerteActive.getCompteurRetourNormal() >= 3) {

									String destinataireEmails = this
											.listAllDestinataire(alerteEmise);
									emailUtils.sendFinAlerteEmail(alerteEmise,
											mesure, destinataireEmails);

									alerteActive.setCompteurRetourNormal(0);

									AlerteEmise alerteEmiseNew = this
											.affectAlerteEmise(alerteActive);
									alerteEmiseNew
											.setMesureLevantAlerte(mesure);
									alerteEmiseNew.setCapteur(alerteActive
											.getCapteur());

									alerteEmiseNew
											.setNiveauAlerte(NiveauAlerte.PREALERTE);

									alerteEmiseNew
											.setCompteurCheckAcquittement(0);
									alerteEmiseNew.setAcquittement(false);

									mesureService.findById(mesure.getId());
									alerteEmiseNew = alerteEmiseService
											.create(alerteEmiseNew);

									alerteDescriptionService
											.update(alerteActive);

									emailUtils.sendAcquittementEmail(
											alerteEmiseNew, destinataireEmails);

								}
								alerteDescriptionService.update(alerteActive);

							}

							// SI lA DERNIERE ALERTE EST UNE PRE-ALERTE
							if (niveauAlerte == NiveauAlerte.PREALERTE) {
								alerteActive.setCompteurRetourNormal(0);

								alerteDescriptionService.update(alerteActive);
							}

						}

						// Si seuil de PRE-ALERTE PAS dépassé
						else if (mesure.getValeur() > alerteActive
								.getSeuilPreAlerte()) {

							alerteActive.setCompteurRetourNormal(alerteActive
									.getCompteurRetourNormal() + 1);

							if (alerteActive.getCompteurRetourNormal() >= 3) {

								String destinataireEmails = this
										.listAllDestinataire(alerteEmise);
								emailUtils.sendFinAlerteEmail(alerteEmise,
										mesure, destinataireEmails);

								alerteActive.setCompteurRetourNormal(0);

								alerteActive.setaSurveiller(false);
								alerteDescriptionService.update(alerteActive);

							}
							alerteDescriptionService.update(alerteActive);
						}

						// si TOUJOURS au dessus d'ALERTE
						else {

							alerteActive.setCompteurRetourNormal(0);
							alerteDescriptionService.update(alerteActive);

							if (niveauAlerte == NiveauAlerte.PREALERTE) {
								AlerteEmise alerteEmiseNew = this
										.affectAlerteEmise(alerteActive);
								alerteEmiseNew.setMesureLevantAlerte(mesure);
								alerteEmiseNew.setCapteur(alerteActive
										.getCapteur());

								alerteEmiseNew
										.setNiveauAlerte(NiveauAlerte.ALERTE);

								alerteEmiseNew.setCompteurCheckAcquittement(0);
								alerteEmiseNew.setAcquittement(false);

								mesureService.findById(mesure.getId());
								alerteEmiseNew = alerteEmiseService
										.create(alerteEmiseNew);

								alerteActive.setaSurveiller(true);
								alerteDescriptionService.update(alerteActive);

								String destinataireEmails = this
										.listAllDestinataire(alerteEmise);
								emailUtils.sendAcquittementEmail(alerteEmise,
										destinataireEmails);
							}

						}

						// SANS seuil de pré-alerte présent
					} else {
						if (mesure.getValeur() <= alerteActive.getSeuilAlerte()) {
							logger.info("-- checkAlerte : alerte inférieur à levée");

							alerteActive.setCompteurRetourNormal(alerteActive
									.getCompteurRetourNormal() + 1);

							if (alerteActive.getCompteurRetourNormal() >= 3) {

								String destinataireEmails = this
										.listAllDestinataire(alerteEmise);
								emailUtils.sendFinAlerteEmail(alerteEmise,
										mesure, destinataireEmails);

								alerteActive.setaSurveiller(false);

							}
							// SI MESURE ENCORE EN ALERTE
							else {
								alerteActive.setCompteurRetourNormal(0);
							}
							alerteDescriptionService.update(alerteActive);

						}

					}

					/**
					 * S'il n'y a pas eu ENCORE d'alerte émise
					 */
				} else {

					if (alerteActive.getSeuilPreAlerte() != null) {

						if (mesure.getValeur() < alerteActive
								.getSeuilPreAlerte()) {

							logger.info("-- checkAlerte : pré-alerte inférieur à levée");

							AlerteEmise alerteEmise = this
									.affectAlerteEmise(alerteActive);
							alerteEmise.setMesureLevantAlerte(mesure);
							alerteEmise.setCapteur(alerteActive.getCapteur());

							if (mesure.getValeur() < alerteActive
									.getSeuilAlerte()) {
								logger.info("-- checkAlerte : alerte inférieur à levée");
								alerteEmise
										.setNiveauAlerte(NiveauAlerte.ALERTE);
							} else {
								alerteEmise
										.setNiveauAlerte(NiveauAlerte.PREALERTE);
							}

							alerteEmise.setCompteurCheckAcquittement(0);
							alerteEmise.setAcquittement(false);

							mesureService.findById(mesure.getId());
							alerteEmise = alerteEmiseService
									.create(alerteEmise);

							alerteActive.setaSurveiller(true);
							alerteActive.setCompteurRetourNormal(0);
							alerteDescriptionService.update(alerteActive);

							String destinataireEmails = this
									.listAllDestinataire(alerteEmise);
							emailUtils.sendAcquittementEmail(alerteEmise,
									destinataireEmails);
						}
					} else {
						if (mesure.getValeur() < alerteActive.getSeuilAlerte()) {
							logger.info("-- checkAlerte : alerte inférieur à levée");

							AlerteEmise alerteEmise = this
									.affectAlerteEmise(alerteActive);
							alerteEmise.setMesureLevantAlerte(mesure);
							alerteEmise.setCapteur(alerteActive.getCapteur());

							alerteEmise.setNiveauAlerte(NiveauAlerte.ALERTE);

							alerteEmise.setCompteurCheckAcquittement(0);
							alerteEmise.setAcquittement(false);

							mesureService.findById(mesure.getId());
							alerteEmise = alerteEmiseService
									.create(alerteEmise);

							alerteActive.setaSurveiller(true);
							alerteActive.setCompteurRetourNormal(0);
							alerteDescriptionService.update(alerteActive);

							String destinataireEmails = this
									.listAllDestinataire(alerteEmise);
							emailUtils.sendAcquittementEmail(alerteEmise,
									destinataireEmails);

						}

					}

				}
				break;

			/**
			 * TENDANCE = SUPERIEUR A
			 */
			case "supérieur à":
				/**
				 * suivi d'une alerte déjà émise
				 */
				if (alerteActive.getaSurveiller()) {
					String codeAlerte = alerteActive.getCodeAlerte();
					AlerteEmise alerteEmise = alerteEmiseService
							.findLastAlerteEmiseByCodeAlerte(codeAlerte);

					NiveauAlerte niveauAlerte = alerteEmise.getNiveauAlerte();

					// Avec seuil de pré-alerte présent
					if (alerteActive.getSeuilPreAlerte() != null) {

						// Si seuil de pré-alerte dépassé mais pas celui
						// d'ALERTE
						if (mesure.getValeur() >= alerteActive
								.getSeuilPreAlerte()
								&& mesure.getValeur() <= alerteActive
										.getSeuilAlerte()) {

							// SI lA DERNIERE ALERTE EST UNE ALERTE
							if (niveauAlerte == NiveauAlerte.ALERTE) {

								alerteActive
										.setCompteurRetourNormal(alerteActive
												.getCompteurRetourNormal() + 1);

								if (alerteActive.getCompteurRetourNormal() >= 3) {

									AlerteEmise alerteEmiseNew = this
											.affectAlerteEmise(alerteActive);
									alerteEmiseNew
											.setMesureLevantAlerte(mesure);
									alerteEmiseNew.setCapteur(alerteActive
											.getCapteur());

									alerteEmiseNew
											.setNiveauAlerte(NiveauAlerte.PREALERTE);

									alerteEmiseNew
											.setCompteurCheckAcquittement(0);
									alerteEmiseNew.setAcquittement(false);

									mesureService.findById(mesure.getId());
									alerteEmiseNew = alerteEmiseService
											.create(alerteEmiseNew);

									String destinataireEmails = this
											.listAllDestinataire(alerteEmise);

									emailUtils.sendFinAlerteEmail(alerteEmise,
											mesure, destinataireEmails);

									emailUtils.sendAcquittementEmail(
											alerteEmiseNew, destinataireEmails);

									alerteActive.setCompteurRetourNormal(0);

								}
								alerteDescriptionService.update(alerteActive);

							}

							// SI lA DERNIERE ALERTE EST UNE PRE-ALERTE
							if (niveauAlerte == NiveauAlerte.PREALERTE) {
								alerteActive.setCompteurRetourNormal(0);
								alerteDescriptionService.update(alerteActive);

								logger.info("alerteActive :" + alerteActive);
							}

						}

						// Si seuil de PRE-ALERTE PAS dépassé
						else if (mesure.getValeur() < alerteActive
								.getSeuilPreAlerte()) {

							alerteActive.setCompteurRetourNormal(alerteActive
									.getCompteurRetourNormal() + 1);

							if (alerteActive.getCompteurRetourNormal() >= 3) {

								String destinataireEmails = this
										.listAllDestinataire(alerteEmise);
								emailUtils.sendFinAlerteEmail(alerteEmise,
										mesure, destinataireEmails);

								alerteActive.setCompteurRetourNormal(0);

								alerteActive.setaSurveiller(false);

							}
							alerteDescriptionService.update(alerteActive);

							logger.info("alerteActive :" + alerteActive);
						}

						// si TOUJOURS au dessus d'ALERTE
						else {

							alerteActive.setCompteurRetourNormal(0);
							alerteDescriptionService.update(alerteActive);

							if (niveauAlerte == NiveauAlerte.PREALERTE) {
								AlerteEmise alerteEmiseNew = this
										.affectAlerteEmise(alerteActive);
								alerteEmiseNew.setMesureLevantAlerte(mesure);
								alerteEmiseNew.setCapteur(alerteActive
										.getCapteur());

								alerteEmiseNew
										.setNiveauAlerte(NiveauAlerte.ALERTE);

								alerteEmiseNew.setCompteurCheckAcquittement(0);
								alerteEmiseNew.setAcquittement(false);

								mesureService.findById(mesure.getId());
								alerteEmiseNew = alerteEmiseService
										.create(alerteEmiseNew);

								alerteActive.setaSurveiller(true);
								alerteDescriptionService.update(alerteActive);

								String destinataireEmails = this
										.listAllDestinataire(alerteEmise);
								emailUtils.sendAcquittementEmail(alerteEmise,
										destinataireEmails);

							}

						}

						// SANS seuil de pré-alerte présent
					} else {

						// SI MESURE NORMALE
						if (mesure.getValeur() < alerteActive.getSeuilAlerte()) {
							logger.info("-- checkAlerte : alerte inférieur à levée");

							alerteActive.setCompteurRetourNormal(alerteActive
									.getCompteurRetourNormal() + 1);

							if (alerteActive.getCompteurRetourNormal() >= 3) {

								String destinataireEmails = this
										.listAllDestinataire(alerteEmise);
								emailUtils.sendFinAlerteEmail(alerteEmise,
										mesure, destinataireEmails);

								alerteActive.setaSurveiller(false);

							}
							// SI MESURE ENCORE EN ALERTE
							else {
								alerteActive.setCompteurRetourNormal(0);
							}
							alerteDescriptionService.update(alerteActive);

						}

					}

					/**
					 * S'il n'y a pas eu ENCORE d'alerte émise
					 */
				} else {

					if (alerteActive.getSeuilPreAlerte() != null) {
						if (mesure.getValeur() > alerteActive
								.getSeuilPreAlerte()) {
							logger.info("-- checkAlerte : pré-alerte supérieur à levée");

							AlerteEmise alerteEmise = this
									.affectAlerteEmise(alerteActive);
							alerteEmise.setMesureLevantAlerte(mesure);
							alerteEmise.setCapteur(alerteActive.getCapteur());

							if (mesure.getValeur() > alerteActive
									.getSeuilAlerte()) {
								logger.info("-- checkAlerte : alerte supérieur à levée");

								alerteEmise
										.setNiveauAlerte(NiveauAlerte.ALERTE);
							} else {
								alerteEmise
										.setNiveauAlerte(NiveauAlerte.PREALERTE);
							}

							alerteEmise.setCompteurCheckAcquittement(0);
							alerteEmise.setAcquittement(false);

							mesureService.findById(mesure.getId());
							alerteEmise = alerteEmiseService
									.create(alerteEmise);

							alerteActive.setaSurveiller(true);
							alerteActive.setCompteurRetourNormal(0);
							alerteDescriptionService.update(alerteActive);

							String destinataireEmails = this
									.listAllDestinataire(alerteEmise);
							emailUtils.sendAcquittementEmail(alerteEmise,
									destinataireEmails);
						}
					} else {
						if (mesure.getValeur() > alerteActive.getSeuilAlerte()) {
							logger.info("-- checkAlerte : alerte supérieur à levée");

							AlerteEmise alerteEmise = this
									.affectAlerteEmise(alerteActive);
							alerteEmise.setMesureLevantAlerte(mesure);
							alerteEmise.setCapteur(alerteActive.getCapteur());

							alerteEmise.setNiveauAlerte(NiveauAlerte.ALERTE);

							alerteEmise.setCompteurCheckAcquittement(0);
							alerteEmise.setAcquittement(false);

							mesureService.findById(mesure.getId());
							alerteEmise = alerteEmiseService
									.create(alerteEmise);

							alerteActive.setaSurveiller(true);
							alerteActive.setCompteurRetourNormal(0);
							alerteDescriptionService.update(alerteActive);

							String destinataireEmails = this
									.listAllDestinataire(alerteEmise);
							emailUtils.sendAcquittementEmail(alerteEmise,
									destinataireEmails);
						}
					}
				}
				break;

			/**
			 * TENDANCE = EGAL A
			 */
			case "égal à":

				/**
				 * suivi d'unne alerte déjà émise
				 */
				if (alerteActive.getaSurveiller()) {
					String codeAlerte = alerteActive.getCodeAlerte();
					AlerteEmise alerteEmise = alerteEmiseService
							.findLastAlerteEmiseByCodeAlerte(codeAlerte);

					// SI MESURE NORMALE
					if (mesure.getValeur() != alerteActive.getSeuilAlerte()) {
						logger.info("-- checkAlerte : alerte inférieur à levée");

						alerteActive.setCompteurRetourNormal(alerteActive
								.getCompteurRetourNormal() + 1);

						if (alerteActive.getCompteurRetourNormal() >= 3) {

							String destinataireEmails = this
									.listAllDestinataire(alerteEmise);
							emailUtils.sendFinAlerteEmail(alerteEmise, mesure,
									destinataireEmails);

							alerteActive.setaSurveiller(false);

						}
						// SI MESURE ENCORE EN ALERTE
						else {
							alerteActive.setCompteurRetourNormal(0);
						}
						alerteDescriptionService.update(alerteActive);

					}

					/**
					 * S'il n'y a pas eu ENCORE d'alerte émise
					 */
				} else {

					if (mesure.getValeur() == alerteActive.getSeuilAlerte()) {
						logger.info("-- checkAlerte : alerte égal à levée");

						AlerteEmise alerteEmise = this
								.affectAlerteEmise(alerteActive);
						alerteEmise.setMesureLevantAlerte(mesure);
						alerteEmise.setCapteur(alerteActive.getCapteur());

						alerteEmise.setCompteurCheckAcquittement(0);
						alerteEmise.setAcquittement(false);

						mesureService.findById(mesure.getId());
						alerteEmise = alerteEmiseService.create(alerteEmise);

						alerteActive.setaSurveiller(true);
						alerteActive.setCompteurRetourNormal(0);
						alerteDescriptionService.update(alerteActive);

						String destinataireEmails = this
								.listAllDestinataire(alerteEmise);
						emailUtils.sendAcquittementEmail(alerteEmise,
								destinataireEmails);

					}
				}
				break;

			/**
			 * TENDANCE = DIFFERENT DE
			 */
			case "différent de":

				/**
				 * suivi d'unne alerte déjà émise
				 */
				if (alerteActive.getaSurveiller()) {
					String codeAlerte = alerteActive.getCodeAlerte();
					AlerteEmise alerteEmise = alerteEmiseService
							.findLastAlerteEmiseByCodeAlerte(codeAlerte);

					// SI MESURE NORMALE
					if (mesure.getValeur() == alerteActive.getSeuilAlerte()) {
						logger.info("-- checkAlerte : alerte inférieur à levée");

						alerteActive.setCompteurRetourNormal(alerteActive
								.getCompteurRetourNormal() + 1);

						if (alerteActive.getCompteurRetourNormal() >= 3) {

							String destinataireEmails = this
									.listAllDestinataire(alerteEmise);
							emailUtils.sendFinAlerteEmail(alerteEmise, mesure,
									destinataireEmails);

							alerteActive.setaSurveiller(false);

						}
						// SI MESURE ENCORE EN ALERTE
						else {
							alerteActive.setCompteurRetourNormal(0);
						}
						alerteDescriptionService.update(alerteActive);

					}

					/**
					 * S'il n'y a pas eu ENCORE d'alerte émise
					 */
				} else {

					if (mesure.getValeur() != alerteActive.getSeuilAlerte()) {
						logger.info("-- checkAlerte : alerte différent de levée");

						AlerteEmise alerteEmise = this
								.affectAlerteEmise(alerteActive);
						alerteEmise.setMesureLevantAlerte(mesure);
						alerteEmise.setCapteur(alerteActive.getCapteur());

						alerteEmise.setCompteurCheckAcquittement(0);
						alerteEmise.setAcquittement(false);

						mesureService.findById(mesure.getId());
						alerteEmise = alerteEmiseService.create(alerteEmise);

						alerteActive.setaSurveiller(true);
						alerteActive.setCompteurRetourNormal(0);
						alerteDescriptionService.update(alerteActive);

						String destinataireEmails = this
								.listAllDestinataire(alerteEmise);
						emailUtils.sendAcquittementEmail(alerteEmise,
								destinataireEmails);

					}
				}
				break;

			default:
				logger.error("ERROR --  checkAlerte() checkAlerte -- la tendance de l'alerte n'a pas été trouvée");
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

	private String listAllDestinataire(AlerteEmise alerteEmise)
			throws ServiceException {
		logger.info("-- checkAlerte checkAlerte-- alerteEmise : "
				+ alerteEmise.getCodeAlerte());
		StringBuilder strbld = new StringBuilder();
		Etablissement etablissement = alerteEmise.getCapteur()
				.getEnregistreur().getOuvrage().getSite().getEtablissement();
		etablissement = etablissementService
				.findByIdWithJoinFetchClients(etablissement.getId());
		etablissement.getClients().forEach(
				c -> strbld.append(c.getMail1() + ","));
		List<Administrateur> administrateurs = administrateurService.findAll();
		administrateurs.forEach(a -> strbld.append(a.getMail1() + ","));

		String destinataireEmails = strbld.toString();
		destinataireEmails = destinataireEmails.substring(0,
				destinataireEmails.lastIndexOf(","));

		return destinataireEmails;
	}

}
