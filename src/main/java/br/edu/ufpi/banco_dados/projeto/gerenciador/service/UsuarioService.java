package br.edu.ufpi.banco_dados.projeto.gerenciador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import br.edu.ufpi.banco_dados.projeto.gerenciador.dto.RankingUsuarioDTO;

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
}

