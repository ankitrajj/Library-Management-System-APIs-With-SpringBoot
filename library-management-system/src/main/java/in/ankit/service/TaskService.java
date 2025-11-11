package in.ankit.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import in.ankit.dto.Task;
import in.ankit.entity.TaskEntity;
import in.ankit.exceptions.IdNotFoundException;
import in.ankit.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public TaskEntity createTask(TaskEntity loanTask) {
        return taskRepository.save(loanTask);
    }

    public void updateTask(TaskEntity loanTask) {
        taskRepository.save(loanTask);
    }

    public Task getById(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> new IdNotFoundException("task", id));
        return modelMapper.map(taskEntity, Task.class);
    }

    public List<Task> getAllTasks() {
        Iterable<TaskEntity> taskEntities = taskRepository.findAll();
        List<TaskEntity> taskEntityList = new ArrayList<>();
        taskEntities.forEach(taskEntityList::add);
        return taskEntityList.stream().map(task -> modelMapper.map(task, Task.class)).toList();
    }
}
