package fr.treeptik.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.TypeCaptAlerteMesure;

public interface TypeCaptAlerteMesureDAO extends
		JpaRepository<TypeCaptAlerteMesure, Integer> {

	TypeCaptAlerteMesure findByNom(String nom) throws DataAccessException;
}
