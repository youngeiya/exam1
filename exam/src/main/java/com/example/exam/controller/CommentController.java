package com.example.exam.controller;

import com.example.exam.dto.CommentDto;
import com.example.exam.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comment/create")
    public String createComment(CommentDto commentDto){


        CommentDto saved = commentService.insertComment(commentDto);
        // board/info/id
        return "redirect:/board/info/"+saved.getBoardId();
    }

    // 댓글 수정
    @PostMapping("/comment/update")
    public String updateComment(CommentDto commentDto) {
        CommentDto updated = commentService.updateComment(commentDto.getId(), commentDto);
        return "redirect:/board/info/" + updated.getBoardId();
    }

    @PostMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable Long id,
                                @RequestParam Long board_Id) {
        commentService.deleteComment(id);
        return "redirect:/board/info/" + board_Id;
    }
}