package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Event;

public interface EventRepository extends CrudRepository<Event, Long>{
	
}