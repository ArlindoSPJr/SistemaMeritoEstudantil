package lab.dev.meritoEstudantil.dto.empresa;

public record EmpresaUpdateDTO(
	String email,
	String senha,
	String nomeFantasia,
	String razaoSocial,
	String cnpj
) {}


