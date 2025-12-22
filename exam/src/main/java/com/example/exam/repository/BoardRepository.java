package com.example.exam.repository;

import com.example.exam.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContainingOrContentContaining(String search, String search1);
    List<Board> findTop5ByOrderByIdDesc();
}
