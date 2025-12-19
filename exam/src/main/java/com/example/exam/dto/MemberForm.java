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
    private String name;
    private int age;
    private String addr;
    private String mobile;
    private String pwd;

    //DTO를 Entity로 변환
    public Member toEntity() {
        return new Member(id, name, age, addr, mobile,pwd);

    }
    //Entity를 DTO 로 변환
    public static MemberForm toDto(Member member) {
        return new MemberForm(member.getId(), member.getName(), member.getAge(),member.getAddr(),member.getMobile(),member.getPwd());
    }


}
