package com.yourcompany.workforcemgmt.dto;

import com.yourcompany.workforcemgmt.model.Priority;
import com.yourcompany.workforcemgmt.model.Status;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskDto {
    private String id;
    private String title;
    private Status status;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String staffId;
    private Priority priority;
    private List<String> comments;
    private List<String> activityHistory;
}
