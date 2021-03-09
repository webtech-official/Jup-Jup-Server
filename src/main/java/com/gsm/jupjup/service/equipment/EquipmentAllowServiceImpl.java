package com.gsm.jupjup.service.equipment;

import com.gsm.jupjup.advice.exception.*;
import com.gsm.jupjup.dto.equipmentAllow.EquipmentAllowSaveDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.response.EquipmentAllowEnum;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import com.gsm.jupjup.repo.EquipmentRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EquipmentAllowServiceImpl implements EquipmentAllowService {

    public final EquipmentAllowRepo equipmentAllowRepo;
    public final EquipmentRepo equipmentRepo;
    public final EquipmentService equipmentService;
    public final AdminRepo adminRepo;

    /**
     * 기자재 신청 저장
     * @param NameOfEquipment 신청 기자재 이름
     * @param equipmentAllowSaveDto 신청 저장 정보
     */
    @Override
    @Transactional
    public void save(String NameOfEquipment, EquipmentAllowSaveDto equipmentAllowSaveDto) {
        //신청할 기자제 수량 0체크
        zeroChk(equipmentAllowSaveDto.getAmount());

        //기자제 이름으로 equipment 테이블을 조회
        Equipment equipment = equipmentService.equipmentFindBy(NameOfEquipment);

        // 수량 체크 및 변경
        int result = equipmentAmountCount(equipment.getCount(), equipmentAllowSaveDto.getAmount());
        equipment.updateAmount(result);

        //toEntity로 연관관계가 맻여진 equipmentAllow생성
        Admin admin = adminRepo.findByEmail(currentUser().getEmail()).orElseThrow(UserDoesNotExistException::new);
        EquipmentAllow equipmentAllow = equipmentAllowSaveDto.toEntity();
        equipmentAllow.EquipmentAdmin_Mapping(admin, equipment);

        equipmentAllowRepo.save(equipmentAllow);
    }

    /**
     * 해당 신청 조회
     * @param eqa_idx 신청 번호
     * @return EquipmentAllow
     */
    @Override
    public EquipmentAllow findById(Long eqa_idx) {
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_idx);
        return equipmentAllow;
    }

    /**
     * 해당 신청 조회 => 하위 메소드
     * @param idx 신청 번호
     * @return EquipmentAllow
     */
    @Override
    public EquipmentAllow equipmentAllowFindBy(Long idx) {
        return equipmentAllowRepo.findById(idx).orElseThrow(EquipmentAllowNotFoundException::new);
    }

    public void zeroChk(int num) {
        if (num == 0) throw new EquipmentAllowAmountZeroException();
    }

    /**
     * 기자재를 신청할 수 있는지 계산해주는 함수
     * 신청하면 남은 기자제를 반환함
     * 신청할 수 있는 수량이 아니면(결과가 음수라면) 예외 발생
     * @param equipmentCount 신청할 수 있는 기자재의 양
     * @param equipmentAllowAmount 사용자가 신청하려고 하는 기자재의 양
     * @return 남은 기자재 양
     */
    public int equipmentAmountCount(int equipmentCount, int equipmentAllowAmount) {
        int result = equipmentCount - equipmentAllowAmount;
        if (result >= 0)
            return result;
        else
            throw new EquipmentAllowAmountExceedException();
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

    /**
     * 신청 수락
     * @param eqa_Idx 신청 번호
     */
    @Transactional
    @Override
    public void SuccessAllow(Long eqa_Idx) {
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_Idx);
        if (equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Accept) || equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Reject)) {
            throw new AlreadyApprovedAndRejectedException();
        } else {
            equipmentAllow.Change_Accept();
        }
    }

    /**
     * 신청 거절
     * @param eqa_Idx 신청 번호
     */
    @Transactional
    @Override
    public void FailureAllow(Long eqa_Idx) {
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_Idx);
        if (equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Accept) || equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Reject)) {
            throw new AlreadyApprovedAndRejectedException();
        } else if (equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Rental)) {
            log.info("이메 대여된 신청입니다.");
        } else {
            equipmentAllow.Change_Reject();
            //신청한 제품
            Equipment equipment = equipmentAllow.getEquipment();

            // 신청 갯수
            int now = equipmentAllow.getAmount();
            //원래 제품 갯수
            int new_c = equipment.getCount();

            equipment.updateAmount(now + new_c);
        }
    }

    /**
     * 기자재 반납
     * @param eqa_Idx 신청 번호
     */
    @Transactional
    @Override
    public void ReturnAllow(Long eqa_Idx) {
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_Idx);
        if (equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Return)) {
            throw new AlreadyReturnedException();
        } else if (equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Waiting)) {
            throw new ApproveApplicationFirstException();
        } else if (equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Reject)) {
            throw new AlreadyApprovedAndRejectedException();
        } else {
            equipmentAllow.Change_Return();

            //신청한 제품
            Equipment equipment = equipmentAllow.getEquipment();

            // 신청 갯수
            int now = equipmentAllow.getAmount();
            //원래 제품 갯수
            int new_c = equipment.getCount();

            equipment.updateAmount(now + new_c);
        }
    }

    /**
     * 기자재 대여 => 사인 후
     * @param eqa_Idx 신청 번호
     */
    @Transactional
    @Override
    public void Rental(Long eqa_Idx) {
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_Idx);
        if (equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Return)) {
            throw new AlreadyReturnedException();
        } else if (equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Waiting)) {
            throw new ApproveApplicationFirstException();
        } else if (equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Reject)) {
            throw new AlreadyApprovedAndRejectedException();
        } else {
            equipmentAllow.Change_Rental();
        }
    }

    /**
     * 현재 사용자 ID 검색
     * @return Admin
     */
    public static Admin currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin user = (Admin) authentication.getPrincipal();
        return user;
    }

    /**
     * 현재 사용자가 "ROLE_ADMIN"이라는 ROLE을 가지고 있는지 확인
     * @return boolean
     */
    public static boolean hasAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent();
    }
}