package com.example.exam.dto;


import com.example.exam.entity.Board;
import com.example.exam.entity.Comment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CommentDto {

    private Long id;
    private String content;
    private String name;
    private Long boardId;

    //DTO를 엔티티로 변환
    public Comment toEntity() {
        Board board = new Board(); // 엔티티 객체 생성
        board.setId(boardId);  // id만 설정
        return new Comment(id, content, name, board);
    }

    //엔티티를 DTO로 변환
    public static CommentDto toDto(Comment comment) {

        return new CommentDto(comment.getId(), comment.getContent(), comment.getName(), comment.getBoard().getId());
    }

}
