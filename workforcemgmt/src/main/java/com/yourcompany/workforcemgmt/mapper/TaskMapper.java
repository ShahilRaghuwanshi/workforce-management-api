package com.yourcompany.workforcemgmt.mapper;

import com.yourcompany.workforcemgmt.dto.CreateTaskRequest;
import com.yourcompany.workforcemgmt.dto.TaskDto;
import com.yourcompany.workforcemgmt.model.Task;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TaskMapper {

    
   
    Task fromRequest(CreateTaskRequest request);

    
    TaskDto toDto(Task task);
}
