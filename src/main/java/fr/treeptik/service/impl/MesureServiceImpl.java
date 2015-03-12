package fr.treeptik.service.impl;

import java.util.Date;
import java.util.HashMap;
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
import fr.treeptik.model.Capteur;
import fr.treeptik.model.Enregistreur;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.Point;
import fr.treeptik.model.TypeEnregistreur;
import fr.treeptik.model.TypeMesureOrTrame;
import fr.treeptik.service.CapteurService;
import fr.treeptik.service.EnregistreurService;
import fr.treeptik.service.MesureService;
import fr.treeptik.util.CheckAlerteUtils;

@Service
public class MesureServiceImpl implements MesureService {

	@Inject
	private MesureDAO mesureDAO;

	@Inject
	private CheckAlerteUtils checkAlerteUtils;

	@Inject
	private CapteurService capteurService;

	@Inject
	private EnregistreurService enregistreurService;

	private Logger logger = Logger.getLogger(MesureServiceImpl.class);

	/**
	 * TEMPERATURE
	 */
	@Override
	public Mesure conversionSignal_Temperature(Mesure mesureTemp)
			throws ServiceException {
		logger.info("--conversionSignalElectrique_Temperature MesureServiceImpl --");

		Float valeur;
		Float signalBrut = mesureTemp.getSignalBrut();
		Capteur capteur = mesureTemp.getCapteur();
		Enregistreur enregistreur = capteur.getEnregistreur();
		Float echelleMinCapteur = capteur.getEchelleMinCapteur();
		Float echelleMaxCapteur = capteur.getEchelleMaxCapteur();

		if (enregistreur.getTypeEnregistreur().equals(
				TypeEnregistreur.ANALOGIQUE)) {
			valeur = (((echelleMaxCapteur - echelleMinCapteur) / 16) * (signalBrut - 4))
					/ echelleMinCapteur;
		} else {
			valeur = signalBrut;
		}
		mesureTemp.setValeur(valeur);
		this.create(mesureTemp);

		return mesureTemp;
	}

