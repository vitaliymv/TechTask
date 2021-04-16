package com.example.demo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@ToString
public class Info {
    @Id
    private Long id;
    private Double latitude;
    private Double longitude;
    private LocalTime delivery_from;
    private LocalTime delivery_to;
}
