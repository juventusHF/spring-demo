package ch.juventus.todo.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a todo item")
public class Todo {

    @Schema(description = "Unique identifier of the todo", example = "1")
    private Long id;

    @Schema(description = "Owner of the todo", example = "Mike")
    private String owner;

    @Schema(description = "Title of the todo", example = "Buy groceries")
    private String title;

    @Schema(description = "Detailed description of the todo", example = "Milk, eggs, bread")
    private String description;

    @Schema(description = "Whether the todo has been completed", example = "false")
    private boolean completed;

    public Todo() {
    }

    public Todo(Long id, String owner, String title, String description, boolean completed) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

