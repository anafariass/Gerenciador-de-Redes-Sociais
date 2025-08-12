package br.edu.ufpi.banco_dados.projeto.gerenciador.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import br.edu.ufpi.banco_dados.projeto.gerenciador.dto.RankingUsuarioDTO;
import br.edu.ufpi.banco_dados.projeto.gerenciador.model.Usuario;

@Service
public class UsuarioService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RankingUsuarioDTO> listarRanking() {
        String sql = "SELECT * FROM ranking_usuarios()";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new RankingUsuarioDTO(
                rs.getString("nome"),
                rs.getInt("total_postagens"),
                rs.getInt("total_comentarios"),
                rs.getInt("total_curtidas")
            ));
    }

    public void criarUsuario(br.edu.ufpi.banco_dados.projeto.gerenciador.model.Usuario usuario) {
    // Define a data de cadastro antes de inserir no banco
    usuario.setDataCadastro(OffsetDateTime.now(ZoneOffset.of("-03:00")));
    String sql = "INSERT INTO usuarios (nome, email, data_cadastro) VALUES (?, ?, ?)";
    jdbcTemplate.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getDataCadastro());
        
    }

    public int totalCurtidasPorUsuario(Long usuarioId) {
        String sql = "SELECT COUNT(*) FROM curtidas WHERE usuario_id = ?";
        Integer totalCurtidas = jdbcTemplate.queryForObject(sql, Integer.class, usuarioId);
        return totalCurtidas != null ? totalCurtidas : 0;
    }

    public void atualizarUsuario(Long id, String novoNome, String novoEmail) {
        String sql = "UPDATE usuarios SET nome = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, novoNome, novoEmail, id);
    }

    public void excluirUsuario(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Usuario> listarUsuarios() {
        String sql = "SELECT id, nome, email, data_cadastro FROM usuarios";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getLong("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setDataCadastro(rs.getObject("data_cadastro", OffsetDateTime.class));
            return usuario;
        });
    }
}

