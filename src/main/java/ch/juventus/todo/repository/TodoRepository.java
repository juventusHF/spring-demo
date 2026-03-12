package ch.juventus.todo.repository;

import ch.juventus.todo.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TodoRepository {

    private final Map<Long, Todo> store = new LinkedHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public List<Todo> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Todo save(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(idSequence.getAndIncrement());
        }
        store.put(todo.getId(), todo);
        return todo;
    }

    public boolean deleteById(Long id) {
        return store.remove(id) != null;
    }
}

