package lab.dev.meritoEstudantil.dto.aluno;

public record AlunoResponseDTO(
	Long id,
	String email,
	String cpf,
	String rg,
	String nome,
	String endereco,
	Integer saldoMoedas,
	String curso
) {}


