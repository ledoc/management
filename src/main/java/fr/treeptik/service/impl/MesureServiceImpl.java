package fr.treeptik.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.MesureDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.TrameDW;
import fr.treeptik.model.TypeEnregistreur;
import fr.treeptik.model.TypeMesureOrTrame;
import fr.treeptik.service.MesureService;
import fr.treeptik.service.TrameDWService;
import fr.treeptik.util.CheckAlerte;

@Service
public class MesureServiceImpl implements MesureService {

	@Inject
	private MesureDAO mesureDAO;

	@Inject
	private CheckAlerte checkAlerte;

	@Inject
	private TrameDWService trameDWService;

	private Logger logger = Logger.getLogger(MesureServiceImpl.class);

	// - profMax : profondeur maximale pour laquel l'enregistreur a été étalonné
	// (en mètre)
	// - intensite : valeur brute transmise par le capteur à un instant t (mA)

	// Valeur à afficher comme mesure conductivité nette à 25°C =
	// ((((Température-25)*coefficient de température)/100)+1)*((valeur capteur
	// pleine échelle/16)*(Valeur mesurée-4))

	/**
	 * 
	 */
	@Override
	public TrameDW conversionSignalElectrique_Valeur(TrameDW trameDW)
			throws ServiceException {
		logger.info("--conversionSignalElectrique_HauteurEau mesure --");

		// hauteur d’eau au-dessus de l’enregistreur à un instant t (en mètre)
		Float valeur;
		/**
		 * TODO ajouter la temperature comme variable
		 */
		Float temperature = 25F;
		Float signalBrut = trameDW.getSignalBrut();
		Enregistreur enregistreur = trameDW.getEnregistreur();
		Float coeffTemperature = enregistreur.getCoeffTemperature();
		Float valeurCapteurPleineEchelle = enregistreur.getEchelleCapteur();

		if (enregistreur.getTypeEnregistreur().equals(
				TypeEnregistreur.ANALOGIQUE)) {
			valeur = ((((temperature - 25) * coeffTemperature) / 100) + 1)
					* (valeurCapteurPleineEchelle / 16) * (signalBrut - 4);
		} else {
			valeur = ((((temperature - 25) * coeffTemperature) / 100) + 1)
					* signalBrut;
		}
		trameDW.setValeur(valeur);

		trameDW = trameDWService.findById(trameDW.getId());
		trameDWService.update(trameDW);

		/**
		 * TODO Voir à déporter sa pour être générique
		 */
		Mesure mesure = new Mesure();
		mesure.setDate(new Date());
		mesure.setTypeMesure(TypeMesureOrTrame.NIVEAUDEAU);
		mesure.setEnregistreur(enregistreur);
		mesure.setValeur(valeur);
		
		this.create(mesure);
		
		checkAlerte.checkAlerte(enregistreur, mesure);

		return trameDW;
	}

	// Ns0 = Nm0
	// Nm0 : mesure manuelle initiale
	// Nsi = Nsi-1 + (hauteurEau i - hauteurEau i-1)
	@Override
	public float conversionHauteurEau_CoteAltimetrique(TrameDW trameDW)
			throws ServiceException {
		logger.info("--conversionHauteurEau_CoteAltimetrique mesure --");

		// hauteur d’eau au-dessus de l’enregistreur à un instant t (en mètre)
		float niveauEau;
		float dernierNiveauEau;
		float derniereHauteurEau;
		Enregistreur enregistreur = trameDW.getEnregistreur();
		float hauteurEau = trameDW.getValeur();
		derniereHauteurEau = enregistreur.getDerniereTrameDW().getValeur();

		if (enregistreur.getDerniereMesure().getValeur() != null) {
			dernierNiveauEau = enregistreur.getDerniereMesure().getValeur();

		} else {
			dernierNiveauEau = enregistreur.getNiveauManuel().getValeur();
		}

		niveauEau = dernierNiveauEau + (hauteurEau - derniereHauteurEau);

		Mesure mesure = new Mesure();
		mesure.setDate(new Date());
		mesure.setTypeMesure(TypeMesureOrTrame.NIVEAUDEAU);
		mesure.setEnregistreur(enregistreur);
		mesure.setValeur(niveauEau);

		enregistreur.getMesures().add(mesure);

		// enregistreurService

		return niveauEau;
	}

	@Override
	public Mesure findById(Integer id) throws ServiceException {
		Mesure mesure = new Mesure();
		mesure = mesureDAO.findOne(id);
		return mesure;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Mesure create(Mesure mesure) throws ServiceException {
		logger.info("--CREATE MesureService --");
		logger.debug("mesure : " + mesure);
		return mesureDAO.save(mesure);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Mesure update(Mesure mesure) throws ServiceException {
		logger.info("--UPDATE MesureService --");
		logger.debug("mesure : " + mesure);
		return mesureDAO.saveAndFlush(mesure);
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE MesureService -- mesureId : " + id);

		Mesure mesure = this.findById(id);

		logger.debug("--DELETE MesureService -- : " + mesure);
		mesureDAO.delete(mesure);
	}

	@Override
	public List<Mesure> findAll() throws ServiceException {
		logger.info("--FINDALL MesureService --");
		return mesureDAO.findAll();
	}

	@Override
	public List<Mesure> findByEnregistreurId(Integer id)
			throws ServiceException {
		logger.info("--findByEnregistreurId MesureService -- Id : " + id);
		List<Mesure> mesures;
		try {
			mesures = mesureDAO.findByEnregistreurId(id);
		} catch (PersistenceException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return mesures;
	}

	@Override
	public List<Mesure> findByOuvrageId(Integer id) throws ServiceException {
		logger.info("--findByOuvrageId MesureService by Id--");
		List<Mesure> mesures;
		try {
			mesures = mesureDAO.findByOuvrageId(id);
		} catch (PersistenceException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return mesures;
	}
}
