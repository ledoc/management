package fr.treeptik.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.mail.MessagingException;
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
import fr.treeptik.util.CheckAlerteUtils;

@Service
public class MesureServiceImpl implements MesureService {

	@Inject
	private MesureDAO mesureDAO;

	@Inject
	private CheckAlerteUtils checkAlerteUtils;

	@Inject
	private TrameDWService trameDWService;

	private Logger logger = Logger.getLogger(MesureServiceImpl.class);

	/**
	 * CONDUCTIVITE
	 */
	@Override
	public TrameDW conversionSignalElectrique_Conductivite(TrameDW trameDW)
			throws ServiceException {
		logger.info("--conversionSignalElectrique_HauteurEau mesure --");

		// hauteur d’eau au-dessus de l’enregistreur à un instant t (en mètre)
		Float valeur;

		/**
		 * TODO ajouter la temperature comme variable
		 */
		Float temperature = 25F;
		Float compenseA25degre = 25F;
		Float compenseA20degre = 20F;
		Float signalBrut = trameDW.getSignalBrut();
		Enregistreur enregistreur = trameDW.getEnregistreur();
		Float coeffTemperature = enregistreur.getCoeffTemperature();
		Float valeurCapteurPleineEchelle = enregistreur.getEchelleCapteur();

		if (enregistreur.getTypeEnregistreur().equals(
				TypeEnregistreur.ANALOGIQUE)) {
			valeur = ((((temperature - compenseA25degre) * coeffTemperature) / 100) + 1)
					* (valeurCapteurPleineEchelle / 16) * (signalBrut - 4);
		} else {
			valeur = ((((temperature - compenseA25degre) * coeffTemperature) / 100) + 1)
					* signalBrut;
		}
		trameDW.setValeur(valeur);

		trameDWService.update(trameDW);

		/**
		 * TODO Voir à déporter ça pour être générique
		 */
		Mesure mesure = new Mesure();
		mesure.setDate(trameDW.getDate());
		mesure.setTypeMesureOrTrame(TypeMesureOrTrame.CONDUCTIVITE);
		mesure.setEnregistreur(enregistreur);
		mesure.setValeur(valeur);

		this.create(mesure);

		mesure = this.findById(mesure.getId());

		try {
			checkAlerteUtils.checkAlerte(enregistreur, mesure);
		} catch (MessagingException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		return trameDW;
	}

	/**
	 * COTE ALTIMETRIQUE
	 */
	@Override
	public TrameDW conversionSignalElectrique_CoteAltimetrique(TrameDW trameDW)
			throws ServiceException {
		logger.info("--conversionSignalElectrique_HauteurEau mesure --");

		// hauteur d’eau au-dessus de l’enregistreur à un instant t (en mètre)
		Float valeur;
		Float salinite = 0F;
		Float altitudeDuCapteur = trameDW.getEnregistreur().getAltitude();
		Float accelerationGravitationnelle = 9.80665F;
		Float masseVolumiqueEau = 995.651F;

		/**
		 * TODO ajouter la temperature comme variable
		 */
		Float temperature = 25F;
		Float compenseA25degre = 25F;
		Float compenseA20degre = 20F;
		Float signalBrut = trameDW.getSignalBrut();
		Enregistreur enregistreur = trameDW.getEnregistreur();
		Float coeffTemperature = enregistreur.getCoeffTemperature();
		Float valeurCapteurPleineEchelle = enregistreur.getEchelleCapteur();

		Float pressionRelativeBrute = null;

		if (enregistreur.getTypeEnregistreur().equals(
				TypeEnregistreur.ANALOGIQUE)) {
			pressionRelativeBrute = (valeurCapteurPleineEchelle / 16)
					* (signalBrut - 4);
		} else {
			pressionRelativeBrute = signalBrut;
		}

		/**
		 * HAUTEUR DE COLONNE D'EAU EN METRES
		 */

		Float hauteurColonneEau = (pressionRelativeBrute * 10000)
				/ (accelerationGravitationnelle * masseVolumiqueEau);

		/**
		 * Cote NGF du Niveau Statique mesurée = Valeur à afficher sans
		 * correction dérive
		 */

		Float CoteNGFNiveauStatiqueMesuree = altitudeDuCapteur
				+ hauteurColonneEau;

		/**
		 * 
		 * =IF(temperature<4,0.000009*temperature^2-0.00008*temperature+0.0002,
		 * IF(AND(temperature>=4,temperature<40), 0.0000055
		 * *temperature^2-0.00002
		 * *temperature-0.00003,IF(AND(temperature>=40,temperature
		 * <60),0.0000042*temperature
		 * ^2+0.00005*temperature-0.0009,0.00000286*temperature
		 * ^2+0.0002*temperature-0.0051)))
		 * 
		 */
		Float facteurCompensationDilatation;

		if (temperature < 4) {
			facteurCompensationDilatation = (float) (0.000009F * Math.pow(
					temperature, 2 - 0.00008 * temperature + 0.0002));
		}

		else if (temperature <= 4 && temperature < 40) {
			facteurCompensationDilatation = (float) (0.0000055 * Math.pow(
					temperature, 2 - 0.00002 * temperature - 0.00003));
		}

		else if (temperature >= 4 && temperature < 60) {
			facteurCompensationDilatation = (float) (0.0000042 * Math.pow(
					temperature, 2 + 0.00005 * temperature - 0.0009));
		} else {
			facteurCompensationDilatation = (float) (0.00000286 * Math.pow(
					temperature, 2 + 0.0002 * temperature - 0.0051));

		}

		Float hauteurColonneEauCompenseDilatation = hauteurColonneEau
				* (1 + facteurCompensationDilatation);
		Float CoteNGFNiveauStatiqueMesureeCompenseDilatation = altitudeDuCapteur
				+ hauteurColonneEauCompenseDilatation;

		/**
		 * Pression barometrique à l'altitude du capteur (INFORMATIF de
		 * controle)
		 */
		Float pressionBarometriqueAlAltitude = (float) (1013.25 * Math.pow(
				(1 - (0.0065 * altitudeDuCapteur) / 288.15), 5.255));

		trameDW.setValeur(CoteNGFNiveauStatiqueMesureeCompenseDilatation);

		trameDWService.update(trameDW);

		/**
		 * TODO Voir à déporter ça pour être générique
		 */
		Mesure mesure = new Mesure();
		mesure.setDate(trameDW.getDate());
		mesure.setTypeMesureOrTrame(TypeMesureOrTrame.NIVEAUDEAU);
		mesure.setEnregistreur(enregistreur);
		mesure.setValeur(CoteNGFNiveauStatiqueMesureeCompenseDilatation);

		this.create(mesure);

		mesure = this.findById(mesure.getId());

		try {
			checkAlerteUtils.checkAlerte(enregistreur, mesure);
		} catch (MessagingException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
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
		mesure.setTypeMesureOrTrame(TypeMesureOrTrame.NIVEAUDEAU);
		mesure.setEnregistreur(enregistreur);
		mesure.setValeur(niveauEau);

		enregistreur.getMesures().add(mesure);

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
	public List<Mesure> findAllDetails() throws ServiceException {
		logger.info("--findAllDetails MesureService --");
		
		List<Mesure> mesures = mesureDAO.findAllDetails();
		return mesures;
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
}
