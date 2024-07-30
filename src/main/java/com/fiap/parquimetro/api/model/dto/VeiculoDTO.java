package com.fiap.parquimetro.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para registrar e gerenciar veículos")
public record VeiculoDTO(
        @Schema(description = "ID do veículo", example = "1") Long id,
        @Schema(description = "Placa do veículo", example = "AAA-1111") String placa,
        @Schema(description = "Modelo do veículo", example = "Corsa") String modelo,
        @Schema(description = "ID do condutor associado ao veículo", example = "1") Long condutorId
) {
}

