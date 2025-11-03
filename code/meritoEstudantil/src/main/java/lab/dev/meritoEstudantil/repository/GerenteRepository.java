package lab.dev.meritoEstudantil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.dev.meritoEstudantil.domain.gerente.Gerente;

public interface GerenteRepository extends JpaRepository<Gerente, Long> {
	Optional<Gerente> findByEmail(String email);
}


