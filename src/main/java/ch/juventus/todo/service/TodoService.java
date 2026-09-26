package ch.juventus.todo.service;

import ch.juventus.todo.model.Priority;
import ch.juventus.todo.model.Todo;
import ch.juventus.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos(Optional<String> owner, Optional<Priority> priority) {
        return todoRepository.findAll().stream()
                .filter(todo -> owner.isEmpty() || todo.getOwner().equalsIgnoreCase(owner.get()))
                .filter(todo -> priority.isEmpty() || todo.getPriority() == priority.get())
                .toList();
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Todo not found with id: " + id));
    }

    public Todo createTodo(Todo todo) {
        if (!isValid(todo)) {
            throw new IllegalArgumentException("Todo owner and title must not be blank");
        }
        todo.setId(null); // ensure a new ID is assigned
        todo.setCompleted(false);
        if (todo.getPriority() == null) {
            todo.setPriority(Priority.LOW);
        }
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo updated) {
        Todo existing = getTodoById(id);
        if (!isValid(updated)) {
            throw new IllegalArgumentException("Todo owner and title must not be blank");
        }
        existing.setOwner(updated.getOwner());
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCompleted(updated.isCompleted());
        existing.setPriority(updated.getPriority() != null ? updated.getPriority() : Priority.LOW);
        return todoRepository.save(existing);
    }

    public void deleteTodo(Long id) {
        if (!todoRepository.deleteById(id)) {
            throw new NoSuchElementException("Todo not found with id: " + id);
        }
    }

    private boolean isValid(Todo todo) {
        boolean hasOwner = todo.getOwner() != null && !todo.getOwner().isBlank();
        boolean hasTitle = todo.getTitle() != null && !todo.getTitle().isBlank();
        return hasOwner && hasTitle;
    }
}
