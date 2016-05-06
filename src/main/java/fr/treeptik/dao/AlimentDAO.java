package fr.treeptik.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.treeptik.model.Aliment;
import fr.treeptik.model.Finance;

public interface AlimentDAO extends
		JpaRepository<Aliment, Long> {

}
