package fr.treeptik.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.treeptik.dao.FinanceDAO;
import fr.treeptik.dto.PointCamenbertDTO;
import fr.treeptik.dto.PointGraphDTO;
import fr.treeptik.exception.ServiceException;
import fr.treeptik.model.BilanFinance;
import fr.treeptik.model.Finance;
import fr.treeptik.model.TypePayment;
import fr.treeptik.service.FinanceService;
import fr.treeptik.util.DateFinanceComparator;
import fr.treeptik.util.Utils;

@Service
public class FinanceServiceImpl implements FinanceService {

	@Inject
	private FinanceDAO financeDAO;

	private Logger logger = Logger.getLogger(FinanceServiceImpl.class);

	@Override
	public Finance findById(Long id) throws ServiceException {
		return financeDAO.findOne(id);
	}

	@Override
	public List<String> listAllCategories() throws ServiceException {
		return financeDAO.listAllCategories();

	}

	@Override
	public List<Finance> listAllByCategorie(String categorie)
			throws ServiceException {
		return financeDAO.listAllByCategorie(categorie);
	}

	@Override
	public List<BilanFinance> listAllBilans() throws ServiceException {
		List<BilanFinance> listBilans = new ArrayList<>();
		List<String> listOfCategories = listAllCategories();
		for (String categorieTemp : listOfCategories) {
			Double sumByCategorie = sumByCategorie(categorieTemp);
			if (sumByCategorie != null) {
				listBilans.add(new BilanFinance(categorieTemp, sumByCategorie));
			}
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
		List<Finance> findBetweenDates = financeDAO.findBetweenDates(dateDebut,
				dateFin);
		findBetweenDates = findBetweenDates
				.stream()
				.filter(f -> f.getMontant() < 0
						&& f.getTypePayment() != TypePayment.LIQUIDE)
				.collect(Collectors.toList());
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

		findBetweenDates = findBetweenDates
				.stream()
				.filter(f -> f.getMontant() < 0
						&& f.getTypePayment() != TypePayment.LIQUIDE)
				.collect(Collectors.toList());
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
		long days = TimeUnit.DAYS.convert(
				(dateFin.getTime() - dateDebut.getTime()),
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
		long days = TimeUnit.DAYS.convert(
				(now.getTime() - dateDebut.getTime()), TimeUnit.MILLISECONDS);
		Double avg = countMonthSum / days;
		avg = Utils.round(avg, 2);
		return avg;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Finance create(Finance finance) throws ServiceException {
		logger.info("--CREATE FinanceService --");
		logger.debug("Finance : " + finance);
		finance = handleTypePayment(finance);
		finance = roundMontant(finance);
		Double lastTotal = selectLastTotal();
		if (finance.getTypePayment() != TypePayment.LIQUIDE) {
			Double newTotal = lastTotal + finance.getMontant();
			finance.setTotal(newTotal);
		}
		return financeDAO.save(finance);
	}

	private Finance handleTypePayment(Finance finance) {
		if (finance.getTypePayment() == TypePayment.SALAIRE
				|| finance.getTypePayment() == TypePayment.REMBOURSEMENT) {
			finance.setRevenu(true);
		} else {
			finance.setMontant(-(finance.getMontant()));
			finance.setRevenu(false);
		}
		return finance;
	}

	private Finance roundMontant(Finance finance) {
		Double montant = Utils.round(finance.getMontant(), 2);
		finance.setMontant(montant);

		return finance;
	}
	
	
	private Finance roundTotal(Finance finance) {
		Double total = Utils.round(finance.getTotal(), 2);
		finance.setTotal(total);

		return finance;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Finance update(Finance finance) throws ServiceException {
		logger.info("--UPDATE FinanceService --");
		logger.debug("Finance : " + finance);
		try {
			finance = handleTypePayment(finance);
			Finance financeInDB = financeDAO.findOne(finance.getId());
			Double montant = finance.getMontant();
			Double montantInDB = financeInDB.getMontant();
			if (montant != montantInDB) {
				Double totalInDB = financeInDB.getTotal();

				Double delta = montant - montantInDB;
				finance.setTotal(totalInDB + delta);

				updateTotal(finance, delta);

			}
			finance = roundMontant(finance);
			finance = financeDAO.save(finance);
		} catch (PersistenceException e) {
			logger.error("Error FinanceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return finance;
	}

	private List<Finance> retrieveFinanceSinceDate(Date date)
			throws ServiceException {
		logger.info("--retrieveFinanceSinceDate FinanceService -- date = "
				+ date);
		try {
			return financeDAO.findBetweenDates(date, new Date());
		} catch (PersistenceException e) {
			logger.error("Error FinanceService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
	}

	private void updateTotal(Finance finance, Double delta)
			throws ServiceException {
		List<Finance> listFinances = retrieveFinanceSinceDate(finance.getDate());

		for (Finance finance2 : listFinances) {
			if (finance.getId() < finance2.getId()) {
				finance2.setTotal(finance.getMontant() + delta);
			}

		}
		financeDAO.save(listFinances);

	}

	@Override
	public void reaffectAllTotal() throws ServiceException {
		List<Finance> allFinance = findAll();

		Optional<Finance> resetOptional = allFinance.stream().findAny().filter(f -> f.getCategorie().equals("reset"));

		Collections.sort(allFinance, new DateFinanceComparator());
		Finance reset = resetOptional.get();
		Double total = reset.getTotal();
		allFinance.remove(reset);
		for (Finance finance : allFinance) {
			if (finance.getTypePayment() != TypePayment.LIQUIDE) {
				Double montant = finance.getMontant();
				finance.setTotal(total + montant);
				finance = roundTotal(finance);
				total = finance.getTotal();
			}
		}
		financeDAO.save(allFinance);
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public void remove(Long id) throws ServiceException {
		logger.info("--DELETE FinanceService -- financeId : " + id);
		try {
			Finance finance = findById(id);
			updateTotal(finance, -(finance.getMontant()));
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
	public PointGraphDTO transformFinanceInPoint(Finance item)
			throws ServiceException {

		PointGraphDTO point = new PointGraphDTO();
		try {
			point.setDate(item.getDate());
			point.setValeur(item.getTotal());
		} catch (PersistenceException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return point;
	}

	@Override
	public PointCamenbertDTO transformBilanInCamenbertPoint(BilanFinance item)
			throws ServiceException {
		if (item.getSomme() != null) {

			PointCamenbertDTO point = new PointCamenbertDTO();
			try {
				point.setCategorie(item.getCategorie());
				point.setValeur(Math.abs(item.getSomme()));
			} catch (PersistenceException e) {
				logger.error("Error MesureService : " + e);
				throw new ServiceException(e.getLocalizedMessage(), e);
			}
			return point;
		}
		return null;
	}

	@Override
	public PointGraphDTO transformBilanInGraphPoint(BilanFinance item)
			throws ServiceException {

		PointGraphDTO point = new PointGraphDTO();
		try {
			point.setCategorie(item.getCategorie());
			point.setValeur(item.getSomme());
		} catch (PersistenceException e) {
			logger.error("Error MesureService : " + e);
			throw new ServiceException(e.getLocalizedMessage(), e);
		}
		return point;
	}

	@Override
	public Double selectLastTotal() throws ServiceException {
		logger.info("--selectLastTotal FinanceService --");
		Double selectLastTotal = financeDAO.selectLastTotal();
		if (selectLastTotal == null) {
			selectLastTotal = 0D;
		}
		return selectLastTotal;
	}

}
