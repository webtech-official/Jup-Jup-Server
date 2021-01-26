package com.gsm.jupjup.model;

import lombok.*;
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

    //fk mapping
    @ManyToOne
    @JoinColumn(name = "specIdx")
    private LaptopSpec laptopSpec;

    @Builder
    public Laptop(String laptopSerialNumber, String laptopName, String laptopBrand, LaptopSpec laptopSpec){
        this.laptopSerialNumber = laptopSerialNumber;
        this.laptopName = laptopName;
        this.laptopSerialNumber = laptopSerialNumber;
        this.laptopSpec = laptopSpec;
    }
    //update에 사용할 생성자
    public void update(String laptopName, String laptopBrand){
        this.laptopName = laptopName;
        this.laptopBrand = laptopBrand;
    }
}
