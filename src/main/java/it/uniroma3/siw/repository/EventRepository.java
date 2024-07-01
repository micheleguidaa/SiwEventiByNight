package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Event;

public interface EventRepository extends CrudRepository<Event, Long>{
	List<Event> findByNameStartingWithIgnoreCase(String name);
	
}