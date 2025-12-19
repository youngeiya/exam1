package com.example.exam.service;

import com.example.exam.dto.BoardForm;
import com.example.exam.entity.Board;
import com.example.exam.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;
    @Transactional(readOnly = true)
    public List<BoardForm> getBoardList(String search) {
        List<Board> boards;

        if (search == null || search.isBlank()) {
            // 검색어 없으면 전체 조회
            boards = boardRepository.findAll();
        } else {
            // 검색어 있으면 제목 또는 내용에 포함된 글만 조회
            boards = boardRepository.findByTitleContainingOrContentContaining(search, search);
        }

        return boards.stream()
                .map(BoardForm::toDto)
                .toList();
    }

//    public BoardForm createBoard(BoardForm form) {
//        try {
//            log.info("DTO 객체 {}",form.toString());
//            Board board = boardRepository.save(form.toEntity());
//            log.info("엔티티 객체 {}", board.toString());
//            return BoardForm.toDto(board);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//@Transactional
//public BoardForm updateBoard(BoardForm form)  {
//    log.info("DTO 객체 {}",form.toString());
//        /*트랜잭션 테스트
//        Board aaa = form.toEntity();
//        aaa.setId(null);
//        boardRepository.save(aaa)
//         */
//    // 1. 기존 내용이 있는지 확인
//    Board board = boardRepository.findById(form.getId())
//            .orElseThrow(() -> new IllegalArgumentException("수정 실패! 대상 글이 없습니다."));
//
//    Board updated = boardRepository.save(form.toEntity());
//    log.info("엔티티 객체 {}", updated.toString());
//    // 2. 엔티티를 DTO 로 변환 해서 리턴
//    return BoardForm.toDto(updated);
//}
//

    public BoardForm createBoard(BoardForm form) {
// 1) 유효성 검증

        if (form.getTitle() == null || form.getTitle().isBlank()) {
            throw new IllegalArgumentException("제목은 필수 입력값입니다.");
        }

        if (form.getContent() == null || form.getContent().isBlank()) {
            throw new IllegalArgumentException("내용은 필수 입력값입니다.");
        }

        try {
            log.info("DTO 객체 {}", form.toString());
            Board board = boardRepository.save(form.toEntity());
            log.info("엔티티 객체 {}", board.toString());
            return BoardForm.toDto(board);

        } catch (Exception e) {
            throw new RuntimeException("DB 저장 중 오류가 발생했습니다.", e);
        }
    }
    public BoardForm getBoard(Long id) {
        // id를 이용하여 게시글 1개 select
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (optionalBoard.isPresent()) {
            return BoardForm.toDto(optionalBoard.get());
        } else {
            return null;
        }
    }
        @Transactional
        public BoardForm updateBoard(BoardForm form, Long id) {
            log.info("DTO 객체 {}",form.toString());
            // 1. 기존 내용이 있는지 확인
            Board board = boardRepository.findById(id)
                    .orElseThrow(() ->
                            new IllegalArgumentException("수정 실패! 대상 글이 없습니다."));
            try {
                Board updated = boardRepository.save(form.toEntity());
                log.info("엔티티 객체 {}", updated.toString());
            // 2. 엔티티를 DTO 로 변환 해서 리턴
                return BoardForm.toDto(updated);
            } catch (Exception e) {
                throw new RuntimeException("DB 수정 중 오류가 발생했습니다.", e);
            }
        }
//    @Transactional
//    public boolean deleteBoard(Long id) {
//        // 1. 대상 엔티티 조회
//        Optional<Board> target = boardRepository.findById(id);
//
//        // 2. 대상이 있으면 삭제
//        if (target.isPresent()) {
//            Board board = target.get();
//            boardRepository.delete(board);
//            log.info("{}번 게시글이 삭제되었습니다.", id);
//            return true; // 삭제 성공
//        } else {
//            // 대상이 없으면 삭제 실패
//            log.warn("삭제 실패! {}번 게시글이 존재하지 않습니다.", id);
//            return false; // 삭제 실패
//        }
//    }
}
