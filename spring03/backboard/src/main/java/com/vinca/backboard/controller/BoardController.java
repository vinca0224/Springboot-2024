package com.vinca.backboard.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.vinca.backboard.entity.Board;
import com.vinca.backboard.entity.Member;
import com.vinca.backboard.service.BoardService;
import com.vinca.backboard.service.MemberService;
import com.vinca.backboard.validation.BoardForm;
import com.vinca.backboard.validation.ReplyForm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@RequestMapping("/board")   //Restfuil URL은 /board로 시작
@Controller
@RequiredArgsConstructor
@Log4j2
public class BoardController {
    
    private final BoardService boardService;    // 중간 연결책
    private final MemberService memberService;
    
    // @RequestMapping("/list", method=RequestMethod.GET) //아래와 동일기능
    // Model: controller에 있는 객체를 view로 보내주는 역할을 하는 객체
    // @GetMapping("/list")
    // public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
    //     // List<Board> boardList = this.boardService.getList();
    //     // model.addAttribute("boardList", boardList); // Thymeleaf, mustache, jsp등 view로 보내는 기능

    //     Page<Board> paging = this.boardService.getList(page);
    //     model.addAttribute("paging", paging);   // 페이징된 보드를 view로 전달
    //     return "board/list";   //templates/board/list.html 렌더링 해서 리턴
    // }

    // 24. 06. 24. list 새로 변경
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String keyword) {
        Page<Board> paging = this.boardService.getList(page, keyword);  // 검색 추가
        model.addAttribute("paging", paging);
        model.addAttribute("kw", keyword);
        return "board/list";
    }
    
    // 댓글 검증을 추가하려면 매개변수로 replyForm을 전달
    @GetMapping("/detail/{bno}")
    public String detail(Model model, @PathVariable("bno") Long bno, ReplyForm replyForm, HttpServletRequest request) throws Exception {
        // 이전 페이지 변수에 담기
        String prevUrl = request.getHeader("referer");
        log.info(String.format("현재 이전 페이지 : %s", prevUrl));
        Board board = this.boardService.getBoard(bno);
        model.addAttribute("board", board);
        model.addAttribute("prevUrl", prevUrl); // 이전 페이지 URL 뷰에 전달
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
    
    @PreAuthorize("isAuthenticated()")  //로그인시만 작성가능
    @GetMapping("/modify/{bno}")
    public String modify(BoardForm boardForm, @PathVariable("bno") long bno, Principal principal) {
        Board board = this.boardService.getBoard(bno);  // 기존 게시글 가져옴

        if(!board.getWriter().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        
        boardForm.setTitle(board.getTitle());
        boardForm.setContent((board.getContent()));
        return "board/create";  // 게시글 등록하는 화면을 수정할 때 그대로 사용
    }

    // 수정
    @PreAuthorize("isAuthenticated()")  //로그인시만 작성가능
    @PostMapping("/modify/{bno}")
    public String modify(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal, @PathVariable("bno") Long bno) {
        if(bindingResult.hasErrors()){
            return "board/create"; // 현재 html에 그대로 머무르기
        }
        Board board = this.boardService.getBoard(bno); 
        if(!board.getWriter().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.boardService.modBoard(board, boardForm.getTitle(), boardForm.getContent());
        return String.format("redirect:/board/detail/%s", bno);
    }
    
    // 삭제
    @PreAuthorize("isAuthenticated()")  //로그인시만 작성가능
    @GetMapping("/delete/{bno}")
    public String delete(@PathVariable("bno") Long bno, Principal principal) {
        Board board = this.boardService.getBoard(bno);
        if(!board.getWriter().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.boardService.remBoard(board);
        return "redirect:/";
    }
    
}
