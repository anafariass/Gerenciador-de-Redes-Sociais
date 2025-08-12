package br.edu.ufpi.banco_dados.projeto.gerenciador.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import br.edu.ufpi.banco_dados.projeto.gerenciador.dto.UsuarioCurtidasDTO;

@Service
public class PostagemService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer criarPostagem(int idUsuario, String conteudo) {
        return jdbcTemplate.execute(
            (CallableStatementCreator) con -> {
                CallableStatement cs = con.prepareCall("{ call criar_postagem(?, ?, ?) }");
                cs.setInt(1, idUsuario);
                cs.setString(2, conteudo);
                cs.registerOutParameter(3, Types.INTEGER);
                return cs;
            },
            (CallableStatementCallback<Integer>) cs -> {
                cs.execute();
                Integer result = (Integer) cs.getObject(3);
                if (result == null) {
                    return 0;
                }
                return result;
            }
        );
    }

    public BigDecimal mediaCurtidas() {
        return jdbcTemplate.queryForObject("SELECT media_curtidas()", BigDecimal.class);
    }

    public List<UsuarioCurtidasDTO> totalCurtidasPorUsuario() {
        String sql = """
            SELECT u.nome, COUNT(c.id) AS total
            FROM usuarios u
            JOIN curtidas c ON u.id = c.id_usuario
            GROUP BY u.nome
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new UsuarioCurtidasDTO(
                rs.getString("nome"),
                rs.getInt("total")
            ));
    }

    public void atualizarPostagem(int idPostagem, String novoConteudo) {
        String sql = "UPDATE postagens SET conteudo = ? WHERE id = ?";
        jdbcTemplate.update(sql, novoConteudo, idPostagem);
    }

    public void excluirPostagem(int idPostagem) {
        String sql = "DELETE FROM postagens WHERE id = ?";
        jdbcTemplate.update(sql, idPostagem);
    }

    public List<String> listarPostagensPorUsuario(int idUsuario) {
        String sql = "SELECT conteudo FROM postagens WHERE id_usuario = ?";
        return jdbcTemplate.query(sql, ps -> ps.setInt(1, idUsuario), (rs, rowNum) -> rs.getString("conteudo"));
    }

    public void criarComentario(int idPostagem, int idUsuario, String conteudo) {
        // Verifica se a postagem existe
        String checkSql = "SELECT COUNT(*) FROM postagens WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, idPostagem);
        if (count == null || count == 0) {
            throw new IllegalArgumentException("Erro: A postagem com o ID informado não existe.");
        }
        String sql = "INSERT INTO comentarios (id_postagem, id_usuario, conteudo) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, idPostagem, idUsuario, conteudo);
    }

    public List<String> listarComentariosDaPostagem(int idPostagem) {
        String sql = "SELECT c.conteudo, u.nome, c.data_comentario FROM comentarios c JOIN usuarios u ON c.id_usuario = u.id WHERE c.id_postagem = ? ORDER BY c.data_comentario DESC";
        return jdbcTemplate.query(sql, ps -> ps.setInt(1, idPostagem), (rs, rowNum) ->
            String.format("[%s] %s: %s", rs.getTimestamp("data_comentario"), rs.getString("nome"), rs.getString("conteudo"))
        );
    }

    public void curtirPostagem(int idPostagem, int idUsuario) {
        String sql = "INSERT INTO curtidas (id_postagem, id_usuario) VALUES (?, ?)";
        jdbcTemplate.update(sql, idPostagem, idUsuario);
    }

    public void descurtirPostagem(int idPostagem, int idUsuario) {
        String sql = "DELETE FROM curtidas WHERE id_postagem = ? AND id_usuario = ?";
        jdbcTemplate.update(sql, idPostagem, idUsuario);
    }

    public int contarPostagensPorUsuario(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM postagens WHERE id_usuario = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idUsuario);
        return count != null ? count : 0;
    }

    public int contarComentariosPorUsuario(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM comentarios WHERE id_usuario = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idUsuario);
        return count != null ? count : 0;
    }
}
