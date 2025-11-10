package lab.dev.meritoEstudantil.dto.vantagem;

public record VantagemCreateDTO(
    String descricao,
    double custoMoedas,
    Long empresaParceiraId
) {}
