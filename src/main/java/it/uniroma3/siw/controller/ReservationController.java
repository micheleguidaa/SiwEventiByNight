package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Reservation;
import it.uniroma3.siw.service.ReservationService;
import jakarta.validation.Valid;

@Controller
public class ReservationController {
	@Autowired
	private ReservationService reservationService;

	// Visualizza tutti locali
	@GetMapping("/reservations")
	public String showLocals(Model model) {
		model.addAttribute("reservations", reservationService.findAll());
		return "RegisterUser/reservations";
	}

	@PostMapping("/addReservation")
	public String addReservation(@Valid @ModelAttribute("reservation") Reservation reservation,
			 @RequestParam("eventId") Long eventId, @RequestParam("userId") Long userId, Model model) {
		reservationService.registerReservation(reservation, userId, eventId);
		return "redirect:/leMieRicette";
	}

}
