package fr.treeptik.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
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
	@Transactional(rollbackFor = ServiceException.class)
	public Mesure findById(Integer id) throws ServiceException {
		return mesureDAO.findOne(id);
	}

	@Override
	public Mesure create(Mesure mesure) throws ServiceException {
		logger.info("--CREATE mesure --");
		logger.debug("mesure : " + mesure);
		return mesureDAO.save(mesure);
	}

	@Override
	public Mesure update(Mesure mesure) throws ServiceException {
		logger.info("--UPDATE mesure --");
		logger.debug("mesure : " + mesure);
		return mesureDAO.saveAndFlush(mesure);
	}

	@Override
	public void remove(Mesure mesure) throws ServiceException {
		logger.info("--DELETE mesure --");
		logger.debug("mesure : " + mesure);
		mesureDAO.delete(mesure);
	}
	
	@Override
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE mesure --");
		logger.debug("mesureId : " + id);
		mesureDAO.delete(id);
	}

	@Override
	public List<Mesure> findAll() throws ServiceException {
		logger.info("--FINDALL mesure --");
		return mesureDAO.findAll();
	}

	// - profMax : profondeur maximale pour laquel l'enregistreur a été étalonné
	// (en mètre)
	// - intensite : valeur brute transmise par le capteur à un instant t (mA) ;
	@Override
	public float conversionSignalElectrique_HauteurEau(float intensite, float profMax) throws ServiceException {
		logger.info("--conversionSignalElectrique_HauteurEau mesure --");

		// hauteur d’eau au-dessus de l’enregistreur à un instant t (en mètre)
		float hauteurEau;
		hauteurEau = (profMax / 16) * (intensite - 4);
		
		Mesure mesure = new Mesure();
		mesure.setDate(new Date());
		mesure.setType(TypeMesure.NIVEAUDEAU);
		mesure.setValeur(hauteurEau);
		return hauteurEau;
	}

	// Ns0 = Nm0
	// Nm0 : mesure manuelle initiale
	// Nsi = Nsi-1 + (hauteurEau i - hauteurEau i-1)
	@Override
	public float conversionHauteurEau_CoteAltimetrique(float hauteurEau, float dernier_calcul_Niveau_Eau,
			float derniere_HauteurEau) {
		logger.info("--conversionHauteurEau_CoteAltimetrique mesure --");

		// hauteur d’eau au-dessus de l’enregistreur à un instant t (en mètre)
		float niveauEau;
		niveauEau = dernier_calcul_Niveau_Eau + (hauteurEau - derniere_HauteurEau);

		return niveauEau;
	}


	
	
	
}
