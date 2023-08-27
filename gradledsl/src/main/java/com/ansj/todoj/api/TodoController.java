package com.ansj.todoj.api;

import com.ansj.todoj.api.model.TodoListResponse;
import com.ansj.todoj.api.model.TodoRequest;
import com.ansj.todoj.api.model.TodoResponse;
import com.ansj.todoj.domain.Todo;
import com.ansj.todoj.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<TodoListResponse> getAll() {
        List<Todo> todos = todoService.findAll();
        return ResponseEntity.ok(TodoListResponse.of(todos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> get(@PathVariable Long id) {
        Todo todo = todoService.findById(id);
        return ResponseEntity.ok(TodoResponse.of(todo));
    }

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
        Todo todo = todoService.create(request);
        return ResponseEntity.ok(TodoResponse.of(todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id,
                                               @RequestBody TodoRequest request) {
        Todo todo = todoService.update(id, request);
        return ResponseEntity.ok(TodoResponse.of(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}