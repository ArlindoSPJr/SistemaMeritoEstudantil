package lab.dev.meritoEstudantil.dto.empresa;

public record EmpresaCreateDTO(
	String email,
	String senha,
	String nomeFantasia,
	String razaoSocial,
	String cnpj
) {}


