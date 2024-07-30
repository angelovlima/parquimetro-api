package com.fiap.parquimetro.api.repository;

import com.fiap.parquimetro.api.model.entity.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Long> {
    List<Recibo> findByVeiculoId(Long veiculoId);
}
