package com.vinca.spring02.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Todo {
    private int tno;

    private String title;

    private LocalDateTime dueDate;

    private String writer;

    private int isDone; 
}
