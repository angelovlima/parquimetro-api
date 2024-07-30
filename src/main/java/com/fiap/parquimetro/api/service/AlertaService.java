package com.fiap.parquimetro.api.service;

import com.fiap.parquimetro.api.model.dto.AlertaDTO;
import com.fiap.parquimetro.api.model.entity.Alerta;
import com.fiap.parquimetro.api.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;

    @Autowired
    public AlertaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    public List<AlertaDTO> buscarAlertasPorPlaca(String placa) {
        return alertaRepository.findByVeiculoPlaca(placa).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AlertaDTO> buscarAlertasPorCondutor(Long condutorId) {
        return alertaRepository.findByCondutorId(condutorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AlertaDTO toDTO(Alerta alerta) {
        return new AlertaDTO(
                alerta.getId(),
                alerta.getMensagem(),
                alerta.getDataHora(),
                alerta.getVeiculo().getPlaca(),
                alerta.getCondutor().getId()
        );
    }
}
