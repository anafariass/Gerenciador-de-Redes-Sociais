package br.edu.ufpi.banco_dados.projeto.gerenciador.dto;

public class UsuarioCurtidasDTO {

    private final String nome;
    private int totalCurtidas;

    public UsuarioCurtidasDTO(String nome, int totalCurtidas) {
        this.nome = nome;
        this.totalCurtidas = totalCurtidas;
    }

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public int getTotalCurtidas() {
        return totalCurtidas;
    }

    public void setTotalCurtidas(int totalCurtidas) {
        this.totalCurtidas = totalCurtidas;
    }
}

