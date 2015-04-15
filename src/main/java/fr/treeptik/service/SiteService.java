package fr.treeptik.service;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Site;

import java.util.List;

public interface SiteService {

    Site findById(Integer id) throws ServiceException;

    Site create(Site site)
            throws ServiceException;

    Site update(Site site) throws ServiceException;

    List<Site> findAll() throws ServiceException;

    void remove(Integer id) throws ServiceException;

    Site findByIdWithJoinFetchOuvrages(Integer id) throws ServiceException;

    List<Site> findByClientLogin(String userLogin) throws ServiceException;
}
