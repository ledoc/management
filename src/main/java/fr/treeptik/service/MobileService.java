package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.deveryware.Mobile;

public interface MobileService {

		Mobile findById(Integer id) throws ServiceException;
		
		Mobile create(Mobile mobile)
				throws ServiceException;

		Mobile update(Mobile mobile) throws ServiceException;
		
		void remove(Mobile mobile)
				throws ServiceException;

		List<Mobile> findAll() throws ServiceException;

		Mobile findByMid(String mid) throws ServiceException;

		public Mobile findByMidWithJoinFechTrameDWs(String mid) throws ServiceException;
}
