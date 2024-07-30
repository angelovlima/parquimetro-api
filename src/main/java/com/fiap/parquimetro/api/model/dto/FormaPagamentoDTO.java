package com.fiap.parquimetro.api.model.dto;

import com.fiap.parquimetro.api.model.enums.FormaPagamento;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para registrar ou atualizar a forma de pagamento de um condutor")
public record FormaPagamentoDTO(
        @Schema(description = "ID do condutor", example = "1") Long condutorId,
        @Schema(description = "Forma de pagamento preferida do condutor", example = "CREDITO") FormaPagamento formaPagamento
) {
}
