package com.example.exam.service;

import com.example.exam.ExamApplication;
import com.example.exam.dto.BoardForm;
import com.example.exam.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = ExamApplication.class)
class BoardServiceTest {

    @Autowired

    BoardService boardService;

    @Test
    void getBoardList() {
        BoardForm expected = new BoardForm(1L,"제목111","내용111");

        BoardForm dto = boardService.getBoard(1L);
    }

    @Test
    @Transactional
    void updateBoard() {
        BoardForm expected = new BoardForm(4L,"junit수정제목","junit수정내용");

        BoardForm dto = boardService.updateBoard(expected,4L);

        assertEquals(expected.toString(),dto.toString());
    }
}