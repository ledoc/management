package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.DocumentDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Document;
import fr.treeptik.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Inject
	private DocumentDAO documentDAO;

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
	@Transactional(rollbackFor = ServiceException.class)
	public Document update(Document document) throws ServiceException {
		logger.info("--UPDATE document --");
		logger.debug("document : " + document);
		return documentDAO.saveAndFlush(document);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Document document) throws ServiceException {
		logger.info("--DELETE document --");
		logger.debug("document : " + document);
		documentDAO.delete(document);
	}
	
	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE document --");
		logger.debug("documentId : " + id);
		documentDAO.delete(id);
	}
	

	@Override
	public List<Document> findAll() throws ServiceException {
		logger.info("--FINDALL document --");
		return documentDAO.findAll();
	}

}
