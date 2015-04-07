package fr.treeptik.service;


import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Project;

public interface ProjectService {
    Project find(boolean isAdmin, String userLogin)  throws ServiceException;
}
