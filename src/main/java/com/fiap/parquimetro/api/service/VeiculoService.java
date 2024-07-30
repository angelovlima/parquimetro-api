package com.fiap.parquimetro.api.service;

import com.fiap.parquimetro.api.exception.ResourceNotFoundException;
import com.fiap.parquimetro.api.model.dto.VeiculoDTO;
import com.fiap.parquimetro.api.model.entity.Condutor;
import com.fiap.parquimetro.api.model.entity.Veiculo;
import com.fiap.parquimetro.api.repository.CondutorRepository;
import com.fiap.parquimetro.api.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final CondutorRepository condutorRepository;

    @Autowired
    public VeiculoService(
            VeiculoRepository veiculoRepository,
            CondutorRepository condutorRepository
    ) {
        this.veiculoRepository = veiculoRepository;
        this.condutorRepository = condutorRepository;
    }

    public VeiculoDTO cadastrarVeiculo(VeiculoDTO veiculoDTO) {
        Condutor condutor = condutorRepository.findById(veiculoDTO.condutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado"));

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(veiculoDTO.placa());
        veiculo.setModelo(veiculoDTO.modelo());
        veiculo.setCondutor(condutor);

        Veiculo novoVeiculo = veiculoRepository.save(veiculo);
        return toDTO(novoVeiculo);
    }

    public List<VeiculoDTO> listarTodosVeiculos() {
        return veiculoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VeiculoDTO buscarVeiculoPorId(Long id) {
        return veiculoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
    }

    public VeiculoDTO editarVeiculo(VeiculoDTO veiculoDTO) {
        Veiculo veiculo = veiculoRepository.findById(veiculoDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

        Condutor condutor = condutorRepository.findById(veiculoDTO.condutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado"));

        veiculo.setPlaca(veiculoDTO.placa());
        veiculo.setModelo(veiculoDTO.modelo());
        veiculo.setCondutor(condutor);

        Veiculo veiculoAtualizado = veiculoRepository.save(veiculo);
        return toDTO(veiculoAtualizado);
    }

    public void deletarVeiculo(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Veículo não encontrado");
        }
        veiculoRepository.deleteById(id);
    }

    private VeiculoDTO toDTO(Veiculo veiculo) {
        return new VeiculoDTO(veiculo.getId(), veiculo.getPlaca(), veiculo.getModelo(), veiculo.getCondutor().getId());
    }
}
