package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Site;

public interface SiteService {

		Site findById(Integer id) throws ServiceException;
		
		Site create(Site site)
				throws ServiceException;

		Site update(Site site) throws ServiceException;

		List<Site> findAll() throws ServiceException;

		List<Site> findFreeSites() throws ServiceException;
		
		void remove(Integer id) throws ServiceException;

		Site findByIdWithJoinFetchOuvrages(Integer id) throws ServiceException;

		Site setCodeWithCheck(Site site) throws ServiceException;

		List<Site> findByClientLogin(String userLogin) throws ServiceException;
}
