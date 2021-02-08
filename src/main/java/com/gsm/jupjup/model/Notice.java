package com.gsm.jupjup.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Notice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notice_Idx;

    private String title;
    
    private String content;

    //Admin 외래키 매핑
    private Admin admin;

}
