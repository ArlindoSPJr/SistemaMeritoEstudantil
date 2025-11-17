package lab.dev.meritoEstudantil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.dev.meritoEstudantil.domain.transacao.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	List<Transacao> findByAlunoId(Long alunoId);
	List<Transacao> findByProfessorId(Long professorId);
}
