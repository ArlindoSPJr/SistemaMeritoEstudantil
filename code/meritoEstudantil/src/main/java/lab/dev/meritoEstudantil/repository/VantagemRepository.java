package lab.dev.meritoEstudantil.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lab.dev.meritoEstudantil.domain.vantagem.Vantagem;

public interface VantagemRepository extends JpaRepository<Vantagem, Long> {
    List<Vantagem> findByEmpresaParceiraId(Long empresaId);
    Optional<Vantagem> findById(Long id);
    
    List<Vantagem> findAllByEmpresaParceiraId(Long empresaParceiraId);

}
