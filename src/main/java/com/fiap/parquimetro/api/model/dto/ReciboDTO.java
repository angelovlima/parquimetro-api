package com.fiap.parquimetro.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO para recibos de estacionamento")
public record ReciboDTO(
        @Schema(description = "ID do recibo", example = "1") Long id,
        @Schema(description = "Data de emissão do recibo", example = "2024-07-28T14:34:56") LocalDateTime dataEmissao,
        @Schema(description = "Duração do estacionamento em segundos", example = "3600") long duracao,
        @Schema(description = "Tarifa aplicada ao estacionamento", example = "5.0") double tarifa,
        @Schema(description = "Valor total pago pelo estacionamento", example = "10.0") double valorTotal,
        @Schema(description = "ID do veículo associado ao recibo", example = "1") Long veiculoId
) {
}
