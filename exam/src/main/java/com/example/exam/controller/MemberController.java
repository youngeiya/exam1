package com.example.exam.controller;

import com.example.exam.dto.BoardForm;
import com.example.exam.dto.MemberForm;
import com.example.exam.entity.Board;
import com.example.exam.entity.Member;
import com.example.exam.repository.BoardRepository;
import com.example.exam.repository.MemberRepository;
import com.example.exam.service.BoardService;
import com.example.exam.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    BoardService boardService;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;
    @GetMapping("/member/login")
    public String loginMember(@RequestParam(required = false) String msg,
                              @RequestParam(required = false) String link,
                              Model model) {
        if ("required".equals(msg)) {
            model.addAttribute("message", "로그인이 필요한 페이지입니다.");
            //이전페이지 정보 추가
            model.addAttribute("link", link);
            System.out.println("이전 페이지 정보 "+link);
        }
        return "member/login";
    }

//    @PostMapping("/member/auth")
//    public String authMember(MemberForm dto, HttpSession session) {
//        log.info("dto {}", dto.toString());
//        MemberForm loginedMember = memberService.login(dto);
//        session.setAttribute("User", loginedMember);
//        MemberForm user = (MemberForm)session.getAttribute("User");
//        log.info("세션 정보 : {} ", user.getId());
//        return "redirect:/home";
//    }

    @GetMapping("/home")
    public String goHome(Model model) {
        log.info("HomeController 진입");
        // Repository 대신 Service의 getTop5Boards 호출
        List<BoardForm> boardList = boardService.getBoardtop();

        model.addAttribute("boardList", boardList);
        return "home";
    }


    @GetMapping("/member/join")
    public String joinMember() {
        return "member/join";
    }


    @GetMapping("/member")
    public String allMember(Model model) {

        List<Member> memberList = memberRepository.findAll();
        model.addAttribute("mlist", memberList);
        return "member/list";
    }



//    @GetMapping("/member/info/{id}")
//    public String infoMember(@PathVariable String id, HttpSession session) {
//        Member mem = new Member();
//        System.out.println("id=" + id );
//        if(mem.getId().equals(id)) {
//            System.out.println(mem.toString());
//            session.setAttribute("mem", mem);
//            return "member/info";
//        }else{
//            return "member/error";
//        }
//    }
    @GetMapping("/member/info")
    public String infoMember(HttpSession session, Model model) {
        System.out.println("세션 ID: " + session.getId());
        System.out.println("세션 'user' 값: " + session.getAttribute("user"));
        System.out.println("세션 'User' 값: " + session.getAttribute("User"));
        // 세션 Key를 소문자 "user"로 가져오기 (로그인 시에도 "user"로 저장했다고 가정)
        Member loginUser = (Member) session.getAttribute("User");
        MemberForm infoUser = memberService.getMember(loginUser.getId());
        if (infoUser == null) {
            // 주소 앞에 /member/ 를 붙여서 정확한 로그인 페이지로 이동
            return "member/error";
        }

        model.addAttribute("mem", infoUser);
        return "member/info";
    }

    @PostMapping("/member/auth")
    public String authMember(@RequestParam String id,
                             @RequestParam String pwd,
                             HttpSession session,
                             @RequestParam(required = false) String link) {

        // 1. Repository를 통해 아이디로 회원 조회
        // (memberService 내부에 이 로직이 있다면 서비스 호출을 권장하지만, 요청하신 대로 Repository를 직접 사용하는 경우)
        Member mem = memberRepository.findById(id).orElse(null);

        // 2. 회원이 존재하지 않는 경우 처리
        if (mem == null) {
            log.info("로그인 실패: 아이디가 존재하지 않음 - {}", id);
            return "member/error";
        }

        // 3. 비밀번호 일치 여부 확인
        // 주의: 실제 서비스에서는 pwd.equals() 대신 암호화된 비밀번호 매칭(BCrypt 등)을 사용해야 합니다.
        if (mem.getId().equals(id) && mem.getPwd().equals(pwd)) {
            log.info("로그인 성공: {}", mem.toString());

            // 세션에 회원 정보 저장
            session.setAttribute("User", mem);

            // 4. 리다이렉트 처리 (기존 link 로직 유지)
            if (link == null || link.isBlank()) {
                return "redirect:/home";
            } else {
                return "redirect:" + link;
            }
        } else {
            // 비밀번호 불일치 시
            log.info("로그인 실패: 비밀번호 불일치 - {}", id);
            return "member/error";
        }
    }
    @PostMapping("/member/create")
    public String createMember(MemberForm mform, Model model){
        System.out.println(mform.toString());

        Member member = mform.toEntity();
        System.out.println(member.toString());

        Member saved = memberRepository.save(member);
        model.addAttribute("member",saved);
        return "redirect:/board";

    }
    @GetMapping("member/edit")
    public String editMember(@RequestParam String id, Model model){

        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            model.addAttribute("member", optionalMember.get());
            return "member/edit";
        } else {
            return "member/error";
        }
    }
    @PostMapping("/member/modify")
    public String modifyMember(MemberForm mform, Model model){
        System.out.println(mform.toString());

        Member member = mform.toEntity();
        System.out.println(member.toString());

        Optional<Member> optionalMember = memberRepository.findById(member.getId());


        if (optionalMember.isPresent()) {
            memberRepository.save(member);
            return "redirect:/member";
        } else {
            return "member/error";
        }
    }
    @GetMapping("member/delete")
    public String deleteMember(@RequestParam String id) {

        // id를 이용하여 게시글 1개 select
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            //해당 id의 데이터가 있으면 엔티티를 받아서 삭제
            memberRepository.delete(optionalMember.get());
            return "redirect:/member";
        } else {
            return "member/error";
        }
    }
    @GetMapping("/member/logout")

    public String logoutMember(HttpSession session) {

        session.invalidate();

        return "redirect:/home";

    }
}