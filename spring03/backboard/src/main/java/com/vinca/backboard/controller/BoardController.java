package com.vinca.backboard.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vinca.backboard.entity.Board;
import com.vinca.backboard.entity.Member;
import com.vinca.backboard.service.BoardService;
import com.vinca.backboard.service.MemberService;
import com.vinca.backboard.validation.BoardForm;
import com.vinca.backboard.validation.ReplyForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;





@RequestMapping("/board")   //Restfuil URL은 /board로 시작
@Controller
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;    // 중간 연결책
    private final MemberService memberService;
    
    // @RequestMapping("/list", method=RequestMethod.GET) //아래와 동일기능
    // Model: controller에 있는 객체를 view로 보내주는 역할을 하는 객체
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        // List<Board> boardList = this.boardService.getList();
        // model.addAttribute("boardList", boardList); // Thymeleaf, mustache, jsp등 view로 보내는 기능

        Page<Board> paging = this.boardService.getList(page);
        model.addAttribute("paging", paging);   // 페이징된 보드를 view로 전달
        return "board/list";   //templates/board/list.html 렌더링 해서 리턴
    }
    
    // 댓글 검증을 추가하려면 매개변수로 replyForm을 전달
    @GetMapping("/detail/{bno}")
    public String detail(Model model, @PathVariable("bno") Long bno, ReplyForm replyForm) throws Exception {
        Board board = this.boardService.getBoard(bno);
        model.addAttribute("board", board);
        return "board/detail";
    }
    
    @PreAuthorize("isAuthenticated()")  //로그인시만 작성가능
    @GetMapping("/create")
    public String create(BoardForm boardForm) {
        return "board/create";
    }
    
    @PreAuthorize("isAuthenticated()")  //로그인시만 작성가능
    @PostMapping("/create")
    public String create(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()){
            return "board/create"; // 현재 html에 그대로 머무르기
        }
        Member writer = memberService.getMember(principal.getName());   // 현재 로그인 사용자 아이디
        // this.boardService.setBoard(title, content);        
        this.boardService.setBoard(boardForm.getTitle(), boardForm.getContent(), writer);
        return "redirect:/board/list";
    }
    
}
