package ru.tasktracler.tasktracker.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.tasktracler.tasktracker.domain.dto.TaskRequest;
import ru.tasktracler.tasktracker.domain.dto.TaskResponse;
import ru.tasktracler.tasktracker.domain.entity.Task;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {

//    @Mapping(source = "id", target = "taskId")
//    TaskResponse toTaskResponse(Task task);

    TaskRequest toTaskRequest(Task task);

    Task toTask(TaskRequest taskRequest);

}
