package com.example.troknite.troknite.controller;

import com.example.troknite.troknite.domain.Users;
import com.example.troknite.troknite.model.LoginDTO;
import com.example.troknite.troknite.model.UsersDTO;
import com.example.troknite.troknite.service.UsersService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    public AuthController(UsersService usersService, PasswordEncoder passwordEncoder, ClientRegistrationRepository clientRegistrationRepository) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity<Long> register(@Valid @RequestBody UsersDTO usersDTO) throws URISyntaxException {
        Users newUser = usersService.register(usersDTO);
        UsersDTO newUsersDTO = usersService.convertToDto(newUser);
        return ResponseEntity.created(new URI("/api/users/" + newUser.getId()))
                .body(newUser.getId());
    }

    @CrossOrigin
    @GetMapping("/me")
    public ResponseEntity<UsersDTO> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Users user = usersService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok().body(usersService.convertToDto(user));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        Users user = usersService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());

        if (!passwordMatches) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        Users authUser = new Users();
        authUser.setId(user.getId());
        authUser.setEmail(user.getEmail());
        authUser.setName(user.getName());
        authUser.setSurname(user.getSurname());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok().body(authUser);
    }

    @GetMapping("/login/facebook")
    public String loginWithFacebook() {
        return "redirect:/oauth2/authorization/facebook";
    }

    @PostMapping("/login/facebook/callback")
    public ResponseEntity<Object> loginWithFacebookCallback(OAuth2AuthenticationToken authenticationToken) {
        // Récupérer les informations d'authentification
        OAuth2User oauth2User = authenticationToken.getPrincipal();
        Map<String, Object> userInfo = oauth2User.getAttributes();
        String email = (String) userInfo.get("email");

        // Vérifier si l'utilisateur existe déjà dans la base de données
        Users user = usersService.findByEmail(email);

        if (user == null) {
            // Créer un nouvel utilisateur ou effectuer d'autres opérations nécessaires
            // ...

            // Enregistrer l'utilisateur dans la base de données
            user = usersService.registerFacebookUser(email);
        }

        // Créer l'utilisateur authentifié
        Users authUser = new Users();
        authUser.setId(user.getId());
        authUser.setEmail(user.getEmail());
        authUser.setName(user.getName());
        authUser.setSurname(user.getSurname());

        // Retourner la réponse avec l'utilisateur authentifié
        return ResponseEntity.ok().body(authUser);
    }
}
