package com.example.exam.dto;



public class Member {
    private String id="test";
    private String name="홍길동";
    private int age=30;
    private String addr="진주";
    private String mobile="01011112222";

    public Member(String id, String name, int age, String addr, String mobile) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.addr = addr;
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", addr='" + addr + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }

}
