package com.fiap.parquimetro.api.repository;

import com.fiap.parquimetro.api.model.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByVeiculoPlaca(String placa);
    List<Alerta> findByCondutorId(Long condutorId);
}
