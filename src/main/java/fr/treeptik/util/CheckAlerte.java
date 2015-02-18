package fr.treeptik.util;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Alerte;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.NiveauAlerte;
import fr.treeptik.service.AlerteService;

@Component
public class CheckAlerte {

	@Inject
	private AlerteService alerteService;

	public void checkAlerte(Enregistreur enregistreur, Mesure mesure)
			throws ServiceException {
		List<Alerte> alertesActives = alerteService
				.findAlertesActivesByEnregistreurId(enregistreur.getId());

		for (Alerte alerte : alertesActives) {
			switch (alerte.getTendance().getDescription()) {

			case "inférieur à":
				if (mesure.getValeur() < alerte.getSeuilPreAlerte()) {
					alerte.setId(null);
					alerte.setMesureLevantAlerte(mesure);
					alerte.setEmise(true);
					alerte.setDate(new Date());
					alerte.setEnregistreur(enregistreur);
					
					if (mesure.getValeur() < alerte.getSeuilAlerte()) {
						alerte.setNiveauAlerte(NiveauAlerte.ALERTE);
					} else {
						alerte.setNiveauAlerte(NiveauAlerte.PREALERTE);
					}
					alerteService.create(alerte);
				}
				break;
			case "supérieur à":
				if (mesure.getValeur() > alerte.getSeuilPreAlerte()) {
					alerte.setId(null);
					alerte.setMesureLevantAlerte(mesure);
					alerte.setEmise(true);
					alerte.setDate(new Date());
					alerte.setEnregistreur(enregistreur);
					
					if (mesure.getValeur() > alerte.getSeuilAlerte()) {
						alerte.setNiveauAlerte(NiveauAlerte.ALERTE);
					} else {
						alerte.setNiveauAlerte(NiveauAlerte.PREALERTE);
					}
					alerteService.create(alerte);
				}
				break;
			case "égal à":
				if (mesure.getValeur() == alerte.getSeuilAlerte()) {

					alerte.setId(null);
					alerte.setMesureLevantAlerte(mesure);
					alerte.setEmise(true);
					alerte.setDate(new Date());
					alerte.setEnregistreur(enregistreur);
					alerteService.create(alerte);
				}
				break;
			case "différent de":
				if (mesure.getValeur() != alerte.getSeuilAlerte()) {
					
					alerte.setId(null);
					alerte.setMesureLevantAlerte(mesure);
					alerte.setEmise(true);
					alerte.setDate(new Date());
					alerte.setEnregistreur(enregistreur);
					alerteService.create(alerte);
				}
				break;

			default:
				break;
			}
		}

	}

}
