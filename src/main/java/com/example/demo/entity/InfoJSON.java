package com.example.demo.entity;

import lombok.*;

import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InfoJSON {
    private LocalTime start;
    private LocalTime end;
    private LocalTime time_to_give;
    private Long speed;
    private Double latitude;
    private Double longitude;
}
