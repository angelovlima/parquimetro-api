package com.fiap.parquimetro.api.controller;

import com.fiap.parquimetro.api.model.dto.EstacionamentoDTO;
import com.fiap.parquimetro.api.service.EstacionamentoService;
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
@RequestMapping("/api/estacionamentos")
public class EstacionamentoController {

    private final EstacionamentoService estacionamentoService;

    @Autowired
    public EstacionamentoController(EstacionamentoService estacionamentoService) {
        this.estacionamentoService = estacionamentoService;
    }

    @Operation(
            summary = "Iniciar Estacionamento",
            description = "Inicia um novo período de estacionamento. Pode ser por tempo fixo ou por hora.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EstacionamentoDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Estacionamento por Tempo Fixo",
                                            summary = "Exemplo de estacionamento por tempo fixo",
                                            value = "{ \"tempoFixo\": true, \"duracao\": 3600, \"veiculoId\": 1 }"
                                    ),
                                    @ExampleObject(
                                            name = "Estacionamento por Hora",
                                            summary = "Exemplo de estacionamento por hora",
                                            value = "{ \"tempoFixo\": false, \"veiculoId\": 1 }"
                                    )
                            }
                    )
            )
    )
    @PostMapping
    public ResponseEntity<EstacionamentoDTO> iniciarEstacionamento(@RequestBody EstacionamentoDTO estacionamentoDTO) {
        EstacionamentoDTO novoEstacionamento = estacionamentoService.iniciarEstacionamento(estacionamentoDTO);
        return new ResponseEntity<>(novoEstacionamento, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EstacionamentoDTO>> listarTodosEstacionamentos() {
        List<EstacionamentoDTO> estacionamentos = estacionamentoService.listarTodosEstacionamentos();
        return new ResponseEntity<>(estacionamentos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstacionamentoDTO> buscarEstacionamentoPorId(@PathVariable Long id) {
        EstacionamentoDTO estacionamento = estacionamentoService.buscarEstacionamentoPorId(id);
        return new ResponseEntity<>(estacionamento, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<EstacionamentoDTO> atualizarEstacionamento(@RequestBody EstacionamentoDTO estacionamentoDTO) {
        EstacionamentoDTO estacionamentoAtualizado = estacionamentoService.atualizarEstacionamento(estacionamentoDTO);
        return new ResponseEntity<>(estacionamentoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstacionamento(@PathVariable Long id) {
        estacionamentoService.deletarEstacionamento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Encerrar Estacionamento",
            description = "Encerra um período de estacionamento em andamento. Para estacionamentos variáveis, verifica se a forma de pagamento é compatível."
    )
    @PostMapping("/{id}/encerrar")
    public ResponseEntity<EstacionamentoDTO> encerrarEstacionamento(@PathVariable Long id) {
        EstacionamentoDTO estacionamentoEncerrado = estacionamentoService.encerrarEstacionamento(id);
        return new ResponseEntity<>(estacionamentoEncerrado, HttpStatus.OK);
    }
}
