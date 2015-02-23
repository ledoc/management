package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Mesure;
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

//	// Ns0 = Nm0
//	// Nm0 : mesure manuelle initiale
//	// Nsi = Nsi-1 + (hauteurEau i - hauteurEau i-1)
	float conversionHauteurEau_CoteAltimetrique(TrameDW trameDW)
			throws ServiceException;

	TrameDW conversionSignalElectrique_CoteAltimetrique(TrameDW trameDW)
			throws ServiceException;

}
