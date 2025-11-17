package lab.dev.meritoEstudantil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.dev.meritoEstudantil.domain.professor.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
	Optional<Professor> findByEmail(String email);
	boolean existsByCpf(String cpf);
}
