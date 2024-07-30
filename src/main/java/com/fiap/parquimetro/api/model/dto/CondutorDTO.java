package com.fiap.parquimetro.api.model.dto;

import com.fiap.parquimetro.api.model.enums.FormaPagamento;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para registrar e gerenciar condutores")
public record CondutorDTO(
        @Schema(description = "ID do condutor", example = "1") Long id,
        @Schema(description = "Nome do condutor", example = "Ângelo Lima") String nome,
        @Schema(description = "Endereço do condutor", example = "Rua Exemplo, 123") String endereco,
        @Schema(description = "Contato do condutor", example = "(12) 98765-4321") String contato,
        @Schema(description = "CPF do condutor", example = "138.496.450-96") String cpf,
        @Schema(description = "Forma de pagamento preferida do condutor", example = "CREDITO") FormaPagamento formaPagamento
) {
}
