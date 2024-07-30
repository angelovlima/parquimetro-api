package com.fiap.parquimetro.api.service;

import com.fiap.parquimetro.api.exception.InvalidOperationException;
import com.fiap.parquimetro.api.exception.PaymentMethodNotSelectedException;
import com.fiap.parquimetro.api.exception.ResourceNotFoundException;
import com.fiap.parquimetro.api.model.dto.EstacionamentoDTO;
import com.fiap.parquimetro.api.model.entity.Alerta;
import com.fiap.parquimetro.api.model.entity.Estacionamento;
import com.fiap.parquimetro.api.model.entity.Recibo;
import com.fiap.parquimetro.api.model.entity.Veiculo;
import com.fiap.parquimetro.api.model.enums.FormaPagamento;
import com.fiap.parquimetro.api.repository.AlertaRepository;
import com.fiap.parquimetro.api.repository.EstacionamentoRepository;
import com.fiap.parquimetro.api.repository.ReciboRepository;
import com.fiap.parquimetro.api.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionamentoService {

    private final EstacionamentoRepository estacionamentoRepository;
    private final VeiculoRepository veiculoRepository;
    private final ReciboRepository reciboRepository;
    private final AlertaRepository alertaRepository;
    private static final double TARIFA_POR_HORA = 5.0;

    @Autowired
    public EstacionamentoService(
            EstacionamentoRepository estacionamentoRepository,
            VeiculoRepository veiculoRepository,
            ReciboRepository reciboRepository,
            AlertaRepository alertaRepository
    ) {
        this.estacionamentoRepository = estacionamentoRepository;
        this.veiculoRepository = veiculoRepository;
        this.reciboRepository = reciboRepository;
        this.alertaRepository = alertaRepository;
    }

    public EstacionamentoDTO iniciarEstacionamento(EstacionamentoDTO estacionamentoDTO) {
        Veiculo veiculo = veiculoRepository.findById(estacionamentoDTO.veiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

        if (veiculo.getCondutor().getFormaPagamento() == null) {
            throw new PaymentMethodNotSelectedException("Selecione uma forma de pagamento preferida antes de iniciar o estacionamento.");
        }

        Estacionamento estacionamento = new Estacionamento();
        estacionamento.setInicio(LocalDateTime.now());
        estacionamento.setTempoFixo(estacionamentoDTO.tempoFixo());
        estacionamento.setDuracao(estacionamentoDTO.duracao());
        estacionamento.setVeiculo(veiculo);

        if (estacionamentoDTO.tempoFixo()) {
            estacionamento.setFim(estacionamento.getInicio().plusSeconds(estacionamentoDTO.duracao()));
        }

        Estacionamento novoEstacionamento = estacionamentoRepository.save(estacionamento);
        return toDTO(novoEstacionamento);
    }

    public List<EstacionamentoDTO> listarTodosEstacionamentos() {
        return estacionamentoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EstacionamentoDTO buscarEstacionamentoPorId(Long id) {
        return estacionamentoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Estacionamento não encontrado"));
    }

    public EstacionamentoDTO atualizarEstacionamento(EstacionamentoDTO estacionamentoDTO) {
        Estacionamento estacionamento = estacionamentoRepository.findById(estacionamentoDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Estacionamento não encontrado"));

        estacionamento.setInicio(estacionamentoDTO.inicio());
        estacionamento.setFim(estacionamentoDTO.fim());
        estacionamento.setTempoFixo(estacionamentoDTO.tempoFixo());
        estacionamento.setDuracao(estacionamentoDTO.duracao());
        estacionamento.setVeiculo(veiculoRepository.findById(estacionamentoDTO.veiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado")));

        Estacionamento estacionamentoAtualizado = estacionamentoRepository.save(estacionamento);
        return toDTO(estacionamentoAtualizado);
    }

    public void deletarEstacionamento(Long id) {
        if (!estacionamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Estacionamento não encontrado");
        }
        estacionamentoRepository.deleteById(id);
    }

    @Scheduled(fixedRate = 60000) // Executa a cada 60 segundos
    public void verificarEstacionamentos() {
        List<Estacionamento> estacionamentos = estacionamentoRepository.findAll();
        LocalDateTime agora = LocalDateTime.now();

        for (Estacionamento estacionamento : estacionamentos) {
            if (estacionamento.isTempoFixo()) {
                if (estacionamento.getFim() != null && Duration.between(agora, estacionamento.getFim()).toMinutes() <= 5) {
                    enviarAlerta(estacionamento, "Seu tempo de estacionamento está prestes a expirar.");
                }

                if (estacionamento.getFim() != null && agora.isAfter(estacionamento.getFim())) {
                    gerarRecibo(estacionamento);
                    estacionamentoRepository.delete(estacionamento);
                }
            } else {
                if (Duration.between(estacionamento.getInicio(), agora).toMinutes() >= estacionamento.getDuracao() / 60) {
                    estacionamento.setDuracao(estacionamento.getDuracao() + 3600);
                    estacionamentoRepository.save(estacionamento);
                    enviarAlerta(
                            estacionamento,
                            "Seu estacionamento foi estendido automaticamente por mais uma hora."
                    );
                }
            }
        }
    }

    public EstacionamentoDTO encerrarEstacionamento(Long id) {
        Estacionamento estacionamento = estacionamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estacionamento não encontrado"));

        if (!estacionamento.isTempoFixo() && estacionamento.getVeiculo().getCondutor().getFormaPagamento() == FormaPagamento.PIX) {
            throw new InvalidOperationException(
                    "Forma de pagamento PIX não é permitida para estacionamentos variáveis. Por favor, altere sua forma de pagamento."
            );
        }

        if (!estacionamento.isTempoFixo()) {
            estacionamento.setFim(LocalDateTime.now());
        }

        gerarRecibo(estacionamento);
        estacionamentoRepository.delete(estacionamento);

        return toDTO(estacionamento);
    }

    private void gerarRecibo(Estacionamento estacionamento) {
        Recibo recibo = new Recibo();
        recibo.setDataEmissao(LocalDateTime.now());
        recibo.setDuracao(Duration.between(estacionamento.getInicio(), estacionamento.getFim()).getSeconds());

        long duracaoEmHoras = Math.max(estacionamento.getDuracao() / 3600, 1);
        recibo.setValorTotal(duracaoEmHoras * TARIFA_POR_HORA);

        recibo.setTarifa(TARIFA_POR_HORA);
        recibo.setVeiculo(estacionamento.getVeiculo());
        reciboRepository.save(recibo);

        enviarAlerta(estacionamento, "Seu estacionamento foi encerrado. Recibo gerado.");
    }

    private void enviarAlerta(Estacionamento estacionamento, String mensagem) {
        Alerta alerta = new Alerta();
        alerta.setMensagem(mensagem);
        alerta.setDataHora(LocalDateTime.now());
        alerta.setVeiculo(estacionamento.getVeiculo());
        alerta.setCondutor(estacionamento.getVeiculo().getCondutor());
        alertaRepository.save(alerta);
    }

    private EstacionamentoDTO toDTO(Estacionamento estacionamento) {
        return new EstacionamentoDTO(
                estacionamento.getId(),
                estacionamento.getInicio(),
                estacionamento.getFim(),
                estacionamento.isTempoFixo(),
                estacionamento.getDuracao(),
                estacionamento.getVeiculo().getId()
        );
    }
}
