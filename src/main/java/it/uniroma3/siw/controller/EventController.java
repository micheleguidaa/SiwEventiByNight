package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Event;
import it.uniroma3.siw.model.Owner;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.EventService;
import it.uniroma3.siw.service.LocalService;
import it.uniroma3.siw.service.ReservationService;
import jakarta.validation.Valid;

import java.io.IOException;

@Controller
@SessionAttributes("CurrentUser")
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

	/* DA RIVEDERE */
	@GetMapping("/event/{id}")
	public String showEvent(@PathVariable("id") Long eventId, @ModelAttribute("CurrentUser") User currentUser,
			Model model) {
		Event event = eventService.getEvent(eventId);
		boolean isReserved = false;

		if (currentUser != null) {
			isReserved = reservationService.isUserReservedForEvent(currentUser, eventId);
		}

		model.addAttribute("event", event);
		model.addAttribute("isReserved", isReserved);

		return "event";
	}

	// Mostra la pagina con l'elenco di tutti gli eventi per l'amministratore da admin
	@GetMapping("/admin/events")
	public String showAdminEvents(Model model) {
		model.addAttribute("events", eventService.findAll());
		return "Admin/indexEventsAdmin";
	}

	// Mostra la pagina per creare un nuovo evento da admin
	@GetMapping("/admin/add/event")
	public String addEventForm(Model model) {
		model.addAttribute("event", new Event());
		model.addAttribute("locals", localService.findAllSortedByName());
		return "Admin/FormAddEvent";
	}

	// Mostra la pagina per modificare un evento esistente da admin
	@GetMapping("/admin/edit/event/{id}")
	public String editEventForm(@PathVariable("id") Long id, Model model) {
		Event event = eventService.getEvent(id);
		model.addAttribute("event", event);
		model.addAttribute("locals", localService.findAllSortedByName());
		return "Admin/FormEditEvent";
	}

	// Mostra la pagina con l'elenco di tutti gli eventi per l'amministratore da proprietario
	@GetMapping("/business/events")
	public String showOwnerEvents(Model model) {
		model.addAttribute("events", eventService.findAll());
		return "Owner/indexEventsOwner";
	}

	// Mostra la pagina per creare un nuovo evento da proprietario
	@GetMapping("/business/add/event")
	public String addEventFormBusiness(Model model) {
		model.addAttribute("event", new Event());
		model.addAttribute("locals", localService.findAllSortedByName());
		return "Owner/FormAddEventOwner";
	}

	// Mostra la pagina per modificare un evento esistente da proprietario
	@GetMapping("/business/edit/event/{id}")
	public String editEventFormOwner(@PathVariable("id") Long id, Model model) {
		Event event = eventService.getEvent(id);
		model.addAttribute("event", event);
		model.addAttribute("locals", localService.findAllSortedByName());
		return "Owner/FormEditEventOwner";
	}

	// Gestisce l'inserimento di un nuovo evento da admin
	@PostMapping("/admin/add/event")
	public String addEvent(@Valid @ModelAttribute("event") Event event, BindingResult eventBindingResult,
			@RequestParam("fileImage") MultipartFile file, Model model) throws IOException {
		if (!eventBindingResult.hasErrors()) {
			eventService.saveEvent(event, file);
			return "redirect:/admin/events";
		}
		model.addAttribute("locals", localService.findAllSortedByName());
		return "Admin/FormAddEvent";
	}

	// Gestisce la modifica di un evento esistente da admin
	@PostMapping("/admin/edit/event/{id}")
	public String editEvent(@PathVariable("id") Long id, @Valid @ModelAttribute("event") Event eventDetails,
			BindingResult eventBindingResult, @RequestParam("fileImage") MultipartFile file, Model model)
			throws IOException {
		if (!eventBindingResult.hasErrors()) {
			eventService.updateEvent(id, eventDetails, file);
			return "redirect:/admin/events";
		}
		model.addAttribute("locals", localService.findAllSortedByName());
		return "Admin/FormEditEvent";
	}

	// Elimina un evento da admin
	@PostMapping("/admin/delete/event/{id}")
	public String deleteEvent(@PathVariable("id") Long id) {
		eventService.deleteById(id);
		return "redirect:/admin/events";
	}

	// Gestisce l'inserimento di un nuovo evento da proprietario
	@PostMapping("/business/add/event")
	public String addEventOwner(@Valid @ModelAttribute("event") Event event, BindingResult eventBindingResult,
			@RequestParam("fileImage") MultipartFile file, Model model) throws IOException {
		if (!eventBindingResult.hasErrors()) {
			eventService.saveEvent(event, file);
			return "redirect:/business/events";
		}
		model.addAttribute("locals", localService.findAllSortedByName());
		return "Owner/FormAddEventOwner";
	}

	// Gestisce la modifica di un evento esistente da proprietario
	@PostMapping("/business/edit/event/{id}")
	public String editEventOwner(@PathVariable("id") Long id, @Valid @ModelAttribute("event") Event eventDetails,
			BindingResult eventBindingResult, @RequestParam("fileImage") MultipartFile file, Model model)
			throws IOException {
		if (!eventBindingResult.hasErrors()) {
			eventService.updateEvent(id, eventDetails, file);
			return "redirect:/business/events";
		}
		model.addAttribute("locals", localService.findAllSortedByName());
		return "Owner/FormEditEventOwner";
	}

	// Elimina un evento da proprietario
	@PostMapping("/business/delete/event/{id}")
	public String deleteEventOwner(@PathVariable("id") Long id) {
		eventService.deleteById(id);
		return "redirect:/business/events";
	}

}
