package com.fiap.parquimetro.api.controller;

import com.fiap.parquimetro.api.model.dto.VeiculoDTO;
import com.fiap.parquimetro.api.service.VeiculoService;
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
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @Autowired
    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @Operation(
            summary = "Registrar Veículo",
            description = "Registra um novo veículo no sistema associado a um condutor.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VeiculoDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo de Registro de Veículo",
                                            summary = "Exemplo de dados para registrar um veículo",
                                            value = "{ \"placa\": \"AAA-1111\", \"modelo\": \"Corsa\", \"condutorId\": 1 }"
                                    )
                            }
                    )
            )
    )
    @PostMapping
    public ResponseEntity<VeiculoDTO> registrarVeiculo(@RequestBody VeiculoDTO veiculoDTO) {
        VeiculoDTO novoVeiculo = veiculoService.cadastrarVeiculo(veiculoDTO);
        return new ResponseEntity<>(novoVeiculo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> listarTodosVeiculos() {
        List<VeiculoDTO> veiculos = veiculoService.listarTodosVeiculos();
        return new ResponseEntity<>(veiculos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> buscarVeiculoPorId(@PathVariable Long id) {
        VeiculoDTO veiculo = veiculoService.buscarVeiculoPorId(id);
        return new ResponseEntity<>(veiculo, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<VeiculoDTO> atualizarVeiculo(@RequestBody VeiculoDTO veiculoDTO) {
        VeiculoDTO veiculoAtualizado = veiculoService.editarVeiculo(veiculoDTO);
        return new ResponseEntity<>(veiculoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id) {
        veiculoService.deletarVeiculo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
