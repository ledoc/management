package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Enregistreur;

public interface EnregistreurDAO extends JpaRepository<Enregistreur, Integer> {

	
}
