package br.edu.ufpi.banco_dados.projeto.gerenciador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufpi.banco_dados.projeto.gerenciador.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
