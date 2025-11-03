package lab.dev.meritoEstudantil.dto.aluno;

public record AlunoUpdateDTO(
	String email,
	String senha,
	String cpf,
	String rg,
	String nome,
	String endereco,
	Integer saldoMoedas,
	String curso
) {}


