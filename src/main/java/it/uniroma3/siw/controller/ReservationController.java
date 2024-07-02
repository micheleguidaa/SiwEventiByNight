package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Owner;
import it.uniroma3.siw.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    
    /* VEDREMO */
    // Visualizza tutte le prenotazioni dell'utente
    @GetMapping("/reservations")
    public String showUserReservation(@ModelAttribute("CurrentUser") User user, Model model) {
        model.addAttribute("reservations", reservationService.findByUser(user));
        return "RegisterUser/reservations";
    }

    // Mostra la pagina con l'elenco di tutte le prenotazioni 
    @GetMapping("/admin/reservations")
    public String showReservationsForAdmin(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "Admin/indexReservationsAdmin";
    }

    // Mostra la pagina con l'elenco di tutte le prenotazioni dei locali per il proprietario
    @GetMapping("/business/reservations")
    public String showReservationsOwner(@ModelAttribute("CurrentOwner") Owner owner, Model model) {	
        boolean hasReservations = reservationService.doesReservationExistForOwner(owner.getId());
        model.addAttribute("hasReservations", hasReservations);
        return "Owner/indexReservationsOwner";
    }
    
    @PostMapping("/delete/reservation/{id}")
    public String deleteReservation(@PathVariable("id") Long id, HttpServletRequest request) {
        reservationService.deleteById(id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    /* DA FIXARE 
    @PostMapping("/addReservation")
    public String addReservation(@RequestParam("event") Event event, @RequestParam("user") User user, Model model) {
        reservationService.registerReservation(user, event);
        return "redirect:/reservations";
    }*/
    
    @PostMapping("/addReservation")
    public String addReservation( @RequestParam("eventId") Long eventId, @RequestParam("userId") Long userId, Model model) {
        reservationService.registerReservation(userId, eventId);
        return "redirect:/reservations";
    }


    
    @GetMapping("/api/reservations/existsByOwner/{ownerId}")
    public ResponseEntity<Boolean> doesReservationExistForOwner(@PathVariable Long ownerId) {
        boolean exists = reservationService.doesReservationExistForOwner(ownerId);
        return ResponseEntity.ok(exists);
    }
}
