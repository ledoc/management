package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Repas;

public interface RepasDAO extends
		JpaRepository<Repas, Integer> {


}
