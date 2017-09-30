package fr.treeptik.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import fr.treeptik.dao.AlimentDAO;
import fr.treeptik.dao.PlatDAO;
import fr.treeptik.dao.RepasDateDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Aliment;
import fr.treeptik.model.Plat;
import fr.treeptik.model.RepasDate;
import fr.treeptik.util.CSVUtils;

@Component
public class ExportCSV {

	@Inject
	private AlimentDAO alimentDAO;
	@Inject
	private PlatDAO platDAO;
	@Inject
	private RepasDateDAO repasDateDAO;

	
	@Inject
	private CSVUtils csvUtils;

	
	public String exportAlimentCSV() throws ServiceException {

		String report = null;
		try {

			List<Aliment> listAliment = alimentDAO.findAll();
			if (!listAliment.isEmpty()) {
				List<String> listTitle = csvUtils.doAlimentHeaderCSV();
				String headersCSV = String.join(";", listTitle);
				StringBuilder builder = new StringBuilder(headersCSV);
				builder.append("\n");
				report = csvUtils.doAlimentCSV(listAliment, builder);
			}
		} catch (DataAccessException e) {
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		return report;
	}
	
	public String exportPlatCSV() throws ServiceException {

		String report = null;
		try {

			List<Plat> listPlat = platDAO.findAll();
			if (!listPlat.isEmpty()) {
				List<String> listTitle = csvUtils.doPlatHeaderCSV();
				String headersCSV = String.join(";", listTitle);
				StringBuilder builder = new StringBuilder(headersCSV);
				builder.append("\n");
				report = csvUtils.doPlatCSV(listPlat, builder);
			}
		} catch (DataAccessException e) {
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		return report;
	}
	
	public String exportRepasCSV() throws ServiceException {

		String report = null;
		try {

			List<RepasDate> listRepasDate = repasDateDAO.findAllWithListPlat();
			if (!listRepasDate.isEmpty()) {
				List<String> listTitle = csvUtils.doAlimentHeaderCSV();
				String headersCSV = String.join(";", listTitle);
				StringBuilder builder = new StringBuilder(headersCSV);
				builder.append("\n");
				report = csvUtils.doRepasDateCSV(listRepasDate, builder);
			}
		} catch (DataAccessException e) {
			throw new ServiceException(e.getLocalizedMessage(), e);
		}

		return report;
	}
	

}
