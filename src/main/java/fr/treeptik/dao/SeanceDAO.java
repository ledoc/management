package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Seance;

public interface SeanceDAO extends
		JpaRepository<Seance, Integer> {


}
