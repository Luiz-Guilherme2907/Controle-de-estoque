package com.controle.estoque.service;

import com.controle.estoque.dto.request.LoginRequest;
import com.controle.estoque.dto.request.RegisterRequest;
import com.controle.estoque.dto.response.AuthResponse;
import com.controle.estoque.entity.Usuario;
import com.controle.estoque.exception.BusinessException;
import com.controle.estoque.repository.UsuarioRepository;
import com.controle.estoque.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse registrar(RegisterRequest req) {
        if (usuarioRepository.existsByEmail(req.email())) {
            throw new BusinessException("E-mail já cadastrado");
        }
        var usuario = Usuario.builder()
                .nome(req.nome())
                .email(req.email())
                .senha(passwordEncoder.encode(req.senha()))
                .role(Usuario.Role.USER)
                .build();
        usuarioRepository.save(usuario);
        return AuthResponse.of(jwtService.gerarToken(usuario), usuario.getEmail(), usuario.getRole().name());
    }

    public AuthResponse login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.senha()));
        var usuario = usuarioRepository.findByEmail(req.email()).orElseThrow();
        return AuthResponse.of(jwtService.gerarToken(usuario), usuario.getEmail(), usuario.getRole().name());
    }
}
