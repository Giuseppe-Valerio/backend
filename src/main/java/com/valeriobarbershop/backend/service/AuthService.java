package com.valeriobarbershop.backend.service;

import com.valeriobarbershop.backend.dto.auth.LoginRequest;
import com.valeriobarbershop.backend.dto.auth.RegisterRequest;
import com.valeriobarbershop.backend.model.Ruolo;
import com.valeriobarbershop.backend.model.Utente;
import com.valeriobarbershop.backend.repository.UtenteRepository;
import com.valeriobarbershop.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Registrazione di un nuovo cliente
     */
    public Utente register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfermaPassword())) {
            throw new RuntimeException("Le password non coincidono");
        }

        if (utenteRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email già utilizzata");
        }

        Utente utente = new Utente();
        utente.setNome(request.getNome());
        utente.setEmail(request.getEmail());
        utente.setPassword(passwordEncoder.encode(request.getPassword()));
        utente.setRuolo(Ruolo.CLIENTE);

        return utenteRepository.save(utente);
    }

    /**
     * Login dell'utente e generazione del token JWT
     */
    public Map<String, String> login(LoginRequest request) {
        Utente utente = utenteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (!passwordEncoder.matches(request.getPassword(), utente.getPassword())) {
            throw new RuntimeException("Credenziali errate");
        }

        String token = jwtUtils.generateToken(utente.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("email", utente.getEmail());
        response.put("nome", utente.getNome());
        response.put("ruolo", utente.getRuolo().name());

        return response;
    }

    /**
     * Carica i dettagli dell'utente per Spring Security
     */
    @Override
    public UserDetails loadUserByUsername(String email) {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(utente.getEmail())
                .password(utente.getPassword())
                .roles(utente.getRuolo().name())
                .build();
    }

    public Utente getUtenteAutenticato() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {

            String email = authentication.getName();
            return utenteRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        }

        throw new RuntimeException("Nessun utente autenticato");
    }
}