package com.gsm.jupjup.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryIdx;

    @Column(nullable = false)
    private String categoryName;

    public void change_CategoryName(String name) {
        this.categoryName = name;
    }
}
