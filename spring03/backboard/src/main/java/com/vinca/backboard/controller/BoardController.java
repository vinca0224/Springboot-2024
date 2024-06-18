package com.vinca.backboard.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vinca.backboard.entity.Board;
import com.vinca.backboard.service.BoardService;

import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequestMapping("/board")   //Restfuil URL은 /board로 시작
@Controller
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;    // 중간 연결책
    
    // @RequestMapping("/list", method=RequestMethod.GET) //아래와 동일기능
    // Model: controller에 있는 객체를 view로 보내주는 역할을 하는 객체
    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boardList = this.boardService.getList();
        model.addAttribute("boardList", boardList); // Thymeleaf, mustache, jsp등 view로 보내는 기능
        return "board/list";   //templates/board/list.html 렌더링 해서 리턴
    }
    
    @GetMapping("/detail/{bno}")
    public String detail(Model model, @PathVariable("bno") Long bno) throws Exception {
        Board board = this.boardService.getBoard(bno);
        model.addAttribute("board", board);
        return "board/detail";
    }
    
}
