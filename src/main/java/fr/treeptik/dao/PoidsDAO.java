package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Poids;

public interface PoidsDAO extends
		JpaRepository<Poids, Long> {


}
