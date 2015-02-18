package fr.treeptik.service;

import fr.treeptik.exception.ServiceException;

public interface DeverywareService {

	public String getHistory(String mid) throws ServiceException;
	
	public String waitForMessage() throws ServiceException;
}
