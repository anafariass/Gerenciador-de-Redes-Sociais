package br.edu.ufpi.banco_dados.projeto.gerenciador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufpi.banco_dados.projeto.gerenciador.dto.RankingUsuarioDTO;
import br.edu.ufpi.banco_dados.projeto.gerenciador.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import br.edu.ufpi.banco_dados.projeto.gerenciador.model.Usuario;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingUsuarioDTO>> getRanking() {
        return ResponseEntity.ok(usuarioService.listarRanking());
    }
    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody Usuario usuario) {
        usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok("Usuário criado com sucesso");
        
    }
    
}

