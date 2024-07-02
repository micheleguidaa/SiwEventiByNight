package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Owner;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;

@ControllerAdvice
/* DA RIVEDERE */
@SessionAttributes("CurrentUser")
public class GlobalController {
    @Autowired
    private CredentialsService credentialsService;

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @ModelAttribute("userDetails")
    public UserDetails getUserDetails() {
        Authentication authentication = getAuthentication();
        if (isAuthenticated(authentication)) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    @ModelAttribute("role")
    public String getAuthorityAsString() {
        Authentication authentication = getAuthentication();
        if (isAuthenticated(authentication)) {
            return authentication.getAuthorities().iterator().next().getAuthority();
        }
        return null;
    }

    @ModelAttribute("CurrentUser")
    public User getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (isAuthenticated(authentication)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
            if (credentials != null && !Credentials.BUSINESS_ROLE.equals(credentials.getRole())) {
                return credentials.getUser();
            }
        }
        return null;
    }

    @ModelAttribute("CurrentOwner")
    public Owner getCurrentOwner() {
        Authentication authentication = getAuthentication();
        if (isAuthenticated(authentication)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
            if (credentials != null && Credentials.BUSINESS_ROLE.equals(credentials.getRole())) {
                return credentials.getOwner();
            }
        }
        return null;
    }
}
