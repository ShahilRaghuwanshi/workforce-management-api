package com.yourcompany.workforcemgmt.repository;

import com.yourcompany.workforcemgmt.model.Task;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskRepository {
    private final Map<String, Task> taskStore = new HashMap<>();

    public void save(Task task) {
        taskStore.put(task.getId(), task);
    }

    public Optional<Task> findById(String id) {
        return Optional.ofNullable(taskStore.get(id));
    }

    public List<Task> findAll() {
        return new ArrayList<>(taskStore.values());
    }

    public void delete(String id) {
        taskStore.remove(id);
    }
}
