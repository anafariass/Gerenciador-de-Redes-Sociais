package br.edu.ufpi.banco_dados.projeto.gerenciador.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufpi.banco_dados.projeto.gerenciador.dto.UsuarioCurtidasDTO;
import br.edu.ufpi.banco_dados.projeto.gerenciador.service.PostagemService;

@RestController
@RequestMapping("/api/postagens")
public class PostagemController {

    @Autowired
    private PostagemService postagemService;

    @PostMapping
    public ResponseEntity<Integer> criarPostagem(@RequestParam int idUsuario, @RequestParam String conteudo) {
        int novoId = postagemService.criarPostagem(idUsuario, conteudo);
        return ResponseEntity.ok(novoId);
    }

    @GetMapping("/media-curtidas")
    public ResponseEntity<BigDecimal> mediaCurtidas() {
        return ResponseEntity.ok(postagemService.mediaCurtidas());
    }

    @GetMapping("/curtidas-por-usuario")
    public ResponseEntity<List<UsuarioCurtidasDTO>> curtidasPorUsuario() {
        return ResponseEntity.ok(postagemService.totalCurtidasPorUsuario());
    }
}

