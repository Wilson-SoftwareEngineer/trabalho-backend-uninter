package com.uninter.tarefas.controller;

import com.uninter.tarefas.model.Tarefa;
import com.uninter.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository repository;

    
    @PostMapping
    public Tarefa criar(@RequestBody Tarefa tarefa) {
        return repository.save(tarefa);
    }

    // 2. Listar todos (GET)
    @GetMapping
    public List<Tarefa> listar() {
        return repository.findAll();
    }

    // 3. Atualizar (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa novosDados) {
        return repository.findById(id)
                .map(tarefa -> {
                    tarefa.setNome(novosDados.getNome());
                    tarefa.setDataEntrega(novosDados.getDataEntrega());
                    tarefa.setResponsavel(novosDados.getResponsavel());
                    Tarefa atualizada = repository.save(tarefa);
                    return ResponseEntity.ok(atualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Deletar (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}