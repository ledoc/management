package fr.treeptik.service;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.model.Site;

import java.util.List;

public interface OuvrageService {

    Ouvrage findById(Integer id) throws ServiceException;

    Ouvrage create(Ouvrage ouvrage)
            throws ServiceException;

    Ouvrage update(Ouvrage ouvrage) throws ServiceException;

    List<Ouvrage> findAll() throws ServiceException;

    void remove(Integer id) throws ServiceException;

    Ouvrage findByIdWithJoinFetchEnregistreurs(Integer id) throws ServiceException;

    List<Ouvrage> findByClientLogin(String userLogin) throws ServiceException;

    List<Ouvrage> findByClientId(Integer userId) throws ServiceException;
}
