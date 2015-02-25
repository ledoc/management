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
import fr.treeptik.model.Point;
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
		@SuppressWarnings("unused")
		Float compenseA20degre = 20F;
		Float signalBrut = trameDW.getSignalBrut();
		Enregistreur enregistreur = trameDW.getEnregistreur();
		
		logger.debug("enregistreur : " + enregistreur);
		
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

		Float accelerationGravitationnelle = 9.80665F;
		Float temperature = 30F;

		Float signalBrut = trameDW.getSignalBrut();

		Enregistreur enregistreur = trameDW.getEnregistreur();
		Float altitudeDuCapteur = enregistreur.getAltitude();
		// Float altitudeDuCapteur = 289.7F;
		Float salinite = enregistreur.getSalinite();
		// Float salinite = 0F;
		Float valeurCapteurPleineEchelle = enregistreur.getEchelleCapteur();
		// Float valeurCapteurPleineEchelle = 0.05F;

		Float pressionRelativeBrute = null;

		if (enregistreur.getTypeEnregistreur().equals(
				TypeEnregistreur.ANALOGIQUE)) {
			pressionRelativeBrute = (valeurCapteurPleineEchelle / 16)
					* (signalBrut - 4);
		} else {
			pressionRelativeBrute = signalBrut;
		}

		Float masseVolumiqueEau = (float) ((999.842594F
				+ 0.06793952F
				* temperature
				- 0.00909529F
				* (temperature * temperature)
				+ 0.0001001685F
				* (temperature * temperature * temperature)
				- 0.000001120083F
				* (temperature * temperature * temperature * temperature)
				+ 0.000000006536332F
				* (temperature * temperature * temperature * temperature * temperature)
				+ 0.824493F * salinite - 0.0040899F * temperature * salinite
				+ 0.000076438F * (temperature * temperature) * salinite
				- 0.00000082467F * (temperature * temperature * temperature)
				* salinite + 0.0000000053875F
				* (temperature * temperature * temperature * temperature)
				* salinite - 0.00572466F * Math.pow(salinite, 3 / 2)
				+ 0.00010227F * temperature * Math.pow(salinite, 3 / 2)
				- 0.0000016546F * (temperature * temperature)
				* Math.pow(salinite, 3 / 2) + 0.00048314F * (salinite * salinite)) / (1F - (pressionRelativeBrute / (((19652.21F
				+ 148.4206F
				* temperature
				- 2.327105F
				* (temperature * temperature)
				+ 0.01360477F
				* (temperature * temperature * temperature) - 0.00005155288F * (temperature
				* temperature * temperature * temperature))
				+ (54.6746F - 0.603459F * temperature + 0.0109987F
						* (temperature * temperature) - 0.00006167F * (temperature
						* temperature * temperature)) * salinite + (0.07944F + 0.016483F * temperature - 0.00053009F * (temperature * temperature))
				* Math.pow(salinite, 3 / 2))
				+ ((3.239908F + 0.00143713F * temperature + 0.000116092F
						* (temperature * temperature) - 0.000000577905F * (temperature
						* temperature * temperature))
						+ (0.0022838F - 0.000010981F * temperature - 0.0000016078F * (temperature * temperature))
						* salinite + 0.000191075F * Math.pow(salinite, 3 / 2))
				* pressionRelativeBrute + ((0.0000850935F - 0.00000612293F * temperature + 0.000000052787F * (temperature * temperature)) + (-0.000000099348F
				+ 0.000000020816F * temperature + 0.00000000091697F * (temperature * temperature))
				* salinite)
				* (pressionRelativeBrute * pressionRelativeBrute)))));

		/**
		 * HAUTEUR DE COLONNE D'EAU EN METRES
		 */

		Float hauteurColonneEau = (pressionRelativeBrute * 10000)
				/ (accelerationGravitationnelle * masseVolumiqueEau);

		/**
		 * Cote NGF du Niveau Statique mesurée = Valeur à afficher sans
		 * correction dérive sans correction barometrique
		 */

		@SuppressWarnings("unused")
		Float CoteNGFNiveauStatiqueMesuree = altitudeDuCapteur
				+ hauteurColonneEau;

		Float facteurCompensationDilatation;

		if (temperature < 4) {
			facteurCompensationDilatation = (float) (0.000009F
					* (temperature * temperature) - 0.00008 * temperature + 0.0002);
		}

		else if (temperature >= 4 && temperature < 40) {
			facteurCompensationDilatation = (float) (0.0000055F
					* (temperature * temperature) - 0.00002F * temperature - 0.00003F);
		}

		else if (temperature >= 40 && temperature < 60) {
			facteurCompensationDilatation = (float) (0.0000042
					* (temperature * temperature) + 0.00005F * temperature - 0.0009F);
		} else {
			facteurCompensationDilatation = (float) (0.00000286
					* (temperature * temperature) + 0.0002F * temperature - 0.0051F);
		}

		Float hauteurColonneEauCompenseDilatation = hauteurColonneEau
				* (1 + facteurCompensationDilatation);
		Float CoteNGFNiveauStatiqueMesureeCompenseDilatation = altitudeDuCapteur
				+ hauteurColonneEauCompenseDilatation;

		/**
		 * Pression barometrique à l'altitude du capteur (INFORMATIF de
		 * controle)
		 */
		@SuppressWarnings("unused")
		Float pressionBarometriqueAlAltitude = (float) (1013.25 * Math.pow(
				(1 - (0.0065 * altitudeDuCapteur) / 288.15), 5.255));

		trameDW.setValeur(CoteNGFNiveauStatiqueMesureeCompenseDilatation);

		trameDWService.update(trameDW);

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
	
	
	@Override
	public List<Mesure> findByEnregistreurIdBetweenDates(Integer id, Date dateDebut, Date dateFin)
			throws ServiceException {
		logger.info("--findByEnregistreurIdBetweenDates MesureService -- Id : " + id);
		List<Mesure> mesures;
		try {
			mesures = mesureDAO.findByEnregistreurIdBetweenDates(id, dateDebut, dateFin);
		} catch (PersistenceException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return mesures;
	}

	@Override
	public Point transformMesureInPoint(Mesure item) throws ServiceException {
//		logger.info("--transformMesureInPoint MesureService -- Id : " + item.getId());

		Point point = new Point();
		try {
			point.setMidEnregistreur(item.getEnregistreur().getMid());
			point.setDate(item.getDate());
			point.setValeur(item.getValeur());
		} catch (PersistenceException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return point;
	}
}
