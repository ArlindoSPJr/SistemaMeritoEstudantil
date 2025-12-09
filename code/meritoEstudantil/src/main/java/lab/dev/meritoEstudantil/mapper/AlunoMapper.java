package lab.dev.meritoEstudantil.mapper;

import org.springframework.stereotype.Component;

import lab.dev.meritoEstudantil.domain.aluno.Aluno;
import lab.dev.meritoEstudantil.dto.aluno.AlunoCreateDTO;
import lab.dev.meritoEstudantil.dto.aluno.AlunoResponseDTO;
import lab.dev.meritoEstudantil.dto.aluno.AlunoUpdateDTO;

/**
 * Mapper para converter entre DTOs e entidades de Aluno.
 * Centraliza a lógica de conversão, evitando duplicação de código nos controllers.
 */
@Component
public class AlunoMapper {

    /**
     * Converte AlunoCreateDTO para entidade Aluno.
     * 
     * @param dto DTO com dados para criação
     * @return Entidade Aluno preenchida
     */
    public Aluno toEntity(AlunoCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Aluno aluno = new Aluno();
        aluno.setEmail(dto.email());
        aluno.setSenha(dto.senha());
        aluno.setCpf(dto.cpf());
        aluno.setRg(dto.rg());
        aluno.setNome(dto.nome());
        aluno.setEndereco(dto.endereco());
        aluno.setCurso(dto.curso());
        
        if (dto.saldoMoedas() != null) {
            aluno.setSaldoMoedas(dto.saldoMoedas());
        }
        
        return aluno;
    }

    /**
     * Converte AlunoUpdateDTO para entidade Aluno.
     * 
     * @param dto DTO com dados para atualização
     * @return Entidade Aluno preenchida
     */
    public Aluno toEntity(AlunoUpdateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Aluno aluno = new Aluno();
        aluno.setEmail(dto.email());
        aluno.setSenha(dto.senha());
        aluno.setCpf(dto.cpf());
        aluno.setRg(dto.rg());
        aluno.setNome(dto.nome());
        aluno.setEndereco(dto.endereco());
        aluno.setCurso(dto.curso());
        
        if (dto.saldoMoedas() != null) {
            aluno.setSaldoMoedas(dto.saldoMoedas());
        }
        
        return aluno;
    }

    /**
     * Converte entidade Aluno para AlunoResponseDTO.
     * 
     * @param aluno Entidade a ser convertida
     * @return DTO de resposta com dados do aluno
     */
    public AlunoResponseDTO toResponseDTO(Aluno aluno) {
        if (aluno == null) {
            return null;
        }
        
        return new AlunoResponseDTO(
            aluno.getId(),
            aluno.getEmail(),
            aluno.getCpf(),
            aluno.getRg(),
            aluno.getNome(),
            aluno.getEndereco(),
            aluno.getSaldoMoedas(),
            aluno.getCurso()
        );
    }
}
