package com.example.demo.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/add")
    public Task createTask(Task task){
        return taskService.addTask(task);
    }

    @GetMapping("/getAll")
    public List<Task> getAllTasks(){
        return taskService.getAll();
    }

    @PutMapping("/update/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }

}
