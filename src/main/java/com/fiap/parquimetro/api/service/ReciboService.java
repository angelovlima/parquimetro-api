package com.fiap.parquimetro.api.service;

import com.fiap.parquimetro.api.exception.ResourceNotFoundException;
import com.fiap.parquimetro.api.model.dto.ReciboDTO;
import com.fiap.parquimetro.api.model.entity.Recibo;
import com.fiap.parquimetro.api.model.entity.Veiculo;
import com.fiap.parquimetro.api.repository.ReciboRepository;
import com.fiap.parquimetro.api.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReciboService {

    private final ReciboRepository reciboRepository;
    private final VeiculoRepository veiculoRepository;

    @Autowired
    public ReciboService(
            ReciboRepository reciboRepository,
            VeiculoRepository veiculoRepository
    ) {
        this.reciboRepository = reciboRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public List<ReciboDTO> listarTodosRecibos() {
        return reciboRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReciboDTO buscarReciboPorId(Long id) {
        return reciboRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Recibo não encontrado"));
    }

    public List<ReciboDTO> buscarRecibosPorIdVeiculo(Long veiculoId) {
        if (!veiculoRepository.existsById(veiculoId)) {
            throw new ResourceNotFoundException("Veículo não encontrado");
        }
        return reciboRepository.findByVeiculoId(veiculoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReciboDTO> buscarRecibosPorPlacaVeiculo(String placa) {
        Veiculo veiculo = veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
        return reciboRepository.findByVeiculoId(veiculo.getId()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ReciboDTO toDTO(Recibo recibo) {
        return new ReciboDTO(
                recibo.getId(),
                recibo.getDataEmissao(),
                recibo.getDuracao(),
                recibo.getTarifa(),
                recibo.getValorTotal(),
                recibo.getVeiculo().getId()
        );
    }
}
