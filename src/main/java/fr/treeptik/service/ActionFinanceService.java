package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.ActionFinance;

public interface ActionFinanceService {

	ActionFinance findById(Integer id) throws ServiceException;

	ActionFinance create(ActionFinance actionFinance) throws ServiceException;

	ActionFinance update(ActionFinance actionFinance) throws ServiceException;

	List<ActionFinance> findAll() throws ServiceException;

	void remove(Integer id) throws ServiceException;

}
