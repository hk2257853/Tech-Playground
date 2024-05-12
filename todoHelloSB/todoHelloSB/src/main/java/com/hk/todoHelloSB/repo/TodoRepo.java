package com.hk.todoHelloSB.repo;

import com.hk.todoHelloSB.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepo extends JpaRepository<TodoItem, Long> {
}
