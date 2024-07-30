package com.fiap.parquimetro.api.controller;

import com.fiap.parquimetro.api.model.dto.ReciboDTO;
import com.fiap.parquimetro.api.service.ReciboService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recibos")
public class ReciboController {

    private final ReciboService reciboService;;

    @Autowired
    public ReciboController(ReciboService reciboService) {
        this.reciboService = reciboService;
    }

    @GetMapping
    public ResponseEntity<List<ReciboDTO>> listarTodosRecibos() {
        List<ReciboDTO> recibos = reciboService.listarTodosRecibos();
        return new ResponseEntity<>(recibos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReciboDTO> buscarReciboPorId(@PathVariable Long id) {
        ReciboDTO recibo = reciboService.buscarReciboPorId(id);
        return new ResponseEntity<>(recibo, HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar Recibos por ID do Veículo",
            description = "Retorna uma lista de recibos associados a um veículo específico."
    )
    @GetMapping("/veiculo/{veiculoId}")
    public ResponseEntity<List<ReciboDTO>> buscarRecibosPorIdVeiculo(@PathVariable Long veiculoId) {
        List<ReciboDTO> recibos = reciboService.buscarRecibosPorIdVeiculo(veiculoId);
        return new ResponseEntity<>(recibos, HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar Recibos por Placa do Veículo",
            description = "Retorna uma lista de recibos associados a um veículo específico pela placa."
    )
    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<ReciboDTO>> buscarRecibosPorPlacaVeiculo(@PathVariable String placa) {
        List<ReciboDTO> recibos = reciboService.buscarRecibosPorPlacaVeiculo(placa);
        return new ResponseEntity<>(recibos, HttpStatus.OK);
    }
}
