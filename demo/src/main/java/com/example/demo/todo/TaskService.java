package com.example.demo.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public Task addTask(@RequestBody Task task){
        return taskRepository.save(task);
    }

    public List<Task> getAll (){
        return taskRepository.findAll();
    }

    public Task updateTask(Integer id, Task task){
        Task newTask = taskRepository.getById(Long.valueOf(id));
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        return taskRepository.save(newTask);
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(Long.valueOf(id));
    }
}
