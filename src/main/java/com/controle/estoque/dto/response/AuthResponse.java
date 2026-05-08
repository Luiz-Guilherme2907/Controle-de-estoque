package com.controle.estoque.dto.response;

public record AuthResponse(String token, String tipo, String email, String role) {
    public static AuthResponse of(String token, String email, String role) {
        return new AuthResponse(token, "Bearer", email, role);
    }
}
