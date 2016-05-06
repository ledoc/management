package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Round;

public interface RoundService {

	Round findById(Long id) throws ServiceException;

	Round create(Round round) throws ServiceException;

	Round update(Round round) throws ServiceException;

	List<Round> findAll() throws ServiceException;

	void remove(Long id) throws ServiceException;

}
