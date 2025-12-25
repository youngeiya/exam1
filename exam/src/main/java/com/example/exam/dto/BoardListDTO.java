package com.example.exam.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

public class BoardListDTO {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
}
