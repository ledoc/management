package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Client;

public interface ClientDAO extends JpaRepository<Client, Integer> {

}
