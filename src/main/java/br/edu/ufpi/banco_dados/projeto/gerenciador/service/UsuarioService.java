package br.edu.ufpi.banco_dados.projeto.gerenciador.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

    public void criarUsuario(br.edu.ufpi.banco_dados.projeto.gerenciador.model.Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, data_cadastro) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getDataCadastro());
        usuario.setDataCadastro(OffsetDateTime.now(ZoneOffset.of("-03:00")));
        
    }
}

