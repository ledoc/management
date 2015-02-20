package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Enregistreur;

public interface EnregistreurService {

		Enregistreur findById(Integer id) throws ServiceException;
		
		Enregistreur create(Enregistreur enregistreur)
				throws ServiceException;

		Enregistreur update(Enregistreur enregistreur) throws ServiceException;
		
		List<Enregistreur> findAll() throws ServiceException;

		Enregistreur findByMid(String mid) throws ServiceException;

		public Enregistreur findByMidWithJoinFetchTrameDWs(String mid) throws ServiceException;

		Enregistreur findByIdWithJoinFetchAlertesActives(Integer id)
				throws ServiceException;

		void remove(Integer id) throws ServiceException;

		List<Enregistreur> findFreeEnregistreurs() throws ServiceException;

		List<Enregistreur> findByClientLogin(String userLogin)
				throws ServiceException;

}
