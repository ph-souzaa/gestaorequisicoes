package com.seuprojeto.gestaorequisicoes.controller;

import java.util.List;
import com.seuprojeto.gestaorequisicoes.model.Servidor;
import com.seuprojeto.gestaorequisicoes.service.ServidorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servidores")
public class ServidorController {

    private final ServidorService servidorService = new ServidorService();

    @PostMapping("/adicionar")
    public ResponseEntity<String> adicionarServidor(@RequestParam String nome, @RequestParam String path) {
        Servidor novoServidor = new Servidor(nome, path);
        if (servidorService.adicionarServidor(novoServidor)) {
            return ResponseEntity.ok("Servidor adicionado com sucesso e está ativo.");
        }
        return ResponseEntity.badRequest().body("Já existe um servidor com esse nome ou não foi possível adicionar.");
    }

    @PostMapping("/parar/{nome}")
    public ResponseEntity<String> pararServidor(@PathVariable String nome) {
        if (servidorService.pararServidor(nome)) {
            return ResponseEntity.ok("Servidor " + nome + " foi parado com sucesso.");
        }
        return ResponseEntity.badRequest().body("Servidor não encontrado ou já está inativo.");
    }

    @PostMapping("/reiniciar/{nome}")
    public ResponseEntity<String> reiniciarServidor(@PathVariable String nome) {
        if (servidorService.reiniciarServidor(nome)) {
            return ResponseEntity.ok("Servidor " + nome + " foi reiniciado com sucesso.");
        }
        return ResponseEntity.badRequest().body("Servidor não encontrado ou já está ativo.");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Servidor>> listarServidores() {
        List<Servidor> servidores = servidorService.listarServidores();
        if (servidores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servidores);
    }

    @PostMapping("/incrementarRequisicao/{nome}")
    public ResponseEntity<String> incrementarRequisicao(@PathVariable String nome) {
        servidorService.adicionarRequisicao(nome);
        return ResponseEntity.ok("Requisição adicionada ao servidor " + nome);
    }

    @PostMapping("/decrementarRequisicao/{nome}")
    public ResponseEntity<String> decrementarRequisicao(@PathVariable String nome) {
        servidorService.removerRequisicao(nome);
        return ResponseEntity.ok("Requisição removida do servidor " + nome);
    }
}
