package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Event;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.EventService;
import it.uniroma3.siw.service.LocalService;
import it.uniroma3.siw.service.ReservationService;
import jakarta.validation.Valid;

import java.io.IOException;

@Controller
public class EventController {
	@Autowired
	private EventService eventService;
	
	@Autowired
	private LocalService localService;
	
	@Autowired
	private ReservationService reservationService;

	// Mostra la pagina con l'elenco di tutti gli eventi
	@GetMapping("/events")
	public String showEvents(Model model) {
		model.addAttribute("events", eventService.findAll());
		return "events";
	}

	@GetMapping("/event/{id}")
	public String getEvent(@PathVariable("id") Long eventId, @RequestParam(value = "CurrentUser", required = false)  User currentUser, Model model) {
		model.addAttribute("event", eventService.getEvent(eventId));
		model.addAttribute("isReserved",(currentUser!=null)&&(reservationService.isUserReservedForEvent(currentUser.getId(),eventId)));
		return "event";
	}

	// Mostra la pagina con l'elenco di tutti gli eventi per l'amministratore
	@GetMapping("/admin/events")
	public String showAdminEvents(Model model) {
		model.addAttribute("events", eventService.findAll());
		return "Admin/indexEventsAdmin";
	}

	// Mostra la pagina per creare un nuovo evento
	@GetMapping("/admin/add/event")
	public String addEventForm(Model model) {
	    model.addAttribute("event", new Event());
	    model.addAttribute("locals", localService.findAllSortedByName());
	    return "Admin/FormAddEvent";
	}

	// Gestisce l'inserimento di un nuovo evento
	@PostMapping("/admin/add/event")
	public String addEvent(@Valid @ModelAttribute("event") Event event,
	                       BindingResult eventBindingResult,
	                       @RequestParam("fileImage") MultipartFile file,
	                       Model model) throws IOException {
	    if (!eventBindingResult.hasErrors()) {
	        eventService.saveEvent(event, file);
	        return "redirect:/admin/events";
	    }
	    model.addAttribute("locals", localService.findAllSortedByName());
	    return "Admin/FormAddEvent";
	}


	// Mostra la pagina per modificare un evento esistente
		@GetMapping("/admin/edit/event/{id}")
		public String editEventForm(@PathVariable("id") Long id, Model model) {
			Event event = eventService.getEvent(id);
			model.addAttribute("event", event);
			model.addAttribute("locals", localService.findAllSortedByName());
			return "Admin/FormEditEvent";
		}

		// Gestisce la modifica di un evento esistente
		@PostMapping("/admin/edit/event/{id}")
		public String editEvent(@PathVariable("id") Long id, 
		                        @Valid @ModelAttribute("event") Event eventDetails, 
		                        BindingResult eventBindingResult,
		                        @RequestParam("fileImage") MultipartFile file,
		                        Model model) throws IOException {
		    if (!eventBindingResult.hasErrors()) {
		        eventService.updateEvent(id, eventDetails, file);
		        return "redirect:/admin/events";
		    }
		    model.addAttribute("locals", localService.findAllSortedByName());
		    return "Admin/FormEditEvent";
		}


	// Elimina un evento
	@PostMapping("/admin/delete/event/{id}")
	public String deleteEvent(@PathVariable("id") Long id) {
		eventService.deleteById(id);
		return "redirect:/admin/events";
	}
}
