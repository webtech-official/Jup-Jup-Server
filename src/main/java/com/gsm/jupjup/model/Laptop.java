package com.gsm.jupjup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Laptop {
    @Id
    @Column
    // 노트북 시리얼 번호
    private String laptopSerialNumber;

    @Column
    // 노트북 이름
    private String laptopName;

    @Column
    // 노트북 제조사
    private String laptopBrand;

    @CreatedDate
    // 노트북 생성 날짜
    private LocalDateTime creationTime;
}
