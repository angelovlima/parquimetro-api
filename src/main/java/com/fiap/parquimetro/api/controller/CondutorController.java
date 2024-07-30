package com.fiap.parquimetro.api.controller;

import com.fiap.parquimetro.api.model.dto.CondutorDTO;
import com.fiap.parquimetro.api.model.dto.FormaPagamentoDTO;
import com.fiap.parquimetro.api.model.enums.FormaPagamento;
import com.fiap.parquimetro.api.service.CondutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/condutores")
public class CondutorController {

    private final CondutorService condutorService;

    @Autowired
    public CondutorController(CondutorService condutorService) {
        this.condutorService = condutorService;
    }

    @Operation(
            summary = "Registrar Condutor",
            description = "Registra um novo condutor no sistema com suas informações pessoais e forma de pagamento preferida.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CondutorDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de Registro de Condutor",
                                            summary = "Exemplo de dados para registrar um condutor",
                                            value = "{ \"nome\": \"Ângelo Lima\", \"endereco\": \"Rua Exemplo, 123\", \"contato\": \"(12) 98765-4321\", \"cpf\": \"138.496.450-96\" }"
                                    )
                            }
                    )
            )
    )
    @PostMapping
    public ResponseEntity<CondutorDTO> registrarCondutor(@RequestBody CondutorDTO condutorDTO) {
        CondutorDTO novoCondutor = condutorService.registrarCondutor(condutorDTO);
        return new ResponseEntity<>(novoCondutor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CondutorDTO>> listarTodosCondutores() {
        List<CondutorDTO> condutores = condutorService.listarTodosCondutores();
        return new ResponseEntity<>(condutores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondutorDTO> buscarCondutorPorId(@PathVariable Long id) {
        CondutorDTO condutor = condutorService.buscarCondutorPorId(id);
        return new ResponseEntity<>(condutor, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CondutorDTO> atualizarCondutor(@RequestBody CondutorDTO condutorDTO) {
        CondutorDTO condutorAtualizado = condutorService.atualizarCondutor(condutorDTO);
        return new ResponseEntity<>(condutorAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCondutor(@PathVariable Long id) {
        condutorService.deletarCondutor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Registrar Forma de Pagamento",
            description = "Registra ou atualiza a forma de pagamento preferida de um condutor.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FormaPagamentoDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de Registro de Forma de Pagamento",
                                            summary = "Exemplo de dados para registrar uma forma de pagamento",
                                            value = "{ \"condutorId\": 1, \"formaPagamento\": \"CREDITO\" }"
                                    )
                            }
                    )
            )
    )
    @PostMapping("/forma-pagamento")
    public ResponseEntity<CondutorDTO> registrarFormaPagamento(@RequestBody FormaPagamentoDTO formaPagamentoDTO) {
        CondutorDTO condutorAtualizado = condutorService.registrarFormaPagamento(formaPagamentoDTO);
        return new ResponseEntity<>(condutorAtualizado, HttpStatus.OK);
    }
}