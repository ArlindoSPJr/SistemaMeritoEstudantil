package lab.dev.meritoEstudantil.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lab.dev.meritoEstudantil.domain.gerente.Gerente;
import lab.dev.meritoEstudantil.repository.GerenteRepository;

@Configuration
public class DataInitializer {

	@Value("${admin.gerente.email:admin@merito.com}")
	private String defaultGerenteEmail;

	@Value("${admin.gerente.senha:admin123}")
	private String defaultGerenteSenha;

	@Bean
	CommandLineRunner seedGerente(GerenteRepository gerenteRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (gerenteRepository.findByEmail(defaultGerenteEmail).isEmpty()) {
				Gerente gerente = new Gerente();
				gerente.setEmail(defaultGerenteEmail);
				gerente.setSenha(passwordEncoder.encode(defaultGerenteSenha));
				gerenteRepository.save(gerente);
			}
		};
	}
}


