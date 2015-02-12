package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.TypeOuvrage;

public interface TypeOuvrageService {

	TypeOuvrage create(TypeOuvrage typeOuvrage) throws ServiceException;

	TypeOuvrage update(TypeOuvrage typeOuvrage) throws ServiceException;

	void remove(Integer id) throws ServiceException;

	List<TypeOuvrage> findAll() throws ServiceException;

	TypeOuvrage findById(Integer id) throws ServiceException;

	TypeOuvrage findByNom(String nom) throws ServiceException;

}
