package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Event;
import it.uniroma3.siw.repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	// Ritorna una lista di tutti gli eventi
	public List<Event> findAll() {
		return (List<Event>) eventRepository.findAll();
	}

	public Event findById(Long id) {
		return eventRepository.findById(id).orElse(null);
	}
}