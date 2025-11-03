package lab.dev.meritoEstudantil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.dev.meritoEstudantil.domain.aluno.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	Optional<Aluno> findByEmail(String email);
	boolean existsByCpf(String cpf);
	boolean existsByRg(String rg);
}


