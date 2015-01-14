package fr.treeptik.service;

import java.util.List;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Document;

public interface DocumentService {

		Document findById(Integer id) throws ServiceException;
		
		Document create(Document document)
				throws ServiceException;

		Document update(Document document) throws ServiceException;
		
		Document remove(Document document)
				throws ServiceException;

		List<Document> findAll() throws ServiceException;

}
