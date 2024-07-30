package com.fiap.parquimetro.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Duration;
import java.time.LocalDateTime;

public record EstacionamentoDTO(
        @Schema(description = "ID do estacionamento", example = "1") Long id,
        @Schema(description = "Data e hora de início do estacionamento", example = "2024-07-28T12:34:56") LocalDateTime inicio,
        @Schema(description = "Data e hora de fim do estacionamento", example = "2024-07-28T14:34:56") LocalDateTime fim,
        @Schema(description = "Indica se o estacionamento é por tempo fixo", example = "true") boolean tempoFixo,
        @Schema(description = "Duração do estacionamento em segundos", example = "3600") long duracao,
        @Schema(description = "ID do veículo associado ao estacionamento", example = "1") Long veiculoId
) {
}
