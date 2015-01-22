package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Mesure;

public interface MesureService {

	Mesure findById(Integer id) throws ServiceException;

	Mesure create(Mesure mesure) throws ServiceException;

	Mesure update(Mesure mesure) throws ServiceException;

	void remove(Mesure mesure) throws ServiceException;

	List<Mesure> findAll() throws ServiceException;

	// - profMax : profondeur maximale pour laquel l'enregistreur a été étalonné
	// (en mètre)
	// - intensite : valeur brute transmise par le capteur à un instant t (mA) ;
	float conversionSignalElectrique_HauteurEau(float intensite, float profMax)
			throws ServiceException;

	// Ns0 = Nm0
	// Nm0 : mesure manuelle initiale
	// Nsi = Nsi-1 + (hauteurEau i - hauteurEau i-1)
	float conversionHauteurEau_CoteAltimetrique(float hauteurEau,
			float dernier_calcul_Niveau_Eau, float derniere_HauteurEau)
			throws ServiceException;

	void remove(Integer id) throws ServiceException;
}
