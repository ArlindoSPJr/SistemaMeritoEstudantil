package MeritoEstudantil.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import MeritoEstudantil.app.controllers.dto.AlunoDTO;
import MeritoEstudantil.app.controllers.dto.AlunoResponseDTO;
import MeritoEstudantil.app.controllers.dto.LoginDTO;
import MeritoEstudantil.app.services.AuthService;
import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody AlunoDTO clienteDTO) {
        try {
            AlunoResponseDTO cliente = authService.registrar(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            Map<String, Object> response = authService.login(loginDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
