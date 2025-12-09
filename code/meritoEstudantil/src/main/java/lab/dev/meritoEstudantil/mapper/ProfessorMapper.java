package lab.dev.meritoEstudantil.mapper;

import org.springframework.stereotype.Component;

import lab.dev.meritoEstudantil.domain.professor.Professor;
import lab.dev.meritoEstudantil.dto.professor.ProfessorCreateDTO;
import lab.dev.meritoEstudantil.dto.professor.ProfessorResponseDTO;

/**
 * Mapper para converter entre DTOs e entidades de Professor.
 * Centraliza a lógica de conversão, evitando duplicação de código nos controllers.
 */
@Component
public class ProfessorMapper {

    /**
     * Converte ProfessorCreateDTO para entidade Professor.
     * 
     * @param dto DTO com dados para criação
     * @return Entidade Professor preenchida
     */
    public Professor toEntity(ProfessorCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Professor professor = new Professor();
        professor.setEmail(dto.email());
        professor.setSenha(dto.senha());
        professor.setNome(dto.nome());
        professor.setCpf(dto.cpf());
        professor.setDepartamento(dto.departamento());
        
        return professor;
    }

    /**
     * Converte entidade Professor para ProfessorResponseDTO.
     * 
     * @param professor Entidade a ser convertida
     * @return DTO de resposta com dados do professor
     */
    public ProfessorResponseDTO toResponseDTO(Professor professor) {
        if (professor == null) {
            return null;
        }
        
        return new ProfessorResponseDTO(
            professor.getId(),
            professor.getEmail(),
            professor.getNome(),
            professor.getCpf(),
            professor.getDepartamento(),
            professor.getSaldoMoedas(),
            professor.getInstituicaoEnsino() != null ? professor.getInstituicaoEnsino().getId() : null
        );
    }

    /**
     * Atualiza entidade Professor existente com dados do DTO.
     * Útil para operações de update onde queremos manter a instância existente.
     * 
     * @param dto DTO com dados para atualização
     * @return Entidade Professor preenchida para update
     */
    public Professor toEntityForUpdate(ProfessorCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setDepartamento(dto.departamento());
        
        return professor;
    }
}
