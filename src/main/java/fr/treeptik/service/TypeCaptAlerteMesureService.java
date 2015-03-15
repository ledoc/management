package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.TypeCaptAlerteMesure;

public interface TypeCaptAlerteMesureService {

	TypeCaptAlerteMesure findById(Integer id) throws ServiceException;

	TypeCaptAlerteMesure create(TypeCaptAlerteMesure typeCaptAlerteMesure)
			throws ServiceException;

	TypeCaptAlerteMesure update(TypeCaptAlerteMesure typeCaptAlerteMesure)
			throws ServiceException;

	void remove(Integer id) throws ServiceException;

	List<TypeCaptAlerteMesure> findAll() throws ServiceException;

	TypeCaptAlerteMesure findByNom(String nom) throws ServiceException;

}
