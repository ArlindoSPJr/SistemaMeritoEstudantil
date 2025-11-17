package lab.dev.meritoEstudantil.dto.transacao;

public record EnviarMoedasDTO(
	Long alunoId,
	Integer quantidadeMoedas,
	String descricao
) {}
