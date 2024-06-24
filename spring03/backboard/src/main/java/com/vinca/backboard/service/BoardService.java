package com.vinca.backboard.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

// 복합쿼리 생성용 
import org.springframework.data.jpa.domain.Specification;

import com.vinca.backboard.common.NotFoundException;
import com.vinca.backboard.entity.Board;
import com.vinca.backboard.entity.Member;
import com.vinca.backboard.entity.Reply;
import com.vinca.backboard.repository.BoardRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
    
    private final BoardRepository boardRepository;

    public List<Board> getList(){
        return this.boardRepository.findAll();
    }

    // 페이징 되는 리스트 메서드
    public Page<Board> getList(int page){
        List<Sort.Order> sorts =  new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));   // pagesize를 동적으로도 변경할 수 있음
        return this.boardRepository.findAll(pageable);
    }

    // 24. 06. 24. 검색 추가 메서드
    public Page<Board> getList(int page, String keyword){
        List<Sort.Order> sorts =  new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));   // pagesize를 동적으로도 변경할 수 있음

        Specification<Board> spec = searchBoard(keyword);
        return this.boardRepository.findAll(spec, pageable);
    }

    public Board getBoard(Long bno){
        Optional<Board> board = this.boardRepository.findById(bno);
        if(board.isPresent()){  // 데이터가 존재하면 실행
            return board.get();
        }else{  // 없으면 오류 발생
            throw new NotFoundException("board not found");
        }
    }

    public void setBoard(String title, String content, Member writer){
        // builder로 생성한 board 객체
        Board board = Board.builder().title(title).content(content).createDate(LocalDateTime.now()).build();
        board.setWriter(writer);

        this.boardRepository.save(board);   // PK가 없으면 INSERT
    }

    // 24.06.24 modBoard 추가 작성
    public void modBoard(Board board, String title, String content){
        board.setTitle(title);
        board.setContent(content);
        board.setModifyDate(LocalDateTime.now());

        this.boardRepository.save(board);   //PK가 있으면 UPDATE
    }

    public void remBoard(Board board){
        this.boardRepository.delete(board);
    }

    public Specification<Board> searchBoard(String keyword){
        return new Specification<Board>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb){
                // query를 JPA로 생성
                query.distinct(true);   // 중복제거
                Join<Board, Reply> r= b.join("replyList", JoinType.LEFT);

                return cb.or(cb.like(b.get("title"), "%" + keyword + "%"), // 게시글 제목에서 검색
                             cb.like(b.get("content"), "%" + keyword + "%" ), // 게시글 내용에서 검색
                             cb.like(r.get("content"), "%" + keyword + "%") // 댓글 내용에서 검색
                            );
            }
        };
    }
}
