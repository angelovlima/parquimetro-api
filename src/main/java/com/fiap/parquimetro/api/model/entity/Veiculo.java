package com.fiap.parquimetro.api.model.entity;

import com.fiap.parquimetro.api.validation.Placa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "veiculos")
@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Placa
    private String placa;

    private String modelo;

    @ManyToOne
    @JoinColumn(name = "condutor_id")
    private Condutor condutor;
}
