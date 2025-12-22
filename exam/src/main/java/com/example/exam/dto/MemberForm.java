package com.example.exam.dto;


import com.example.exam.entity.Member;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class MemberForm {
    private String id;
    private String pwd;
    private String name;
    private int age;
    private String addr;
    private String mobile;


    //DTO를 Entity로 변환
    public Member toEntity() {
        return new Member(id, pwd,name, age, addr, mobile);

    }
    //Entity를 DTO 로 변환
    public static MemberForm toDto(Member member) {
        return new MemberForm(member.getId(),member.getPwd(), member.getName(), member.getAge(),member.getAddr(),member.getMobile());
    }


}
