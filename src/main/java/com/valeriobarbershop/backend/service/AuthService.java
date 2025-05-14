package com.valeriobarbershop.backend.service;

import com.valeriobarbershop.backend.dto.auth.LoginRequest;
import com.valeriobarbershop.backend.dto.auth.RegisterRequest;
import com.valeriobarbershop.backend.model.Ruolo;
import com.valeriobarbershop.backend.model.Utente;
import com.valeriobarbershop.backend.repository.UtenteRepository;
import com.valeriobarbershop.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

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
}