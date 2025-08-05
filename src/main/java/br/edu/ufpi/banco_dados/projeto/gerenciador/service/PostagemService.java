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
}
