package com.gsm.jupjup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Laptop extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long laptopIdx;

    @Column(unique = true, nullable = false, length = 10000)   // 노트북 시리얼 번호
    private String laptopSerialNumber;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String classNumber;

    //laptopSpec 관계 설정
    @ManyToOne
    @JoinColumn(name = "specIdx")
    private LaptopSpec laptopSpec;

    @ManyToOne
    @JoinColumn(name = "adminIdx")
    private Admin admin;

    //================= 비즈니스 메소드 ==================//
    public void Delete_LaptopSpec() {
        this.laptopSpec = null;
    }
}
