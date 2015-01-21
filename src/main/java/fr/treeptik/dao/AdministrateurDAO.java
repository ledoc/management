package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Administrateur;

public interface AdministrateurDAO extends JpaRepository<Administrateur, Integer> {

}
