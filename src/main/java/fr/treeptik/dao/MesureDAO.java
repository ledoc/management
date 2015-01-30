package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Mesure;

public interface MesureDAO extends JpaRepository<Mesure, Integer> {
	
}
