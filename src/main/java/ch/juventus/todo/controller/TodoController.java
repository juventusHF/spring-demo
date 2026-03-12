package ch.juventus.todo.controller;

import ch.juventus.todo.model.Todo;
import ch.juventus.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todos", description = "CRUD operations for todo items")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    @Operation(summary = "Get all todos", description = "Returns all todos, optionally filtered by owner")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved todos")
    public ResponseEntity<List<Todo>> getAllTodos(
            @Parameter(description = "Filter todos by owner name") @RequestParam Optional<String> owner) {
        return ResponseEntity.ok(todoService.getAllTodos(owner));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a todo by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todo found"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    public ResponseEntity<Todo> getTodoById(
            @Parameter(description = "ID of the todo to retrieve") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(todoService.getTodoById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new todo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Todo created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<?> createTodo(@RequestBody Todo todo) {
        try {
            Todo created = todoService.createTodo(todo);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing todo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Todo updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        try {
            return ResponseEntity.ok(todoService.updateTodo(id, todo));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a todo by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Todo deleted"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

