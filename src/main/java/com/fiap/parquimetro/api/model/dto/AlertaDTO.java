package com.fiap.parquimetro.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO para alertas de estacionamento")
public record AlertaDTO(
        @Schema(description = "ID do alerta", example = "1") Long id,
        @Schema(description = "Mensagem do alerta", example = "Seu tempo de estacionamento está prestes a expirar.") String mensagem,
        @Schema(description = "Data e hora do alerta", example = "2024-07-28T14:34:56") LocalDateTime dataHora,
        @Schema(description = "Placa do veículo associado ao alerta", example = "AAA1111") String placaVeiculo,
        @Schema(description = "ID do condutor associado ao alerta", example = "1") Long condutorId
) {
}
