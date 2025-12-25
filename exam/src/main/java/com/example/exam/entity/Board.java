package com.example.exam.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;
    // 1. 날짜 필드 추가
    @Column
    private LocalDateTime createdAt;

    // 2. 데이터 저장 시 자동으로 현재 시간 입력
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}

