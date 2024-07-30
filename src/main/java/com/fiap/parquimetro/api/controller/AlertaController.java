package com.fiap.parquimetro.api.controller;

import com.fiap.parquimetro.api.model.dto.AlertaDTO;
import com.fiap.parquimetro.api.model.dto.CondutorDTO;
import com.fiap.parquimetro.api.model.dto.FormaPagamentoDTO;
import com.fiap.parquimetro.api.service.AlertaService;
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
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaService alertaService;

    @Autowired
    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @Operation(
            summary = "Buscar Alertas por Placa do Veículo",
            description = "Retorna uma lista de alertas associados a um veículo específico pela placa."
    )
    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<AlertaDTO>> buscarAlertasPorPlaca(@PathVariable String placa) {
        List<AlertaDTO> alertas = alertaService.buscarAlertasPorPlaca(placa);
        return new ResponseEntity<>(alertas, HttpStatus.OK);
    }

    @Operation(
            summary = "Buscar Alertas por ID do Condutor",
            description = "Retorna uma lista de alertas associados a um condutor específico."
    )
    @GetMapping("/condutor/{condutorId}")
    public ResponseEntity<List<AlertaDTO>> buscarAlertasPorCondutor(@PathVariable Long condutorId) {
        List<AlertaDTO> alertas = alertaService.buscarAlertasPorCondutor(condutorId);
        return new ResponseEntity<>(alertas, HttpStatus.OK);
    }
}