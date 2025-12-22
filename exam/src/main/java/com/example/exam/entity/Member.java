package com.example.exam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Member {
    @Id
    @Column
    private String id;
    @Column
    private String pwd;
    @Column
    private String name;
    @Column
    private int age;
    @Column
    private String addr;
    @Column
    private String mobile;



}