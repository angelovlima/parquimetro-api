package com.fiap.parquimetro.api.repository;

import com.fiap.parquimetro.api.model.entity.Estacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long> {
}
