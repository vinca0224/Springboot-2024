package com.vinca.backboard.restcontroller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vinca.backboard.dto.BoardDto;
import com.vinca.backboard.dto.Header;
import com.vinca.backboard.dto.PagingDto;
import com.vinca.backboard.dto.ReplyDto;
import com.vinca.backboard.entity.Board;
import com.vinca.backboard.entity.Category;
import com.vinca.backboard.entity.Reply;
import com.vinca.backboard.service.BoardService;
import com.vinca.backboard.service.CategoryService;
import com.vinca.backboard.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
@Log4j2
public class RestBoardController {
    
    private final BoardService boardService;    // 중간 연결책
    private final MemberService memberService;  // 사용자 정보
    private final CategoryService categoryService;

     @GetMapping("/list/{category}")
     @ResponseBody
    public Header<List<BoardDto>> list(
                       @PathVariable(value = "category") String category,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String keyword) {

        Category cate = this.categoryService.getCategory(category);
        Page<Board> pages = this.boardService.getList(page, keyword, cate);  // 검색 및 카테고리 추가
        // List<Board> list = pages.getContent();
        PagingDto paging = new PagingDto(pages.getTotalElements(), pages.getNumber()+1, 10, 10);

        List<BoardDto> result = new ArrayList<BoardDto>();
        Long curNum = pages.getTotalElements() - pages.getNumber() * 10;
        for (Board origin : pages){
            List<ReplyDto> subList = new ArrayList<>();

            BoardDto bdDto = new BoardDto();
            // 게시글 번호를 추가
            bdDto.setNum(curNum--);
            bdDto.setBno(origin.getBno());
            bdDto.setTitle(origin.getTitle());
            bdDto.setContent(origin.getContent());
            bdDto.setCreateDate(origin.getCreateDate());
            bdDto.setModifyDate(origin.getModifyDate());
            bdDto.setWriter(origin.getWriter() != null ? origin.getWriter().getUsername() : "");  // null 에러
            bdDto.setHit(origin.getHit());

            if (origin.getReplyList().size() > 0){
                for(Reply reply : origin.getReplyList()){
                    ReplyDto replyDto = new ReplyDto();
                    replyDto.setRno(reply.getRno());
                    replyDto.setContent(reply.getContent());
                    replyDto.setCreateDate(reply.getCreateDate());
                    replyDto.setModifyDate(reply.getModifyDate());
                    replyDto.setWriter(reply.getWriter() != null ? reply.getWriter().getUsername() : "");

                    subList.add(replyDto);
                }

                bdDto.setReplyList(subList);
            }

            result.add(bdDto);
        }
        log.info(String.format("▶▶▶▶▶ result 게시글 수 %s", result.size()));
        // model.addAttribute("pages", pages);
        // model.addAttribute("kw", keyword);
        // model.addAttribute("category", category);

        // Header<> 에 담아줌
        Header<List<BoardDto>> last = Header.OK(result,paging);
        return last;
    }

    @GetMapping("/detail/{bno}")
    @ResponseBody
    public BoardDto detail(@PathVariable("bno") Long bno, HttpServletRequest request) throws Exception {
        // 이전 페이지 변수에 담기
        String prevUrl = request.getHeader("referer");
        log.info(String.format("현재 이전 페이지 : %s", prevUrl));
        // Board board = this.boardService.getBoard(bno);
        Board _board = this.boardService.hitBoard(bno);  // 조회수 증가후 리턴
        BoardDto board = BoardDto.builder().bno(_board.getBno()).title(_board.getTitle())
                                           .content(_board.getContent()).createDate(_board.getCreateDate())
                                           .modifyDate(_board.getModifyDate())
                                           .writer(_board.getWriter() != null ? _board.getWriter().getUsername() : "")
                                           .build();
                                           
        List<ReplyDto> replyList = new ArrayList<>();
        if (_board.getReplyList().size() > 0) {
            _board.getReplyList().forEach(reply -> replyList.add(ReplyDto.builder().content(reply.getContent())
                                                            .createDate(reply.getCreateDate()).modifyDate(reply.getModifyDate())
                                                            .rno(reply.getRno())
                                                            .writer(reply.getWriter() != null ? reply.getWriter().getUsername() : "")
                                                            .build()));
        }
        board.setReplyList(replyList);                                            
        // model.addAttribute("board", board);
        // model.addAttribute("prevUrl", prevUrl); // 이전 페이지 URL 뷰에 전달
        return board;
    }
}
