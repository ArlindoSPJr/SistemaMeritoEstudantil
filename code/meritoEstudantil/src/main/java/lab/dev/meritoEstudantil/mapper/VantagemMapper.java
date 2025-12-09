package lab.dev.meritoEstudantil.mapper;

import org.springframework.stereotype.Component;

import lab.dev.meritoEstudantil.domain.empresa.EmpresaParceira;
import lab.dev.meritoEstudantil.domain.vantagem.Vantagem;
import lab.dev.meritoEstudantil.dto.vantagem.VantagemCreateDTO;
import lab.dev.meritoEstudantil.dto.vantagem.VantagemResponseDTO;
import lab.dev.meritoEstudantil.dto.vantagem.VantagemUpdateDTO;
import lab.dev.meritoEstudantil.repository.EmpresaParceiraRepository;

/**
 * Mapper para converter entre DTOs e entidades de Vantagem.
 * Centraliza a lógica de conversão, evitando duplicação de código nos controllers.
 */
@Component
public class VantagemMapper {

    private final EmpresaParceiraRepository empresaParceiraRepository;

    public VantagemMapper(EmpresaParceiraRepository empresaParceiraRepository) {
        this.empresaParceiraRepository = empresaParceiraRepository;
    }

    /**
     * Converte VantagemCreateDTO para entidade Vantagem.
     * Busca a empresa parceira no repository ao invés de usar reflexão.
     * 
     * @param dto DTO com dados para criação
     * @return Entidade Vantagem preenchida
     */
    public Vantagem toEntity(VantagemCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Vantagem vantagem = new Vantagem();
        vantagem.setDescricao(dto.descricao());
        vantagem.setAtivo(true);
        vantagem.setCustoMoedas(dto.custoMoedas());
        vantagem.setQuantidade(dto.quantidade());
        
        // Busca a empresa parceira no banco ao invés de usar reflexão
        if (dto.empresaParceiraId() != null) {
            EmpresaParceira empresa = empresaParceiraRepository.findById(dto.empresaParceiraId())
                    .orElse(null);
            vantagem.setEmpresaParceira(empresa);
        }
        
        return vantagem;
    }

    /**
     * Converte VantagemUpdateDTO para entidade Vantagem.
     * Busca a empresa parceira no repository ao invés de usar reflexão.
     * 
     * @param dto DTO com dados para atualização
     * @return Entidade Vantagem preenchida
     */
    public Vantagem toEntity(VantagemUpdateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Vantagem vantagem = new Vantagem();
        vantagem.setDescricao(dto.descricao());
        
        if (dto.ativo() != null) {
            vantagem.setAtivo(dto.ativo());
        }
        
        if (dto.imageUrl() != null) {
            vantagem.setImageUrl(dto.imageUrl());
        }
        
        if (dto.custoMoedas() != null) {
            vantagem.setCustoMoedas(dto.custoMoedas());
        }
        
        if (dto.quantidade() != null) {
            vantagem.setQuantidade(dto.quantidade());
        }
        
        // Busca a empresa parceira no banco ao invés de usar reflexão
        if (dto.empresaParceiraId() != null) {
            EmpresaParceira empresa = empresaParceiraRepository.findById(dto.empresaParceiraId())
                    .orElse(null);
            vantagem.setEmpresaParceira(empresa);
        }
        
        return vantagem;
    }

    /**
     * Converte entidade Vantagem para VantagemResponseDTO.
     * 
     * @param vantagem Entidade a ser convertida
     * @return DTO de resposta com dados da vantagem
     */
    public VantagemResponseDTO toResponseDTO(Vantagem vantagem) {
        if (vantagem == null) {
            return null;
        }
        
        Long empresaId = vantagem.getEmpresaParceira() != null ? vantagem.getEmpresaParceira().getId() : null;
        String empresaNome = vantagem.getEmpresaParceira() != null ? vantagem.getEmpresaParceira().getNomeFantasia() : null;
        
        return new VantagemResponseDTO(
            vantagem.getId(),
            vantagem.getDescricao(),
            vantagem.isAtivo(),
            vantagem.getImageUrl(),
            vantagem.getCustoMoedas(),
            empresaId,
            empresaNome,
            vantagem.getQuantidade()
        );
    }
}
