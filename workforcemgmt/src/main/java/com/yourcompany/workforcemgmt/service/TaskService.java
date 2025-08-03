package com.yourcompany.workforcemgmt.service;

import com.yourcompany.workforcemgmt.model.Task;
import com.yourcompany.workforcemgmt.model.Priority;
import com.yourcompany.workforcemgmt.model.Status;
import com.yourcompany.workforcemgmt.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final Map<String, Task> taskMap = new HashMap<>();

    // ✅ Create a new task
    public Task createTask(Task task) {
        task.setId(UUID.randomUUID().toString());
        task.setStatus(Status.ACTIVE);
        task.getActivityHistory().add("Task created.");
        taskMap.put(task.getId(), task);
        return task;
    }

    // ✅ Get all tasks
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }

    // ✅ Get task by ID
    public Task getTaskById(String id) {
        Task task = taskMap.get(id);
        if (task == null) {
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }
        return task;
    }

    // ✅ Get tasks by priority
    public List<Task> getTasksByPriority(Priority priority) {
        return taskMap.values().stream()
                .filter(t -> t.getPriority() == priority)
                .collect(Collectors.toList());
    }

    // ✅ Smart task view with priority, status, date filtering, and overdue handling
    public List<Task> getSmartTasks(LocalDate start, LocalDate end) {
        return taskMap.values().stream()
                .filter(task -> task.getStatus() == Status.ACTIVE)
                .filter(task -> task.getPriority() == Priority.HIGH || task.getPriority() == Priority.CRITICAL)
                .filter(task -> {
                    LocalDate taskStart = task.getStartDate();
                    LocalDate taskDue = task.getDueDate();
                    if (taskStart == null || taskDue == null) return false;

                    boolean inRange = !taskStart.isBefore(start) && !taskStart.isAfter(end);
                    boolean overdue = taskStart.isBefore(start) && taskDue.isBefore(LocalDate.now());

                    return inRange || overdue;
                })
                .collect(Collectors.toList());
    }

    // ✅ Get tasks for specific staff in a date range
    public List<Task> getTasksByDateRange(String staffId, LocalDate start, LocalDate end) {
        return taskMap.values().stream()
                .filter(t -> t.getStaffId().equals(staffId))
                .filter(t -> t.getStatus() != Status.CANCELLED)
                .filter(t -> {
                    LocalDate taskStart = t.getStartDate();
                    return taskStart != null &&
                           taskStart.compareTo(start) >= 0 &&
                           taskStart.compareTo(end) <= 0;
                })
                .collect(Collectors.toList());
    }

    // ✅ Reassign task to a new staff and cancel old
    public Task reassignTask(String taskId, String newStaffId) {
        Task oldTask = taskMap.get(taskId);
        if (oldTask == null) {
            throw new ResourceNotFoundException("Task not found with ID: " + taskId);
        }

        for (Task t : taskMap.values()) {
            if (t.getTitle().equalsIgnoreCase(oldTask.getTitle())
                    && t.getStatus() == Status.ACTIVE
                    && !t.getId().equals(taskId)) {
                t.setStatus(Status.CANCELLED);
                t.getActivityHistory().add("Cancelled due to reassignment of task with same title.");
            }
        }

        oldTask.setStatus(Status.CANCELLED);
        oldTask.getActivityHistory().add("Task reassigned and old task cancelled.");

        Task newTask = new Task(
                UUID.randomUUID().toString(),
                oldTask.getTitle(),
                Status.ACTIVE,
                LocalDate.now(),
                oldTask.getDueDate(),
                newStaffId,
                oldTask.getPriority(),
                new ArrayList<>(),
                new ArrayList<>(List.of("Task reassigned to new staff."))
        );
        taskMap.put(newTask.getId(), newTask);
        return newTask;
    }

    // ✅ Update task priority
    public Task updatePriority(String taskId, Priority priority) {
        Task task = taskMap.get(taskId);
        if (task == null) {
            throw new ResourceNotFoundException("Task not found with ID: " + taskId);
        }
        task.setPriority(priority);
        task.getActivityHistory().add("Priority updated to: " + priority);
        return task;
    }

    // ✅ Add comment to task
    public Task addComment(String taskId, String comment) {
        Task task = taskMap.get(taskId);
        if (task == null) {
            throw new ResourceNotFoundException("Task not found with ID: " + taskId);
        }
        task.getComments().add(comment);
        task.getActivityHistory().add("Comment added: " + comment);
        return task;
    }
} 
