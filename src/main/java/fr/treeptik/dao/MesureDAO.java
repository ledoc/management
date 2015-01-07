package fr.treeptik.dao;

import fr.treeptik.model.Mesure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesureDAO extends JpaRepository<Mesure, Integer> {
	
}
