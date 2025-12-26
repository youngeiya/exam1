package com.example.exam.dto;

import com.example.exam.entity.Board;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BoardForm {
    private Long id;
    private String memberid;

    private String title;
    private String content;
    private LocalDateTime createdAt;

    //DTO를 Entity로 변환
    public Board toEntity() {
        return new Board(id,memberid, title, content,createdAt);
    }

    //Entity를 DTO 로 변환
    public static BoardForm toDto(Board board) {
        return new BoardForm(board.getId(),board.getMemberid(), board.getTitle(), board.getContent(),board.getCreatedAt());
    }
}