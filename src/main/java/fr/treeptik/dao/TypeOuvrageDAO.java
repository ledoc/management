package fr.treeptik.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.TypeOuvrage;

public interface TypeOuvrageDAO extends JpaRepository<TypeOuvrage, Integer> {

	TypeOuvrage findByNom(String nom) throws DataAccessException;
}
