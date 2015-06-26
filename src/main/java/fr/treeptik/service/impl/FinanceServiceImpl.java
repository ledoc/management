package fr.treeptik.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.FinanceDAO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.Bilan;
import fr.treeptik.model.Finance;
import fr.treeptik.model.PointGraphDTO;
import fr.treeptik.service.FinanceService;
import fr.treeptik.util.Utils;

@Service
public class FinanceServiceImpl implements FinanceService {

	@Inject
	private FinanceDAO financeDAO;

	private Logger logger = Logger.getLogger(FinanceServiceImpl.class);

	@Override
	public Finance findById(Integer id) throws ServiceException {
		return financeDAO.findOne(id);
	}

	@Override
	public List<String> listAllCategories() throws ServiceException {
		return financeDAO.listAllCategories();
		
	}
	@Override
	public List<Finance> listAllByCategorie(String categorie) throws ServiceException {
		return financeDAO.listAllByCategorie(categorie);
	}
	
	@Override
	public List<Bilan> listAllBilans() throws ServiceException {
		List<Bilan> listBilans = new ArrayList<>();
		List<String> listOfCategories = listAllCategories();
		for (String categorieTemp : listOfCategories) {
			listBilans.add(new Bilan(categorieTemp, sumByCategorie(categorieTemp)));
		}
		
		return listBilans;
	}
	
	@Override
	public Double sumByCategorie(String categorie) throws ServiceException {
		return financeDAO.sumByCategorie(categorie);
	}
	
	@Override
	public Double countBeforeMonthSum() throws ServiceException {
		Double sum = 0.0D;

		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, -1);
		Date dateFin = calendar.getTime();
		calendar.add(Calendar.MONTH, -1);
		Date dateDebut = calendar.getTime();
		System.out.println(dateDebut);
		System.out.println(now);
		List<Finance> findBetweenDates = financeDAO.findBetweenDates(dateDebut,
				dateFin);
		findBetweenDates.forEach(f -> System.out.println(f.getMontant()));
		sum = findBetweenDates.stream().mapToDouble(f -> f.getMontant()).sum();
		sum = Utils.round(sum, 2);
		
		return sum;
	}
	
	@Override
	public Double countMonthSum() throws ServiceException {
		Double sum = 0.0D;

		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, -1);
		Date dateDebut = calendar.getTime();
		
		List<Finance> findBetweenDates = financeDAO.findBetweenDates(dateDebut,
				now);
		findBetweenDates.forEach(f -> System.out.println(f.getMontant()));
		sum = findBetweenDates.stream().mapToDouble(f -> f.getMontant()).sum();
		
		sum = Utils.round(sum, 2);
		return sum;
	}

	@Override
	public Double countBeforeMonthAverage() throws ServiceException {
		Double countSum = countBeforeMonthSum();
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, -1);
		Date dateFin = calendar.getTime();
		calendar.add(Calendar.MONTH, -1);
		Date dateDebut = calendar.getTime();
		long days = TimeUnit.DAYS.convert((dateFin.getTime() - dateDebut.getTime()),
				TimeUnit.MILLISECONDS);
		Double avg = countSum / days;
		
		avg = Utils.round(avg, 2);
		return avg;
	}
	
	@Override
	public Double countMonthAverage() throws ServiceException {
		Double countMonthSum = countMonthSum();
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, -1);
		Date dateDebut = calendar.getTime();
		long days = TimeUnit.DAYS.convert((now.getTime() - dateDebut.getTime()),
				TimeUnit.MILLISECONDS);
		Double avg = countMonthSum / days;
		avg = Utils.round(avg, 2);
		return avg;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Finance create(Finance Finance) throws ServiceException {
		logger.info("--CREATE FinanceService --");
		logger.debug("Finance : " + Finance);
		return financeDAO.save(Finance);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Finance update(Finance Finance) throws ServiceException {
		logger.info("--UPDATE FinanceService --");
		logger.debug("Finance : " + Finance);
		try {

			Finance = financeDAO.save(Finance);
		} catch (PersistenceException e) {
			logger.error("Error FinanceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return Finance;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Integer id) throws ServiceException {
		logger.info("--DELETE FinanceService -- financeId : " + id);
		try {
			financeDAO.delete(id);
		} catch (PersistenceException e) {
			logger.error("Error FinanceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public List<Finance> findAll() throws ServiceException {
		logger.info("--FINDALL FinanceService --");
		List<Finance> listFinance;
		try {
			listFinance = financeDAO.findAll();
		} catch (PersistenceException e) {
			logger.error("Error FinanceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return listFinance;
	}

	@Override
	public PointGraphDTO transformFinanceInPoint(Finance item) throws ServiceException {

		
        PointGraphDTO point = new PointGraphDTO();
		try {
			point.setDate(item.getDate());
			point.setValeur(item.getMontant());
		} catch (PersistenceException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return point;
	}
	
}
