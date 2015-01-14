package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Rapport;

public interface RapportDAO extends JpaRepository<Rapport, Integer> {

}
