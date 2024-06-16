package com.seuprojeto.gestaorequisicoes.controller;

import com.seuprojeto.gestaorequisicoes.database.Database;
import com.seuprojeto.gestaorequisicoes.model.Item;
import com.seuprojeto.gestaorequisicoes.model.Requisicao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/requisicoes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RequisicaoController {

    @GetMapping
    public List<Requisicao> getAllRequisicoes() {
        return Database.getAllRequisicoes();
    }

    @PostMapping
    public ResponseEntity<Requisicao> createRequisicao(@RequestBody Requisicao newRequisicao) {
        Item item = Database.getItem(newRequisicao.getItem().getId());
        if (item == null) {
            return ResponseEntity.badRequest().body(null);
        }
        newRequisicao.setItem(item);
        newRequisicao.setDataCriacao(new Date());
        Requisicao createdRequisicao = Database.addRequisicao(newRequisicao);
        return ResponseEntity.ok(createdRequisicao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Requisicao> getRequisicaoById(@PathVariable Long id) {
        Requisicao requisicao = Database.getRequisicao(id);
        if (requisicao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(requisicao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Requisicao> updateRequisicao(@PathVariable Long id, @RequestBody Requisicao updatedRequisicao) {
        Requisicao currentRequisicao = Database.getRequisicao(id);
        if (currentRequisicao == null) {
            return ResponseEntity.notFound().build();
        }
        if (updatedRequisicao.getDescricao() != null) {
            currentRequisicao.setDescricao(updatedRequisicao.getDescricao());
        }
        if (updatedRequisicao.getStatus() != null) {
            currentRequisicao.setStatus(updatedRequisicao.getStatus());
        }
        if (updatedRequisicao.getItem() != null && updatedRequisicao.getItem().getId() != null) {
            Item item = Database.getItem(updatedRequisicao.getItem().getId());
            if (item != null) {
                currentRequisicao.setItem(item);
            }
        }
        Database.updateRequisicao(id, currentRequisicao);
        return ResponseEntity.ok(currentRequisicao);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequisicao(@PathVariable Long id) {
        boolean isRemoved = Database.deleteRequisicao(id);
        if (!isRemoved) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
    
    
    @PutMapping("/{id}/atendimento")
    public ResponseEntity<Requisicao> setStatusAtendimento(@PathVariable Long id) {
        Requisicao requisicao = Database.getRequisicao(id);
        if (requisicao == null) {
            return ResponseEntity.notFound().build();
        }
        requisicao.setStatus("Em Atendimento");
        Database.updateRequisicao(id, requisicao);
        return ResponseEntity.ok(requisicao);
    }

    @PutMapping("/{id}/fechado")
    public ResponseEntity<Requisicao> setStatusFechado(@PathVariable Long id) {
        Requisicao requisicao = Database.getRequisicao(id);
        if (requisicao == null) {
            return ResponseEntity.notFound().build();
        }
        requisicao.setStatus("Fechado");
        Database.updateRequisicao(id, requisicao);
        return ResponseEntity.ok(requisicao);
    }
}
