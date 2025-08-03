package com.yourcompany.workforcemgmt.controller;

import com.yourcompany.workforcemgmt.dto.CreateTaskRequest;
import com.yourcompany.workforcemgmt.dto.TaskDto;
import com.yourcompany.workforcemgmt.mapper.TaskMapper;
import com.yourcompany.workforcemgmt.model.Task;
import com.yourcompany.workforcemgmt.model.Priority;
import com.yourcompany.workforcemgmt.service.TaskService;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    // Create a new task
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskRequest request) {
        Task task = taskMapper.fromRequest(request);
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.ok(taskMapper.toDto(savedTask));
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> list = taskService.getAllTasks().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable String id) {
        Task task = taskService.getTaskById(id);
        if (task == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(taskMapper.toDto(task));
    }

    // Get tasks by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskDto>> getByPriority(@PathVariable Priority priority) {
        List<TaskDto> list = taskService.getTasksByPriority(priority).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // Smart daily view
    @GetMapping("/smart")
    public ResponseEntity<List<TaskDto>> getSmartTasks(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<TaskDto> list = taskService.getSmartTasks(start, end).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // Get tasks for a staff by date range (excluding cancelled)
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<TaskDto>> getByDateAndStaff(
            @PathVariable String staffId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<TaskDto> list = taskService.getTasksByDateRange(staffId, start, end).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // Reassign task
    @PutMapping("/{id}/reassign")
    public ResponseEntity<TaskDto> reassign(@PathVariable String id, @RequestParam String newStaffId) {
        Task updatedTask = taskService.reassignTask(id, newStaffId);
        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }

    // Update priority
    @PutMapping("/{id}/priority")
    public ResponseEntity<TaskDto> updatePriority(@PathVariable String id, @RequestParam Priority priority) {
        Task updatedTask = taskService.updatePriority(id, priority);
        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }

    // Add comment
    @PostMapping("/{id}/comment")
    public ResponseEntity<TaskDto> addComment(@PathVariable String id, @RequestBody String comment) {
        Task updatedTask = taskService.addComment(id, comment);
        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
    }
}
