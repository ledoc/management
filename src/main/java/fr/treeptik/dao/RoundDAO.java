package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Round;

public interface RoundDAO extends
		JpaRepository<Round, Long> {


}
