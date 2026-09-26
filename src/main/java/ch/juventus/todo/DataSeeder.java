package ch.juventus.todo;

import ch.juventus.todo.model.Priority;
import ch.juventus.todo.model.Todo;
import ch.juventus.todo.repository.TodoRepository;
import ch.juventus.todo.service.TodoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TodoRepository todoRepository;
    private final TodoService todoService;

    public DataSeeder(TodoRepository todoRepository, TodoService todoService) {
        this.todoRepository = todoRepository;
        this.todoService = todoService;
    }

    @Override
    public void run(String... args) {
        if (todoRepository.count() > 0) {
            return;
        }
        todoService.createTodo(todo("Mike", "Buy groceries", "Milk, eggs, bread", Priority.MEDIUM));
        todoService.createTodo(todo("Anna", "Finish report", "Quarterly sales report", Priority.HIGH));
        todoService.createTodo(todo("Tom", "Clean garage", null, Priority.LOW));
    }

    private Todo todo(String owner, String title, String description, Priority priority) {
        Todo todo = new Todo();
        todo.setOwner(owner);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setPriority(priority);
        return todo;
    }
}
