package com.gsm.jupjup.service.equipment;


import com.gsm.jupjup.advice.exception.EquipmentAllowAmountExceedException;
import com.gsm.jupjup.advice.exception.EquipmentAllowAmountZeroException;
import com.gsm.jupjup.advice.exception.EquipmentAllowNotFoundException;
import com.gsm.jupjup.advice.exception.UserDoesNotExistException;
import com.gsm.jupjup.config.security.JwtTokenProvider;
import com.gsm.jupjup.dto.equipmentAllow.EquipmentAllowSaveDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.response.EquipmentAllowEnum;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class EquipmentAllowService {
    public final EquipmentAllowRepo equipmentAllowRepo;
    public final EquipmentService equipmentService;
    public final JwtTokenProvider jwtTokenProvider;
    public final AdminRepo adminRepo;

    @Transactional
    public void save(String NameOfEquipment, EquipmentAllowSaveDto equipmentAllowSaveDto, HttpServletRequest req) throws Exception {
        //신청할 기자제 수량 0체크
        zeroChk(equipmentAllowSaveDto.getAmount());

        //기자제 이름으로 equipment 테이블을 조회
        Equipment equipment = equipmentService.equipmentFindBy(NameOfEquipment);

        // 수량 체크 및 변경
        int result = equipmentAmountCount(equipment.getCount(), equipmentAllowSaveDto.getAmount());
        equipment.updateAmount(result);

        //equipment 조회해서 equipmentALlowSaveDto 에 값을 주입하여
        equipmentAllowSaveDto.setEquipment(equipment);

        //toEntity로 연관관계가 맻여진 equipmentAllow생성
        EquipmentAllow equipmentAllow = equipmentAllowSaveDto.toEntity();

        //UserEmail을 가져와서 Admin과 연관관계 매핑
        String userEmail = GetUserEmail(req);
        Admin admin = adminRepo.findByEmail(userEmail).orElseThrow(UserDoesNotExistException::new);
        equipmentAllow.setAdmin(admin);

        equipmentAllowRepo.save(equipmentAllow);
    }


    @Transactional(readOnly = true)
    public EquipmentAllow findById(Long eqa_idx){
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_idx);
        return equipmentAllow;
    }

    @Transactional
    public EquipmentAllow equipmentAllowFindBy(Long idx){
        return equipmentAllowRepo.findById(idx).orElseThrow(EquipmentAllowNotFoundException::new);
    }

    public void zeroChk(int num){
        if(num == 0) throw new EquipmentAllowAmountZeroException();
    }

    /** 기자재를 신청할 수 있는지 계산해주는 함수
     * 신청하면 남은 기자제를 반환함
     * 신청할 수 있는 수량이 아니면(결과가 음수라면) 예외 발
     * @param equipmentCount   //신청할 수 있는 기자재의 양
     * @param equipmentAllowAmount  //사용자가 신청하려고 하는 기자재의 양
     * @return 남은 기자재 양
     */
    public int equipmentAmountCount(int equipmentCount, int equipmentAllowAmount){
        int result = equipmentCount - equipmentAllowAmount;
        if(result >= 0)
            return result;
        else
            throw new EquipmentAllowAmountExceedException();
    };

    public String GetUserEmail(HttpServletRequest req){
        String token = jwtTokenProvider.resolveToken(req);
        String userEmail = jwtTokenProvider.getUserPk(token);
        return userEmail;
    }

//    @Transactional
//    public void update(Long eqa_idx, EquipmentAllowSaveDto equipmentAllowSaveDto){
//        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_idx);
//        int equipmentCount = equipmentAllow.getAmount();
//        int equipmentAllowAmount = equipmentAllow.getAmount();
//
//        equipmentAmountCount(equipmentCount, equipmentAllowAmount);
//    }

//    @Transactional
//    public void deleteById(Long eqa_idx){
//        try {
//            equipmentAllowRepo.deleteById(eqa_idx);
//        }catch (Exception e){
//            throw new EquipmentAllowNotFoundException();
//        }
//    }

    //승인 요청 처리
    @Transactional
    public void SuccessAllow(Long eqa_Idx){
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_Idx);
        if(equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Accept) || equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Reject)){
            System.out.println("이미 승인되거나 거절된 신청입니다");
        } else {
            equipmentAllow.setIsReturn(false);
            equipmentAllow.setEquipmentEnum(EquipmentAllowEnum.ROLE_Accept);
        }
    }

    //거절 요청 처리
    @Transactional
    public void FailureAllow(Long eqa_Idx){
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_Idx);
        if(equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Accept) || equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Reject)){
            System.out.println("이미 승인되거나 거절된 신청입니다");
        } else {
            equipmentAllow.setEquipmentEnum(EquipmentAllowEnum.ROLE_Reject);
            //신청한 제품
            Equipment equipment = equipmentAllow.getEquipment();

            // 신청 갯수
            int now = equipmentAllow.getAmount();
            //원래 제품 갯수
            int new_c = equipment.getCount();

            equipment.updateAmount(now + new_c);
        }
    }

    //반납 요청 처리
    @Transactional
    public void ReturnAllow(Long eqa_Idx){
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_Idx);
        if(equipmentAllow.getIsReturn() == true){
            System.out.println("이미 반납된 제품입니다");
        } else {
            equipmentAllow.setIsReturn(true);

            //신청한 제품
            Equipment equipment = equipmentAllow.getEquipment();

            // 신청 갯수
            int now = equipmentAllow.getAmount();
            //원래 제품 갯수
            int new_c = equipment.getCount();

            equipment.updateAmount(now + new_c);
        }
    }
}