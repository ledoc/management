package fr.treeptik.dao;

import fr.treeptik.model.Mesure;
import fr.treeptik.model.deveryware.Mobile;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MesureDAO extends JpaRepository<Mesure, Integer> {
	
}
