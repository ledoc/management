package fr.treeptik.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Document;

public interface DocumentService {

		Document findById(Integer id) throws ServiceException;
		
		Document create(Document document)
				throws ServiceException;

		Document update(Document document) throws ServiceException;
		
		void remove(Document document)
				throws ServiceException;

		List<Document> findAll() throws ServiceException;

		void remove(Integer id) throws ServiceException;

		void uploadFileAndAssign(MultipartFile file, Integer clientId,
				Integer ouvrageId) throws ServiceException;


		void downloadFile(Document document, HttpServletRequest request,
				HttpServletResponse response) throws ServiceException;

		List<Document> findByClientLogin(String login) throws ServiceException;

}
