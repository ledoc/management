package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Exercice;

public interface ExerciceDAO extends
		JpaRepository<Exercice, Long> {


}
