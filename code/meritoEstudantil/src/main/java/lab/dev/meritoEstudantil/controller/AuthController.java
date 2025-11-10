package lab.dev.meritoEstudantil.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lab.dev.meritoEstudantil.dto.auth.LoginRequest;
import lab.dev.meritoEstudantil.dto.auth.TokenResponse;
import lab.dev.meritoEstudantil.security.jwt.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.senha()));
		
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails user) {
			Long userId = null;
			// Verifica se o usuário tem ID
			try {
				java.lang.reflect.Method getIdMethod = principal.getClass().getMethod("getId");
				userId = (Long) getIdMethod.invoke(principal);
			} catch (Exception ignored) {
				// Se não conseguir pegar o ID, continua com null
			}
			
			String token = jwtService.generateToken(user, userId);
			return ResponseEntity.ok(new TokenResponse(token, user.getAuthorities().toString(), userId));
		}
		
		throw new IllegalStateException("Usuário não encontrado");
	}
}


