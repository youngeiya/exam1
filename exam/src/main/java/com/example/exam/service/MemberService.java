package com.example.exam.service;

import com.example.exam.dto.MemberForm;
import com.example.exam.entity.Member;
import com.example.exam.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public List<MemberForm> getMemberList() {
        //엔티티를 DTO로 변환


        return memberRepository.findAll()
                .stream()
                .map(MemberForm::toDto)
                .toList();

    }

    public MemberForm createMember(MemberForm form) {
        try {
            log.info("DTO 객체 {}",form.toString());
            Member member = memberRepository.save(form.toEntity());
            log.info("엔티티 객체 {}", member.toString());
            return MemberForm.toDto(member);
        } catch (Exception e) {
            return null;
        }
    }
    public MemberForm getMember(String id) {
        // id를 이용하여 게시글 1개 select
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            return MemberForm.toDto(optionalMember.get());
        } else {
            return null;
        }
    }
    @Transactional
    public MemberForm updateMember(MemberForm form)  {
        log.info("DTO 객체 {}",form.toString());
        /*트랜잭션 테스트
        Board aaa = form.toEntity();
        aaa.setId(null);
        boardRepository.save(aaa)
         */
        // 1. 기존 내용이 있는지 확인
        Member board = memberRepository.findById(form.getId())
                .orElseThrow(() -> new IllegalArgumentException("수정 실패! 대상 글이 없습니다."));

        Member updated = memberRepository.save(form.toEntity());
        log.info("엔티티 객체 {}", updated.toString());
        // 2. 엔티티를 DTO 로 변환 해서 리턴
        return MemberForm.toDto(updated);
    }
    @Transactional
    public void deleteMember(String id) {
        // 1. 기존 엔티티 확인
        Member board = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제 실패! 대상 글이 없습니다."));

        // 2. 엔티티 삭제
        memberRepository.delete(board);
        log.info("{}번 게시글이 삭제되었습니다.", id);
    }

    public MemberForm login(MemberForm dto) {
        log.info("dto {}", dto.toString());
        Member member = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 없음"));

        if (!member.getPwd().equals(dto.getPwd())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        return MemberForm.toDto(member);
    }
}

