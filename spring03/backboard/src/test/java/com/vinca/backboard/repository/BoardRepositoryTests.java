package com.vinca.backboard.repository;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// import com.vinca.backboard.entity.Board;


@SpringBootTest
public class BoardRepositoryTests {
    
    // //JUnit 테스트
    // @Autowired
    // private BoardRepository boardRepository;

    // @Test
    // void testInsertBoard(){
    //     // 전통적인 객체 생성방식
    //     Board board1 = new Board();     
    //     board1.setTitle("첫번째 테스트입니다.");
    //     board1.setContent("안녕하세요, 좋은 하루 보내세요");
    //     board1.setCreateDate(LocalDateTime.now());
    //     this.boardRepository.save(board1);

    //     // Builder를 사용한 객체 생성방식
    //     Board board2 = Board.builder().title("두번째 테스트입니다")
    //                                   .content("ㅎㅎㅎ")
    //                                   .createDate(LocalDateTime.now()).build();
    //     this.boardRepository.save(board2);

    //     System.out.println("Board 테스트 완료");
    // }

    // @Test
    // void testSelectBoard(){
    //     List<Board> all = this.boardRepository.findAll(); // select * from board
    //     assertEquals(4, all.size());             // assertEquals(기대값, 실제값)
    //     System.out.println(all.size());

    //     Board bd = all.get(0); // 게시글 중 가장 첫번째 값
    //     assertEquals(1, bd.getBno());   //첫 번째 게시글의 PK의 값이 1인지 확인
    //     System.out.println(bd.getBno());
    // }

    // @Test
    // void testUpdateBoard(){
    //     Optional<Board> bd = this.boardRepository.findById(1L); // Long 값은 뒤에 L 추가
    //     assertTrue(bd.isPresent()); // bno가 1번인 게시글이 객체에 넘어왔는지 확인
    //     Board ubd = bd.get();
    //     ubd.setContent("테스트로 수정합니다.");
    //     this.boardRepository.save(ubd);     // sava() id가 없으면 insert, 있으면 update 쿼리 자동 실행
    //     System.out.println("수정완료");
    // }

    // @Test
    // void testDeleteBoard(){
    //     Optional<Board> bd = this.boardRepository.findById(153L); // Long 값은 뒤에 L 추가
    //     assertTrue(bd.isPresent());
    //     Board dbd = bd.get();
    //     this.boardRepository.delete(dbd);
    //     System.out.println("삭제완료");
    // }
}
