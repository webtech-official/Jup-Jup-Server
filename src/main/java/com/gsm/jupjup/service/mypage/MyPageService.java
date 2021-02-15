package com.gsm.jupjup.service.mypage;

import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.Laptop;

import java.util.List;

public interface MyPageService {
    //내가 빌린 기자재 보기
    List<EquipmentAllow> findMyEquipment();

    //현재 나의 노트북 보기
    List<Laptop> findMyLaptop();
}
