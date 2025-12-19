package com.example.exam.service;

import com.example.exam.dto.CommentDto;
import com.example.exam.entity.Comment;
import com.example.exam.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Slf4j
@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public List<CommentDto> getCommentList(Long boardId) {
        return commentRepository.findByBoard_id(boardId)
                .stream()
                .map(CommentDto::toDto)
                .toList();
    }
    public CommentDto insertComment(CommentDto commentDto) {
        Comment saved = commentRepository.save(commentDto.toEntity());
        return CommentDto.toDto(saved);
    }
    // 댓글 수정
    @Transactional
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("수정 실패! 댓글이 없습니다."));

        try {
            comment.setContent(commentDto.getContent()); // 내용만 수정
            Comment updated = commentRepository.save(comment);
            log.info("수정된 엔티티 {}", updated.toString());
            return CommentDto.toDto(updated);
        } catch (Exception e) {
            throw new RuntimeException("DB 수정 중 오류가 발생했습니다.", e);
        }
    }

    // 댓글 삭제
    @Transactional
    public boolean deleteComment(Long id) {
        return commentRepository.findById(id)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return true;
                })
                .orElse(false);
    }



}