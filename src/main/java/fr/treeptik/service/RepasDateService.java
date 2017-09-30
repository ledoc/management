package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.BilanRepas;
import fr.treeptik.model.RepasDate;

public interface RepasDateService {

	RepasDate findById(Long id) throws ServiceException;

	RepasDate create(RepasDate repasDate) throws ServiceException;

	RepasDate update(RepasDate repasDate) throws ServiceException;

	List<RepasDate> findAll() throws ServiceException;

	void remove(Long id) throws ServiceException;

	List<BilanRepas> createBilanByDate() throws ServiceException;

	RepasDate findByIdWithListPlat(Long id) throws ServiceException;

	List<RepasDate> findAllWithListPlat() throws ServiceException;

}
