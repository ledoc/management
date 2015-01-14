package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.User;

public interface UserService {

		User findById(Integer id) throws ServiceException;
		
		User create(User user)
				throws ServiceException;

		User update(User user) throws ServiceException;
		
		User remove(User user)
				throws ServiceException;

		List<User> findAll() throws ServiceException;

}
