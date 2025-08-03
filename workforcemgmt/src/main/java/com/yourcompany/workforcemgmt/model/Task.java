package com.yourcompany.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String id;
    private String title;
    private Status status;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String staffId;

    // Optional: For priority management and activity logging
    private Priority priority;
    private List<String> comments = new ArrayList<>();
    private List<String> activityHistory = new ArrayList<>();
}
