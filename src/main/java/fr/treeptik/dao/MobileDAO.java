package fr.treeptik.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.deveryware.Mobile;

public interface MobileDAO extends JpaRepository<Mobile, Integer> {

	Mobile findByMid(String mid) throws DataAccessException;
}
