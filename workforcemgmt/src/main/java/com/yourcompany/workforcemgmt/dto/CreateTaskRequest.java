package com.yourcompany.workforcemgmt.dto;

import java.time.LocalDate;

import com.yourcompany.workforcemgmt.model.Priority;
import com.yourcompany.workforcemgmt.model.Status;
import lombok.Data;

@Data
public class CreateTaskRequest {
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String staffId;
    private Priority priority;
    private Status status;
}
