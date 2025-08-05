package br.edu.ufpi.banco_dados.projeto.gerenciador.dto;
public class RankingUsuarioDTO {

    private final String nome;
    private final int totalPostagens;
    private final int totalComentarios;
    private final int totalCurtidas;

    public RankingUsuarioDTO(String nome, int totalPostagens, int totalComentarios, int totalCurtidas) {
        this.nome = nome;
        this.totalPostagens = totalPostagens;
        this.totalComentarios = totalComentarios;
        this.totalCurtidas = totalCurtidas;
    }

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public int getTotalPostagens() {
        return totalPostagens;
    }

    public int getTotalComentarios() {
        return totalComentarios;
    }

    public int getTotalCurtidas() {
        return totalCurtidas;
    }
}
