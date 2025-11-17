package lab.dev.meritoEstudantil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.dev.meritoEstudantil.domain.instituicao.IInstituicaoEnsino;

public interface IInstituicaoEnsinoRepository extends JpaRepository<IInstituicaoEnsino, Long> {
}
