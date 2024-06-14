package com.vinca.spring02.controller;

import java.util.List;

import com.vinca.spring02.domain.Todo;
import com.vinca.spring02.service.TodoService;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RestController;



@Controller
public class TodoController {
    
    @Resource
    TodoService todoService;

    //localhot:8091/todos 로 요청하면 실행
    @GetMapping("/todos")
    public String getTodos(Model model) throws Exception{
        List<Todo> allTodos = todoService.getTodos();
        model.addAttribute("todoList", allTodos);  //view에 model로 todoList 전달
        return "todolist";
    }

    //http://localhost:8091/todo/tno/1
    @GetMapping("/todo/{tno}")
    public Todo getTodo(@PathVariable("tno") int tno) throws Exception{
        return todoService.getTodo(tno);
    }
    
}
