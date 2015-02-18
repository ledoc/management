package fr.treeptik.util;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.AlerteDescription;
import fr.treeptik.model.AlerteEmise;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.NiveauAlerte;
import fr.treeptik.service.AlerteDescriptionService;
import fr.treeptik.service.AlerteEmiseService;

@Component
public class CheckAlerte {

	@Inject
	private AlerteDescriptionService alerteDescriptionService;
	@Inject
	private AlerteEmiseService alerteEmiseService;

	public void checkAlerte(Enregistreur enregistreur, Mesure mesure)
			throws ServiceException {
		List<AlerteDescription> alertesActives = alerteDescriptionService
				.findAlertesActivesByEnregistreurId(enregistreur.getId());

		for (AlerteDescription alerteActive : alertesActives) {
			switch (alerteActive.getTendance().getDescription()) {

			case "inférieur à":
				if (mesure.getValeur() < alerteActive.getSeuilPreAlerte()) {
					AlerteEmise alerteEmise = new AlerteEmise();
					alerteEmise.setMesureLevantAlerte(mesure);
					alerteEmise.setDate(new Date());
					alerteEmise.setEnregistreur(enregistreur);

					if (mesure.getValeur() < alerteActive.getSeuilAlerte()) {
						alerteEmise.setNiveauAlerte(NiveauAlerte.ALERTE);
					} else {
						alerteEmise.setNiveauAlerte(NiveauAlerte.PREALERTE);
					}
					alerteEmiseService.create(alerteEmise);
				}
				break;
			case "supérieur à":
				if (mesure.getValeur() > alerteActive.getSeuilPreAlerte()) {
					AlerteEmise alerteEmise = new AlerteEmise();
					alerteEmise.setMesureLevantAlerte(mesure);
					alerteEmise.setDate(new Date());
					alerteEmise.setEnregistreur(enregistreur);

					if (mesure.getValeur() > alerteActive.getSeuilAlerte()) {
						alerteEmise.setNiveauAlerte(NiveauAlerte.ALERTE);
					} else {
						alerteEmise.setNiveauAlerte(NiveauAlerte.PREALERTE);
					}
					alerteEmiseService.create(alerteEmise);
				}
				break;
			case "égal à":
				if (mesure.getValeur() == alerteActive.getSeuilAlerte()) {
					AlerteEmise alerteEmise = new AlerteEmise();
					alerteEmise.setMesureLevantAlerte(mesure);
					alerteEmise.setDate(new Date());
					alerteEmise.setEnregistreur(enregistreur);
					alerteEmiseService.create(alerteEmise);
				}
				break;
			case "différent de":
				if (mesure.getValeur() != alerteActive.getSeuilAlerte()) {
					AlerteEmise alerteEmise = new AlerteEmise();
					alerteEmise.setMesureLevantAlerte(mesure);
					alerteEmise.setDate(new Date());
					alerteEmise.setEnregistreur(enregistreur);
					alerteEmiseService.create(alerteEmise);
				}
				break;

			default:
				break;
			}
		}

	}

}
