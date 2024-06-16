package com.seuprojeto.gestaorequisicoes.controller;

import com.seuprojeto.gestaorequisicoes.database.Database;
import com.seuprojeto.gestaorequisicoes.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ItemController {

    @GetMapping
    public List<Item> getAllItems() {
        return Database.getAllItens();
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return Database.addItem(item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = Database.getItem(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        Item currentItem = Database.getItem(id);
        if (currentItem == null) {
            return ResponseEntity.notFound().build();
        }
        currentItem.setNome(updatedItem.getNome());
        currentItem.setDescricao(updatedItem.getDescricao());
        Database.updateItem(id, currentItem);
        return ResponseEntity.ok(currentItem);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        boolean isRemoved = Database.deleteItem(id);
        if (!isRemoved) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
