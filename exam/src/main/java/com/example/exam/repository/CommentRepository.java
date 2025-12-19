package com.example.exam.repository;

import com.example.exam.dto.CommentDto;
import com.example.exam.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard_id(Long board_id);
}