	/**
	 * CONDUCTIVITE
	 */
	@Override
	public Mesure conversionSignal_Conductivite(
			HashMap<TypeMesureOrTrame, Mesure> hashMapCalcul)
			throws ServiceException {
		logger.info("--conversionSignal_Conductivite MesureServiceImpl --");

		Float valeur;

		Mesure mesureCond = hashMapCalcul.get(TypeMesureOrTrame.CONDUCTIVITE);
		Mesure mesureTemp = null;
		Float temperature = 25F;
		Float compenseA25degre = 25F;
		@SuppressWarnings("unused")
		Float compenseA20degre = 20F;

		Float signalBrut = mesureCond.getSignalBrut();

		Capteur capteur = mesureCond.getCapteur();
		Float echelleMaxCapteur = capteur.getEchelleMaxCapteur();
		Float echelleMinCapteur = capteur.getEchelleMinCapteur();

		Enregistreur enregistreur = capteur.getEnregistreur();
		Float coeffTemperature = enregistreur.getCoeffTemperature();

		if (hashMapCalcul.containsKey(TypeMesureOrTrame.TEMPERATURE)) {
			mesureTemp = this.conversionSignal_Temperature(hashMapCalcul
					.get(TypeMesureOrTrame.TEMPERATURE));
			temperature = mesureTemp.getValeur();
		}

		if (enregistreur.getTypeEnregistreur().equals(
				TypeEnregistreur.ANALOGIQUE)) {
			valeur = ((((temperature - compenseA25degre) * coeffTemperature) / 100) + 1)
					* (echelleMaxCapteur - echelleMinCapteur / 16)
					* (signalBrut - 4);
		} else {
			valeur = ((((temperature - compenseA25degre) * coeffTemperature) / 100) + 1)
					* signalBrut;
		}
		mesureCond.setValeur(valeur);

		mesureCond = this.create(mesureCond);

		try {
			checkAlerteUtils.checkAlerte(capteur, mesureCond);
		} catch (MessagingException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		return mesureCond;
	}

	/**
	 * TODO : NiveauEauDeSurface
	 */
	@Override
	public Mesure conversionSignal_NiveauEauDeSurface(
			HashMap<TypeMesureOrTrame, Mesure> hashMapCalcul)
			throws ServiceException {
		logger.info("--conversionSignalElectrique_NiveauEauDeSurface mesure --");

		Mesure mesureNiveauEau = hashMapCalcul
				.get(TypeMesureOrTrame.CONDUCTIVITE);
		Mesure mesureTemp = null;
		Float temperature = 25F;
		Float compenseA25degre = 25F;
		Float compenseA20degre = 20F;

		Float signalBrut = mesureNiveauEau.getSignalBrut();

		Capteur capteur = mesureNiveauEau.getCapteur();
		Float echelleMaxCapteur = capteur.getEchelleMaxCapteur();
		Float echelleMinCapteur = capteur.getEchelleMinCapteur();

		Enregistreur enregistreur = capteur.getEnregistreur();
		Float coeffTemperature = enregistreur.getCoeffTemperature();

		if (hashMapCalcul.containsKey(TypeMesureOrTrame.TEMPERATURE)) {
			mesureTemp = this.conversionSignal_Temperature(hashMapCalcul
					.get(TypeMesureOrTrame.TEMPERATURE));
			temperature = mesureTemp.getValeur();
		}

		Float hauteurDeVide;

		if (enregistreur.getTypeEnregistreur().equals(
				TypeEnregistreur.ANALOGIQUE)) {
			hauteurDeVide = ((echelleMaxCapteur - echelleMinCapteur) / 16)
					* (signalBrut - 4) + 1;
		} else {
			hauteurDeVide = signalBrut;
		}

		Float repereFilEau = ((enregistreur.getOuvrage().getCoteSolBerge() - enregistreur
				.getOuvrage().getCoteRepereNGF()) + hauteurDeVide);

		Float profondeurHauteurDEauEnM = (enregistreur.getOuvrage()
				.getCoteSolBerge() - enregistreur.getOuvrage()
				.getMesureRepereNGFSol());

		Float debit = (-4000000F * (profondeurHauteurDEauEnM
				* profondeurHauteurDEauEnM * profondeurHauteurDEauEnM * profondeurHauteurDEauEnM))
				+ (18288F * (profondeurHauteurDEauEnM
						* profondeurHauteurDEauEnM * profondeurHauteurDEauEnM))
				+ (22424F * (profondeurHauteurDEauEnM * profondeurHauteurDEauEnM))
				+ (22.444F * profondeurHauteurDEauEnM - 0.0235F);

		/**
		 * =IF(D28=D23,(J133*(E31-E29)*24*60*60),IF(D28=D24,J133*(E31-E29)*24*60
		 * ,IF(D28=D25,(J133*(E31-E29)*24),IF(D28=D26,(J133*(E31-E29)*24*60*60*
		 * 1000),IF(D28=D27,(J133*(E31-E29)*24*60*60*1000/3600))))))
		 */

		/**
		 * CUMUL
		 */
		// Float cumulSansCorrecDeriveEnLitre;
		//
		//
		// String choixUnite = null;
		//
		// if(choixUnite == "l/s" ) {
		//
		// moyenneNonRecalee * (dateN - dateMoinsN) * 24 * 60 * 60
		//
		// }

		Float coteNGFNiveauEauMesure = enregistreur.getOuvrage()
				.getCoteRepereNGF() - hauteurDeVide;

		mesureNiveauEau.setValeur(coteNGFNiveauEauMesure);

		this.create(mesureNiveauEau);

		mesureNiveauEau = this.findById(mesureNiveauEau.getId());

		try {
			checkAlerteUtils.checkAlerte(capteur, mesureNiveauEau);
		} catch (MessagingException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		return mesureNiveauEau;
	}

	/**
	 * COTE ALTIMETRIQUE
	 */
	@Override
	public Mesure conversionSignal_NiveauEauNappeSouterraine(
			HashMap<TypeMesureOrTrame, Mesure> hashMapCalcul)
			throws ServiceException {
		logger.info("--conversionSignalElectrique_CoteAltimetrique mesure --");

		Mesure mesureNiveauEau = hashMapCalcul
				.get(TypeMesureOrTrame.NIVEAUDEAU);
		Mesure mesureTemp = null;
		Float accelerationGravitationnelle = 9.80665F;
		Float temperature = 25F;
		Float compenseA25degre = 25F;
		@SuppressWarnings("unused")
		Float compenseA20degre = 20F;

		Float signalBrut = mesureNiveauEau.getSignalBrut();

		Capteur capteur = mesureNiveauEau.getCapteur();
		Float echelleMaxCapteur = capteur.getEchelleMaxCapteur();
		Float echelleMinCapteur = capteur.getEchelleMinCapteur();

		Enregistreur enregistreur = capteur.getEnregistreur();
		Float salinite = enregistreur.getSalinite();
		Float coeffTemperature = enregistreur.getCoeffTemperature();
		Float altitudeDuCapteur = enregistreur.getAltitude();

		if (hashMapCalcul.containsKey(TypeMesureOrTrame.TEMPERATURE)) {
			mesureTemp = this.conversionSignal_Temperature(hashMapCalcul
					.get(TypeMesureOrTrame.TEMPERATURE));
			temperature = mesureTemp.getValeur();
		}

		Float pressionRelativeBrute = null;

		if (enregistreur.getTypeEnregistreur().equals(
				TypeEnregistreur.ANALOGIQUE)) {
			pressionRelativeBrute = (echelleMaxCapteur - echelleMinCapteur / 16)
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

		mesureNiveauEau
				.setValeur(CoteNGFNiveauStatiqueMesureeCompenseDilatation);

		this.create(mesureNiveauEau);

		mesureNiveauEau = this.findById(mesureNiveauEau.getId());

		try {
			checkAlerteUtils.checkAlerte(capteur, mesureNiveauEau);
		} catch (MessagingException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		return mesureNiveauEau;
	}

	@Override
	public Mesure findById(Integer id) throws ServiceException {
		Mesure mesure = new Mesure();
		mesure = mesureDAO.findOne(id);
		return mesure;
	}

	@Override
	@Transactional
	public Mesure findByIdWithFetch(Integer id) throws ServiceException {
		logger.info("--findByIdWithFetch MesureService -- mesureId : " + id);
		Mesure mesure = mesureDAO.findByIdWithFetch(id);
		return mesure;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Mesure create(Mesure mesure) throws ServiceException {
		logger.info("--CREATE MesureService -- mesure : " + mesure);
		return mesureDAO.save(mesure);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Mesure update(Mesure mesure) throws ServiceException {
		logger.info("--UPDATE MesureService --");
		logger.debug("mesure : " + mesure);
		mesure = mesureDAO.saveAndFlush(mesure);
		mesure = this.findByIdWithFetch(mesure.getId());
		return mesure;
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE MesureService -- mesureId : " + id);
		Mesure mesure = this.findById(id);
		Capteur capteur = capteurService.findById(mesure.getCapteur().getId());
		boolean success = capteur.getMesures().remove(mesure);
		logger.debug("remove mesure from enregistreur success ? : " + success);

		mesureDAO.delete(id);
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
	public List<Mesure> findByCapteurIdWithFetch(Integer id)
			throws ServiceException {
		logger.info("--findByCapteurId MesureService -- Id : " + id);
		List<Mesure> mesures;
		try {
			mesures = mesureDAO.findByCapteurIdWithFetch(id);
		} catch (PersistenceException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return mesures;
	}

	@Override
	public List<Mesure> findByCapteurIdBetweenDates(Integer id, Date dateDebut,
			Date dateFin) throws ServiceException {
		logger.info("--findByEnregistreurIdBetweenDates MesureService -- Id : "
				+ id);
		List<Mesure> mesures;
		try {
			mesures = mesureDAO.findByCapteurIdBetweenDates(id, dateDebut,
					dateFin);
		} catch (PersistenceException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return mesures;
	}

	@Override
	public Point transformMesureInPoint(Mesure item) throws ServiceException {

		Point point = new Point();
		try {
			point.setMid(item.getCapteur().getEnregistreur().getMid());
			point.setTypeMesureOrTrameDescription(item.getCapteur()
					.getTypeMesureOrTrame().getDescription());
			point.setDate(item.getDate());
			point.setValeur(item.getValeur());
			point.setUnite(item.getUnite());
		} catch (PersistenceException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return point;
	}

	@Override
	@Transactional
	public void affectNewNiveauManuel(Integer mesureId) throws ServiceException {
		Mesure mesure = this.findById(mesureId);
		mesure.setTypeMesureOrTrame(TypeMesureOrTrame.NIVEAUMANUEL);
		this.update(mesure);
		this.findByIdWithFetch(mesureId);

	}
}
