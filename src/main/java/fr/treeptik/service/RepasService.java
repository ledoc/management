package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Plat;
import fr.treeptik.model.Repas;

public interface RepasService {

	Repas findById(Long id) throws ServiceException;

	Repas create(Repas repas) throws ServiceException;

	Repas update(Repas repas) throws ServiceException;

	List<Repas> findAll() throws ServiceException;

	void remove(Long id) throws ServiceException;


	Repas findByIdWithListPlat(Long id) throws ServiceException;

	List<Repas> findAllWithListPlat() throws ServiceException;

	Boolean checkRepasExist(List<Plat> listPlats ) throws ServiceException;
}
