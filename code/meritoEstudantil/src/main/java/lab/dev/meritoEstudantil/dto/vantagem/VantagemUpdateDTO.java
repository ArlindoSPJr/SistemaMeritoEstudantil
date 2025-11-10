package lab.dev.meritoEstudantil.dto.vantagem;

public record VantagemUpdateDTO(
    String descricao,
    Boolean ativo,
    String imageUrl,
    Double custoMoedas,
    Long empresaParceiraId
) {}
