package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Local;
import it.uniroma3.siw.service.LocalService;

import java.io.IOException;

@Controller
public class LocalController {
	
	@Autowired
    private LocalService localService;

    // Visualizza tutti locali
    @GetMapping("/locals")
    public String showLocals(Model model) {
        model.addAttribute("locals", localService.getAllLocals());
        return "locals"; 
    }
    
    // Visualizza un singolo locale
	@GetMapping("/local/{id}")
	public String getLocal(@PathVariable("id") Long id, Model model) {
		model.addAttribute("local", localService.getLocal(id));
        return "local";
	}
	
	// Mostra la pagina con l'elenco di tutti i locali per l'amministratore
	@GetMapping("/admin/locals")
	public String showAdminLocals(Model model) {
		model.addAttribute("locals", localService.getAllLocals());
		return "Admin/indexLocalsAdmin";
	}
	
	// Mostra la pagina per aggiungere un nuovo locale
	@GetMapping("/admin/addLocal")
	public String addLocalForm(Model model) {
		model.addAttribute("local", new Local());
		return "Admin/FormAddLocal";
	}
	
	// Gestisce l'inserimento di un nuovo locale
	@PostMapping("/admin/addLocal")
	public String addLocal(Local local, @RequestParam("fileImage") MultipartFile file) throws IOException {
		localService.saveLocal(local, file);
		return "redirect:/admin/locals";
	}

	// Elimina un locale
	@PostMapping("/admin/delete/local/{id}")
	public String deleteLocal(@PathVariable("id") Long id) {
		localService.deleteById(id);
		return "redirect:/admin/locals";
	}
	
	// Mostra la pagina per modificare un locale esistente
	@GetMapping("/admin/edit/local/{id}")
	public String editLocalForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("local", localService.getLocal(id));
		return "Admin/FormEditLocal";
	}
	
	// Gestisce la modifica di un locale esistente
	@PostMapping("/admin/edit/local/{id}")
	public String editLocal(@PathVariable("id") Long id, Local local, @RequestParam("fileImage") MultipartFile file) throws IOException {
		localService.updateLocal(id, local, file);
		return "redirect:/admin/locals";
	}
	
	// Mostra la pagina con l'elenco di tutti i locali per l'amministratore
	@GetMapping("/business/locals")
	public String showBusinessLocals(Model model) {
		model.addAttribute("locals", localService.getAllLocals());
		return "Owner/indexLocalsOwner";
	}
}
