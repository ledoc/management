package fr.treeptik.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import fr.treeptik.dao.DocumentDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Client;
import fr.treeptik.model.Document;
import fr.treeptik.model.Ouvrage;
import fr.treeptik.service.ClientService;
import fr.treeptik.service.DocumentService;
import fr.treeptik.service.OuvrageService;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Inject
	private DocumentDAO documentDAO;

	@Inject
	OuvrageService ouvrageService;
	@Inject
	ClientService clientService;
	@Inject
	private Environment env;

	private Logger logger = Logger.getLogger(DocumentServiceImpl.class);

	@Override
	public Document findById(Integer id) throws ServiceException {
		return documentDAO.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Document create(Document document) throws ServiceException {
		logger.info("--CREATE document --");
		logger.debug("document : " + document);
		return documentDAO.save(document);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ServiceException.class)
	public Document update(Document document) throws ServiceException {
		logger.info("--UPDATE document --");
		logger.debug("document : " + document);
		return documentDAO.saveAndFlush(document);
	}

	@Override
	@Secured("ADMIN")
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE document --");
		logger.debug("documentId : " + id);
		Document document = this.findById(id);
		Path path = Paths.get(document.getPath());
		try {
			Files.delete(path);
		} catch (IOException e) {
			logger.error("Error DocumentService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		documentDAO.delete(id);
	}

	@Override
	public List<Document> findAll() throws ServiceException {
		logger.info("--FINDALL document --");
		return documentDAO.findAll();
	}

	@Override
	public List<Document> findByClientLogin(String login)
			throws ServiceException {
		logger.info("--findByClientLogin DocumentService -- login : " + login);
		List<Document> documents;
		try {
			documents = documentDAO.findByClientLogin(login);
		} catch (PersistenceException e) {
			logger.error("Error DocumentService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return documents;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void uploadFileAndAssign(MultipartFile file, Integer clientId,
			Integer ouvrageId) throws ServiceException {

		Ouvrage ouvrage;
		Client client;
		File serverFile;
		String fileName = null;
		Document document = new Document();
		try {
			byte[] bytes = file.getBytes();
			ouvrage = ouvrageService.findById(ouvrageId);
			client = clientService.findById(clientId);
			String codeOuvrage = ouvrage.getCodeOuvrage();
			String clientIdentifiant = client.getIdentifiant();

			fileName = file.getOriginalFilename();
			String rootPath = env.getProperty("upload.path");
			File dir = new File(rootPath + File.separator + clientIdentifiant
					+ File.separator + codeOuvrage);

			if (!dir.exists())
				dir.mkdirs();

			serverFile = new File(dir.getAbsolutePath() + File.separator
					+ fileName);
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
		} catch (ServiceException | IOException e) {
			logger.error("Error DocumentService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		logger.info("Emplacement du fichier  : " + serverFile.getAbsolutePath());
		logger.info("Upload réussie du fichier=" + fileName);

		document.setOuvrage(ouvrage);
		document.setClient(client);
		document.setNom(fileName);
		document.setPath(serverFile.getAbsolutePath());
		document.setType(fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length()).toUpperCase());
		document.setDate(new Date());
		document.setTaille((int) serverFile.length() / 1000);

		this.create(document);
	}

	@Override
	public void downloadFile(Document document, HttpServletRequest request,
			HttpServletResponse response) throws ServiceException {

		try {
			File downloadFile = new File(document.getPath());
			FileInputStream inputStream = new FileInputStream(downloadFile);

			String mimeType = request.getServletContext().getMimeType(
					document.getPath());
			logger.debug("File Mime Type : " + mimeType);

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					downloadFile.getName());

			logger.info("headerValue : " + headerValue);
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());
			response.setHeader(headerKey, headerValue);

			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();

		} catch (IOException ex) {
			logger.error("Error writing file to output stream. Filename was  "
					+ document.getNom(), ex);
			throw new RuntimeException("IOError writing file to output stream");
		}
	}
}
