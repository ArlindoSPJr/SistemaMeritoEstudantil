package lab.dev.meritoEstudantil.dto.auth;

public record TokenResponse(String token, String role, Long userId) {}


