package com.vinca.backboard.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vinca.backboard.entity.Board;
import com.vinca.backboard.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
    
    private final BoardRepository boardRepository;

    public List<Board> getList(){
        return this.boardRepository.findAll();
    }

    public Board getBoard(Long bno)throws Exception{
        Optional<Board> board = this.boardRepository.findById(bno);
        if(board.isPresent()){  // 데이터가 존재하면 실행
            return board.get();
        }else{  // 없으면 오류 발생
            throw new Exception("board not found");
        }
    }

    public void setBoard(String title, String content){
        // builder로 생성한 board 객체
        Board board = Board.builder().title(title).content(content).createDate(LocalDateTime.now()).build();
        
        this.boardRepository.save(board);
    }
}
