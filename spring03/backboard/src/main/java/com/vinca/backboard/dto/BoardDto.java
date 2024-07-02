package com.vinca.backboard.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    
    private Long bno;                                       // PK

    private String title;                                   // 글제목

    private String content;                                 // 글내용

    private LocalDateTime createDate;                       // 글생성일

    private LocalDateTime modifyDate;   // 24. 06. 24 수정일 추가

    private Integer hit;    // 24. 06. 26. 조회수 추가

    private String writer;

    private List<ReplyDto> replyList;

}
