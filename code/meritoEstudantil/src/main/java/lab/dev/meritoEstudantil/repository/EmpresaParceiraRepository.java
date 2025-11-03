package lab.dev.meritoEstudantil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.dev.meritoEstudantil.domain.empresa.EmpresaParceira;

public interface EmpresaParceiraRepository extends JpaRepository<EmpresaParceira, Long> {
	Optional<EmpresaParceira> findByEmail(String email);
	boolean existsByCnpj(String cnpj);
}


