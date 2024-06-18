package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Owner;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;

import org.springframework.security.core.userdetails.UserDetails;

@ControllerAdvice
public class GlobalController {
	@Autowired
	private CredentialsService credentialsService;
	
	@ModelAttribute("userDetails")
	public UserDetails getUserDetails() {
		UserDetails  userDetails = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();}
		return userDetails;
	}
	
    @ModelAttribute("role")
    public String getAuthorityAsString() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getAuthorities().isEmpty()) {
            GrantedAuthority authority = authentication.getAuthorities().iterator().next();
            return authority.getAuthority();
        }
        return null;
    }
    
  
    @ModelAttribute("CurrentUser")
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() 
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Credentials credenziali = credentialsService.getCredentials(userDetails.getUsername());
            if (credenziali != null && !credenziali.getRole().equals(Credentials.BUSINESS_ROLE)) {
                return credenziali.getUser();
            }
        }
        return null;
    }
    
    @ModelAttribute("CurrentOwner")
    public Owner getOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() 
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Credentials credenziali = credentialsService.getCredentials(userDetails.getUsername());
            if (credenziali != null && credenziali.getRole().equals(Credentials.BUSINESS_ROLE)) {
                return credenziali.getOwner();
            }
        }
        return null;
    }
}