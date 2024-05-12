package com.hk.todoHelloSB.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TodoItem {
    private long id;
    // @NotBlank
    private String title;
    private boolean done;

    public TodoItem() {
    }

    public TodoItem(long id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }
}
