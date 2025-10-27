package MeritoEstudantil.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import MeritoEstudantil.app.config.JwtConfig;
import MeritoEstudantil.app.controllers.dto.AlunoDTO;
import MeritoEstudantil.app.controllers.dto.AlunoResponseDTO;
import MeritoEstudantil.app.controllers.dto.LoginDTO;
import MeritoEstudantil.app.models.Aluno;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    
    @Autowired
    private AlunoService clienteService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtConfig jwtConfig;
    
    public AlunoResponseDTO registrar(AlunoDTO clienteDTO) {
        return clienteService.cadastrarCliente(clienteDTO);
    }
    
    public Map<String, Object> login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
            );
            
            Aluno cliente = (Aluno) authentication.getPrincipal();
            String token = jwtConfig.generateToken(cliente);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("cliente", new AlunoResponseDTO(cliente));
            
            return response;
            
        } catch (AuthenticationException e) {
            throw new RuntimeException("Email ou senha inválidos");
        }
    }
}
