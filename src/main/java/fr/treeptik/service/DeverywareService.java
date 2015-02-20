package fr.treeptik.service;

import fr.treeptik.exception.ServiceException;

public interface DeverywareService {

	public void getHistory() throws ServiceException;
	
	public String waitForMessage() throws ServiceException;
}
