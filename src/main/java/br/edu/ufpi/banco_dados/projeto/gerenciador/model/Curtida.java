package br.edu.ufpi.banco_dados.projeto.gerenciador.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "curtidas", uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario", "id_postagem"}))
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_postagem")
    private Postagem postagem;

    @Column(name = "data_curtida")
    private OffsetDateTime dataCurtida;

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Postagem getPostagem() {
        return postagem;
    }

    public OffsetDateTime getDataCurtida() {
        return dataCurtida;
    }

}
