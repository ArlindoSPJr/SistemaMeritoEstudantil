package lab.dev.meritoEstudantil.dto.vantagem;

public record VantagemResponseDTO(
    Long id,
    String descricao,
    boolean ativo,
    String imageUrl,
    double custoMoedas,
    Long empresaParceiraId,
    String empresaNome
) {}
