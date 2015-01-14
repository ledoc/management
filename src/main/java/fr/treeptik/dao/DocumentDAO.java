package fr.treeptik.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.treeptik.model.Document;

public interface DocumentDAO extends JpaRepository<Document, Integer> {

}
