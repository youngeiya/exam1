package com.example.exam.controller;

import com.example.exam.dto.BoardForm;
import com.example.exam.dto.BoardListDTO;
import com.example.exam.dto.CommentDto;
import com.example.exam.dto.Member;
import com.example.exam.entity.Board;
import com.example.exam.repository.BoardRepository;
import com.example.exam.service.BoardService;
import com.example.exam.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    BoardRepository boardRepository;
    //DI(의존성주입) : 미리 생성된 객체를 호출

    @Autowired
    BoardService boardService;

    @Autowired
    CommentService commentService;

    @GetMapping("/board")
    public String listBoard(@RequestParam(required = false) String search, Model model) {
        List<BoardForm> boardList = boardService.getBoardList(search);
        model.addAttribute("list", boardList);
        return "board/list";
    }

    // 2. 글쓰기 페이지 호출
    @GetMapping("/board/new")
    public String boardNew() {
        return "board/new"; // new.mustache 호출
    }

    @GetMapping("/board/input")
    public String inputBoard() {
        return "board/input";
    }

    @PostMapping("/board/create")
    public String createBoard(BoardForm form, RedirectAttributes rttr) {

        BoardForm saved = boardService.createBoard(form);
        if(saved != null) {
            rttr.addFlashAttribute("msg", "새글 등록");
            return "redirect:/board";
        }else {
            return "error/merror";
        }
    }

    @GetMapping("board/info/{id}")
    public String infoBoard(@PathVariable Long id, Model model) {

        BoardForm saved = boardService.getBoard(id);
        if (saved != null) {
            model.addAttribute("board", saved);

            // ✅ 댓글 리스트도 함께 전달
            List<CommentDto> comment = commentService.getCommentList(id);
            model.addAttribute("comment", comment);

            return "board/info";
        } else {
            return "error/merror";
        }
    }

    @GetMapping("board/edit")
    public String editBoard(@RequestParam Long id, Model model) {

        BoardForm saved = boardService.getBoard(id);
        if (saved != null) {
            model.addAttribute("board", saved);
            return "board/edit";
        } else {
            return "error/merror";
        }
    }

    @PostMapping("/board/modify")
    public String modifyBoard(BoardForm bform, RedirectAttributes rttr) {

        BoardForm updated = boardService.updateBoard(bform, bform.getId());
        if(updated != null) {
            rttr.addFlashAttribute("msg", "수정 완료");
            return "redirect:/board";
        }else {
            return "error/merror";
        }
    }

    @GetMapping("board/delete")
    public String deleteBoard(@RequestParam Long id) {

        // id를 이용하여 게시글 1개 select
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (optionalBoard.isPresent()) {
            //해당 id의 데이터가 있으면 엔티티를 받아서 삭제
            boardRepository.delete(optionalBoard.get());
            return "redirect:/board";
        } else {
            return "error/merror";
        }
    }
    @GetMapping("/board/myposts")
    public String myBoardList(HttpSession session, Model model) {
        // 세션에서 로그인한 유저 정보 가져오기
        Member loginUser = (Member) session.getAttribute("User");

        if (loginUser == null) {
            return "redirect:/member/login";
        }

        // 서비스 호출 (DTO 리스트 반환)
        List<BoardListDTO> myPosts = boardService.getMyPosts(loginUser.getId());

        model.addAttribute("posts", myPosts);
        return "board/myPosts"; // 새로 만들 머스테치/타임리프 파일
    }
}