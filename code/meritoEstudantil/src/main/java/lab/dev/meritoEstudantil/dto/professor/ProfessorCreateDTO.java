package lab.dev.meritoEstudantil.dto.professor;

public record ProfessorCreateDTO(
	String email,
	String senha,
	String nome,
	String cpf,
	String departamento,
	Long instituicaoEnsinoId
) {}
