package br.edu.ufpi.banco_dados.projeto.gerenciador.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @SuppressWarnings("unused")
    private String nome;

    @SuppressWarnings("unused")
    private String email;

    @Column(name = "data_cadastro")
    private OffsetDateTime dataCadastro;

    // Getters e setters
    public Long getId() {
        return id;
    }

    public OffsetDateTime getDataCadastro() {
        return dataCadastro;
    }
}
