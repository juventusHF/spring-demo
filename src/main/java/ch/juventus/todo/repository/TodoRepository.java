package ch.juventus.todo.repository;

import ch.juventus.todo.model.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    List<Todo> findAll();

    Optional<Todo> findById(Long id);

    Todo save(Todo todo);

    boolean deleteById(Long id);
}
