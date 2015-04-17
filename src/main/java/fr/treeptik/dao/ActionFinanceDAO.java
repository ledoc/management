package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.ActionFinance;

public interface ActionFinanceDAO extends
		JpaRepository<ActionFinance, Integer> {


}
