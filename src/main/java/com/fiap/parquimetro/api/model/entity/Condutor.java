package com.fiap.parquimetro.api.model.entity;

import com.fiap.parquimetro.api.model.enums.FormaPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "condutores")
@Entity
public class Condutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String endereco;
    private String contato;
    @CPF(message = "CPF inválido")
    private String cpf;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "condutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Veiculo> veiculos;
}
