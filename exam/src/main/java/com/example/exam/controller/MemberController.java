package com.example.exam.controller;

import com.example.exam.dto.MemberForm;
import com.example.exam.entity.Member;
import com.example.exam.repository.MemberRepository;
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
    MemberRepository memberRepository;

    @GetMapping("/member/login")
    public String loginMember(@RequestParam(required = false) String msg,
                              Model model) {
        if ("required".equals(msg)) {
            model.addAttribute("message", "로그인이 필요한 페이지입니다.");
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
    public String goHome() {
        log.info("HomeController 진입");
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



    @GetMapping("/member/info/{id}")
    public String infoMember(@PathVariable String id, Model model) {
        Member mem = new Member();
        System.out.println("id=" + id );
        if(mem.getId().equals(id)) {
            System.out.println(mem.toString());
            model.addAttribute("mem", mem);
            return "member/info";
        }else{
            return "member/error";
        }
    }


    @PostMapping("/member/auth")
    public String authMember(@RequestParam String id, @RequestParam String name, HttpSession session) {
        Member mem = memberRepository.findById(id).orElse(null);
        if (mem == null) {
            return "member/error";
        }

        System.out.println("id=" + id + ", name=" + name);
        if(mem.getId().equals(id) && mem.getName().equals(name)) {
            System.out.println(mem.toString());
            session.setAttribute("User", mem);
            return "redirect:/home";
        }else{
            return "member/error";
        }

    }
    @PostMapping("/member/new")
    public String createMember(MemberForm mform, Model model){
        System.out.println(mform.toString());

        Member member = mform.toEntity();
        System.out.println(member.toString());

        Member saved = memberRepository.save(member);
        model.addAttribute("member",saved);
        return "member/create";

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