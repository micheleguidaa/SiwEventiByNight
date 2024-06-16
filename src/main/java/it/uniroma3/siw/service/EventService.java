package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Event;
import it.uniroma3.siw.repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private FileService fileService;

	private static final String UPLOADED_FOLDER = "uploads/events/";
	private static final String DEFAULT_IMAGE = "/images/default/senzaFoto.jpeg";

	// Ritorna una lista di tutti gli eventi
	public List<Event> findAll() {
		return (List<Event>) eventRepository.findAll();
	}

	public Event getEvent(Long eventId) {
		return eventRepository.findById(eventId).orElse(null);
	}

	public void deleteById(Long id) {
		eventRepository.deleteById(id);
	}

	public void saveEvent(Event event, MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			event.setUrlImage(DEFAULT_IMAGE);
		} else {
			String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
			event.setUrlImage(fileUrl);
		}
		eventRepository.save(event);
	}

	public void updateEvent(Long id, Event eventDetails, MultipartFile file) throws IOException {
		Event event = this.getEvent(id);
		if (event != null) {
			event.setName(eventDetails.getName());
			event.setTheme(eventDetails.getTheme());
			event.setStartDateTime(eventDetails.getStartDateTime());
			event.setEndDateTime(eventDetails.getEndDateTime());
			event.setCost(eventDetails.getCost());
			event.setnMaxParticipants(eventDetails.getnMaxParticipants());
			if (!file.isEmpty()) {
				String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
				event.setUrlImage(fileUrl);
			}
			eventRepository.save(event);
		}
	}
}
