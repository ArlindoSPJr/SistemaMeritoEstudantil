package lab.dev.meritoEstudantil.dto.aluno;

public record AlunoCreateDTO(
	String email,
	String senha,
	String cpf,
	String rg,
	String nome,
	String endereco,
	Integer saldoMoedas,
	String curso
) {}


