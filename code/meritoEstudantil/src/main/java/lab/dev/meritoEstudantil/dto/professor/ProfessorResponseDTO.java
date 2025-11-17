package lab.dev.meritoEstudantil.dto.professor;

public record ProfessorResponseDTO(
	Long id,
	String email,
	String nome,
	String cpf,
	String departamento,
	Integer saldoMoedas,
	Long instituicaoEnsinoId
) {}
