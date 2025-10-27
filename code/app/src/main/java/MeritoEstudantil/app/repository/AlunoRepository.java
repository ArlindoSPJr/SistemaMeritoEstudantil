package MeritoEstudantil.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import MeritoEstudantil.app.models.Aluno;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    
    Optional<Aluno> findByEmail(String email);
    
    Optional<Aluno> findByCpf(String cpf);
    
    Optional<Aluno> findByRg(String rg);
    
    boolean existsByEmail(String email);
    
    boolean existsByCpf(String cpf);
    
    boolean existsByRg(String rg);
}
