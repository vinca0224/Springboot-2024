package com.vinca.spring02.controller;

import java.util.List;
import org.springframework.stereotype.Controller;

import com.vinca.spring02.domain.Todo;
import com.vinca.spring02.service.TodoService;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TodoController {
    
    @Resource
    TodoService todoService;

    //localhot:8091/todos 로 요청하면 실행
    @GetMapping("/todos")
    public List<Todo> getMethodName() throws Exception{
        List<Todo> allTodos = todoService.getTodos();
        return allTodos;
    }
}
