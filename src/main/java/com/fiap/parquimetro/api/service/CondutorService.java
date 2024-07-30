package com.fiap.parquimetro.api.service;

import com.fiap.parquimetro.api.exception.ResourceNotFoundException;
import com.fiap.parquimetro.api.model.dto.CondutorDTO;
import com.fiap.parquimetro.api.model.dto.FormaPagamentoDTO;
import com.fiap.parquimetro.api.model.entity.Condutor;
import com.fiap.parquimetro.api.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CondutorService {

    private final CondutorRepository condutorRepository;

    @Autowired
    public CondutorService(CondutorRepository condutorRepository) {
        this.condutorRepository = condutorRepository;
    }

    public CondutorDTO registrarCondutor(CondutorDTO condutorDTO) {
        Condutor condutor = new Condutor();
        condutor.setNome(condutorDTO.nome());
        condutor.setEndereco(condutorDTO.endereco());
        condutor.setContato(condutorDTO.contato());
        condutor.setCpf(condutorDTO.cpf());
        Condutor novoCondutor = condutorRepository.save(condutor);
        return toDTO(novoCondutor);
    }

    public List<CondutorDTO> listarTodosCondutores() {
        return condutorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CondutorDTO buscarCondutorPorId(Long id) {
        return condutorRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado"));
    }

    public CondutorDTO atualizarCondutor(CondutorDTO condutorDTO) {
        Condutor condutor = condutorRepository.findById(condutorDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado"));
        condutor.setNome(condutorDTO.nome());
        condutor.setEndereco(condutorDTO.endereco());
        condutor.setContato(condutorDTO.contato());
        condutor.setCpf(condutorDTO.cpf());
        Condutor condutorAtualizado = condutorRepository.save(condutor);
        return toDTO(condutorAtualizado);
    }

    public void deletarCondutor(Long id) {
        condutorRepository.deleteById(id);
    }

    public CondutorDTO registrarFormaPagamento(FormaPagamentoDTO formaPagamentoDTO) {
        Condutor condutor = condutorRepository.findById(formaPagamentoDTO.condutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado"));

        condutor.setFormaPagamento(formaPagamentoDTO.formaPagamento());
        Condutor condutorAtualizado = condutorRepository.save(condutor);
        return toDTO(condutorAtualizado);
    }

    private CondutorDTO toDTO(Condutor condutor) {
        return new CondutorDTO(
                condutor.getId(),
                condutor.getNome(),
                condutor.getEndereco(),
                condutor.getContato(),
                condutor.getCpf(),
                condutor.getFormaPagamento()
        );
    }
}
