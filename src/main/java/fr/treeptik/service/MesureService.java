package fr.treeptik.service;

import java.util.Date;
import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Mesure;
import fr.treeptik.model.Point;
import fr.treeptik.model.TrameDW;

public interface MesureService {

	Mesure findById(Integer id) throws ServiceException;

	Mesure create(Mesure mesure) throws ServiceException;

	Mesure update(Mesure mesure) throws ServiceException;

	List<Mesure> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

	List<Mesure> findByEnregistreurId(Integer id) throws ServiceException;

	// - profMax : profondeur maximale pour laquel l'enregistreur a été étalonné
	// (en mètre)
	// - intensite : valeur brute transmise par le capteur à un instant t (mA) ;
	TrameDW conversionSignalElectrique_Conductivite(TrameDW trameDW)
			throws ServiceException;

	TrameDW conversionSignalElectrique_CoteAltimetrique(TrameDW trameDW)
			throws ServiceException;

	List<Mesure> findAllDetails() throws ServiceException;

	Point transformMesureInPoint(Mesure item) throws ServiceException;

	List<Mesure> findByEnregistreurIdBetweenDates(Integer id, Date dateDebut,
			Date dateFin) throws ServiceException;

}
