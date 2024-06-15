package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.ReservationService;

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
    

}
