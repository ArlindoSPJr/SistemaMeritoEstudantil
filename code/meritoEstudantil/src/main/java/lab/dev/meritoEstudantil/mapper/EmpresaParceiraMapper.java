package lab.dev.meritoEstudantil.mapper;

import org.springframework.stereotype.Component;

import lab.dev.meritoEstudantil.domain.empresa.EmpresaParceira;
import lab.dev.meritoEstudantil.dto.empresa.EmpresaCreateDTO;
import lab.dev.meritoEstudantil.dto.empresa.EmpresaResponseDTO;
import lab.dev.meritoEstudantil.dto.empresa.EmpresaUpdateDTO;

/**
 * Mapper para converter entre DTOs e entidades de EmpresaParceira.
 * Centraliza a lógica de conversão, evitando duplicação de código nos controllers.
 */
@Component
public class EmpresaParceiraMapper {

    /**
     * Converte EmpresaCreateDTO para entidade EmpresaParceira.
     * 
     * @param dto DTO com dados para criação
     * @return Entidade EmpresaParceira preenchida
     */
    public EmpresaParceira toEntity(EmpresaCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmpresaParceira empresa = new EmpresaParceira();
        empresa.setEmail(dto.email());
        empresa.setSenha(dto.senha());
        empresa.setNomeFantasia(dto.nomeFantasia());
        empresa.setRazaoSocial(dto.razaoSocial());
        empresa.setCnpj(dto.cnpj());
        
        return empresa;
    }

    /**
     * Converte EmpresaUpdateDTO para entidade EmpresaParceira.
     * 
     * @param dto DTO com dados para atualização
     * @return Entidade EmpresaParceira preenchida
     */
    public EmpresaParceira toEntity(EmpresaUpdateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        EmpresaParceira empresa = new EmpresaParceira();
        empresa.setEmail(dto.email());
        empresa.setSenha(dto.senha());
        empresa.setNomeFantasia(dto.nomeFantasia());
        empresa.setRazaoSocial(dto.razaoSocial());
        empresa.setCnpj(dto.cnpj());
        
        return empresa;
    }

    /**
     * Converte entidade EmpresaParceira para EmpresaResponseDTO.
     * 
     * @param empresa Entidade a ser convertida
     * @return DTO de resposta com dados da empresa
     */
    public EmpresaResponseDTO toResponseDTO(EmpresaParceira empresa) {
        if (empresa == null) {
            return null;
        }
        
        return new EmpresaResponseDTO(
            empresa.getId(),
            empresa.getEmail(),
            empresa.getNomeFantasia(),
            empresa.getRazaoSocial(),
            empresa.getCnpj()
        );
    }
}
