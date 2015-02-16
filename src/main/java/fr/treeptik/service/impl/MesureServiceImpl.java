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
import fr.treeptik.model.Mesure;
import fr.treeptik.model.TypeMesure;
import fr.treeptik.service.MesureService;

@Service
public class MesureServiceImpl implements MesureService {

	@Inject
	private MesureDAO mesureDAO;

	private Logger logger = Logger.getLogger(MesureServiceImpl.class);

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
		logger.info("--DELETE MesureService --");
		logger.debug("mesureId : " + id);
		mesureDAO.delete(id);
	}

	@Override
	public List<Mesure> findAll() throws ServiceException {
		logger.info("--FINDALL MesureService --");
		return mesureDAO.findAll();
	}
	
	@Override
	public List<Mesure> findByEnregistreurId(Integer id)
			throws ServiceException {
		logger.info("--findByEnregistreurId MesureService by Id--");
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
	public List<Mesure> findByOuvrageId(Integer id)
			throws ServiceException {
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
	

	// - profMax : profondeur maximale pour laquel l'enregistreur a été étalonné
	// (en mètre)
	// - intensite : valeur brute transmise par le capteur à un instant t (mA) ;
	@Override
	public float conversionSignalElectrique_HauteurEau(float intensite,
			float profMax) throws ServiceException {
		logger.info("--conversionSignalElectrique_HauteurEau mesure --");

		// hauteur d’eau au-dessus de l’enregistreur à un instant t (en mètre)
		float hauteurEau;
		hauteurEau = (profMax / 16) * (intensite - 4);

		Mesure mesure = new Mesure();
		mesure.setDate(new Date());
		mesure.setTypeMesure(TypeMesure.NIVEAUDEAU);
		mesure.setValeur(hauteurEau);
		return hauteurEau;
	}

	// Ns0 = Nm0
	// Nm0 : mesure manuelle initiale
	// Nsi = Nsi-1 + (hauteurEau i - hauteurEau i-1)
	@Override
	public float conversionHauteurEau_CoteAltimetrique(float hauteurEau,
			float dernier_calcul_Niveau_Eau, float derniere_HauteurEau) {
		logger.info("--conversionHauteurEau_CoteAltimetrique mesure --");

		// hauteur d’eau au-dessus de l’enregistreur à un instant t (en mètre)
		float niveauEau;
		niveauEau = dernier_calcul_Niveau_Eau
				+ (hauteurEau - derniere_HauteurEau);

		return niveauEau;
	}

}
