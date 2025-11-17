package lab.dev.meritoEstudantil.dto.transacao;

import java.time.LocalDateTime;

public record TransacaoResponseDTO(
	Long id,
	Long professorId,
	String professorNome,
	Long alunoId,
	String alunoNome,
	Integer quantidadeMoedas,
	String tipo,
	String descricao,
	LocalDateTime dataCriacao
) {}
