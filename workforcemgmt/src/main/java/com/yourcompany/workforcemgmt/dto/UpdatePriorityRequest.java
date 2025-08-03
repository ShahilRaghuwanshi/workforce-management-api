package com.yourcompany.workforcemgmt.dto;

import com.yourcompany.workforcemgmt.model.Priority;
import lombok.Data;

@Data
public class UpdatePriorityRequest {
    private Priority priority;
}
