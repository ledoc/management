package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Exercice;

public interface ExerciceService {

	Exercice findById(Integer id) throws ServiceException;

	Exercice create(Exercice exercice) throws ServiceException;

	Exercice update(Exercice exercice) throws ServiceException;

	List<Exercice> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

}
