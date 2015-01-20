package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Ouvrage;

public interface OuvrageDAO extends JpaRepository<Ouvrage, Integer> {

}
