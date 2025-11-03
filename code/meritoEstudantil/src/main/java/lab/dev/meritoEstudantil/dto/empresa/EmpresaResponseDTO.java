package lab.dev.meritoEstudantil.dto.empresa;

public record EmpresaResponseDTO(
	Long id,
	String email,
	String nomeFantasia,
	String razaoSocial,
	String cnpj
) {}


