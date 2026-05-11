package com.controle.estoque.dto.response;

public record AuthResponse(String token, String tipo, String email) {
    public static AuthResponse of(String token, String email) {
        return new AuthResponse(token, "Bearer", email);
    }
}
