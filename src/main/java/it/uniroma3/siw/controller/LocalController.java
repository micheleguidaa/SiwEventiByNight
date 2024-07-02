package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Local;
import it.uniroma3.siw.service.LocalService;
import it.uniroma3.siw.service.OwnerService;

import java.io.IOException;

@Controller
public class LocalController {

    @Autowired
    private LocalService localService;

    @Autowired
    private OwnerService ownerService;

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

    // Mostra la pagina con l'elenco di tutti i locali per l'admin
    @GetMapping("/admin/locals")
    public String showAdminLocals(Model model) {
        model.addAttribute("locals", localService.getAllLocals());
        return "Admin/indexLocalsAdmin";
    }

    // Mostra la pagina per aggiungere un nuovo locale per admin
    @GetMapping("/admin/add/local")
    public String addLocalForm(Model model) {
        model.addAttribute("local", new Local());
        model.addAttribute("owners", ownerService.findAllSortedByName());
        return "Admin/FormAddLocal";
    }

    // Mostra la pagina per modificare un locale esistente da admin
    @GetMapping("/admin/edit/local/{id}")
    public String editLocalForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("local", localService.getLocal(id));
        model.addAttribute("owners", ownerService.findAllSortedByName());
        return "Admin/FormEditLocal";
    }

    // Gestisce l'inserimento di un nuovo locale per admin
    @PostMapping("/admin/add/local")
    public String addLocal(@ModelAttribute Local local, @RequestParam("fileImage") MultipartFile file,
                           @RequestParam("ownerId") Long ownerId) throws IOException {
        localService.saveLocal(local, ownerId, file);
        return "redirect:/admin/locals";
    }

    // Elimina un locale per admin
    @PostMapping("/admin/delete/local/{id}")
    public String deleteLocal(@PathVariable("id") Long id) {
        localService.deleteById(id);
        return "redirect:/admin/locals";
    }

    // Gestisce la modifica di un locale esistente per admin
    @PostMapping("/admin/edit/local/{id}")
    public String editLocal(@PathVariable("id") Long id, @ModelAttribute Local local,
    			@RequestParam("ownerId") Long ownerId, @RequestParam("fileImage") MultipartFile file) throws IOException {
        localService.updateLocal(id, local, ownerId, file);
        return "redirect:/admin/locals";
    }

    // Mostra la pagina con l'elenco di tutti i locali per il proprietario
    @GetMapping("/business/locals")
    public String showBusinessLocals(Model model) {
        return "Owner/indexLocalsOwner";
    }

    // Mostra la pagina per aggiungere un nuovo locale per proprietario
    @GetMapping("/business/add/local")
    public String addLocalFormOwner(Model model) {
        model.addAttribute("local", new Local());
        return "Owner/FormAddLocalOwner";
    }

    // Mostra la pagina per modificare un locale esistente per proprietario
    @GetMapping("/business/edit/local/{id}")
    public String editLocalFormOwner(@PathVariable("id") Long id, Model model) {
        model.addAttribute("local", localService.getLocal(id));
        return "Owner/FormEditLocalOwner";
    }

    // Gestisce l'inserimento di un nuovo locale
    @PostMapping("/business/add/local")
    public String addLocalOwner(@ModelAttribute Local local, @RequestParam("fileImage") MultipartFile file,
                                @RequestParam("ownerId") Long ownerId) throws IOException {
        localService.saveLocal(local, ownerId, file);
        return "redirect:/business/locals";
    }

    // Elimina un locale
    @PostMapping("/business/delete/local/{id}")
    public String deleteLocalOener(@PathVariable("id") Long id) {
        localService.deleteById(id);
        return "redirect:/business/locals";
    }

    // Gestisce la modifica di un locale esistente
    @PostMapping("/business/edit/local/{id}")
    public String editLocalOwner(@PathVariable("id") Long id, @ModelAttribute Local local,
                                 @RequestParam("fileImage") MultipartFile file) throws IOException {
        localService.updateLocal(id, local, file);
        return "redirect:/business/locals";
    }
}
