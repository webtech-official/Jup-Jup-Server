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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipmentAllowServiceImpl implements EquipmentAllowService {

    public final EquipmentAllowRepo equipmentAllowRepo;
    public final EquipmentRepo equipmentRepo;
    public final EquipmentService equipmentService;
    public final AdminRepo adminRepo;

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
        EquipmentAllow equipmentAllow = equipmentAllowSaveDto.toEntity();
        equipmentAllow.setEquipment(equipment);

        //UserEmail을 가져와서 Admin과 연관관계 매핑
        String userEmail = GetUserEmail();
        Admin admin = adminRepo.findByEmail(userEmail).orElseThrow(UserDoesNotExistException::new);
        equipmentAllow.setAdmin(admin);

        equipmentAllowRepo.save(equipmentAllow);
    }

    @Override
    public EquipmentAllow findById(Long eqa_idx){
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_idx);
        return equipmentAllow;
    }

    @Override
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

    @Transactional
    @Override
    public void SuccessAllow(Long eqa_Idx){
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_Idx);
        if(equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Accept) || equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Reject)){
            throw new AlreadyApprovedAndRejectedException();
        } else {
            equipmentAllow.setEquipmentEnum(EquipmentAllowEnum.ROLE_Accept);
        }
    }




    @Transactional
    @Override
    public void FailureAllow(Long eqa_Idx){
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_Idx);
        if(equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Accept) || equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Reject)){
            throw new AlreadyApprovedAndRejectedException();
        } else if(equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Rental)) {
            log.info("이메 대여된 신청입니다.");
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

    @Transactional
    @Override
    public void ReturnAllow(Long eqa_Idx){
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_Idx);
        if(equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Return)){
            throw new AlreadyReturnedException();
        } else if(equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Waiting)){
            throw new ApproveApplicationFirstException();
        } else if(equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Reject)) {
            throw new AlreadyApprovedAndRejectedException();
        } else {
            equipmentAllow.setEquipmentEnum(EquipmentAllowEnum.ROLE_Return);

            //신청한 제품
            Equipment equipment = equipmentAllow.getEquipment();

            // 신청 갯수
            int now = equipmentAllow.getAmount();
            //원래 제품 갯수
            int new_c = equipment.getCount();

            equipment.updateAmount(now + new_c);
        }
    }

    @Transactional
    @Override
    public void Rental(Long eqa_Idx) {
        EquipmentAllow equipmentAllow = equipmentAllowFindBy(eqa_Idx);
        if(equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Return)){
            throw new AlreadyReturnedException();
        } else if(equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Waiting)){
            throw new ApproveApplicationFirstException();
        } else if(equipmentAllow.getEquipmentEnum().equals(EquipmentAllowEnum.ROLE_Reject)) {
            throw new AlreadyApprovedAndRejectedException();
        } else {
            equipmentAllow.setEquipmentEnum(EquipmentAllowEnum.ROLE_Rental);
        }
    }


    //현재 사용자의 ID를 Return
    public static UserDetails currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return user;
    }

    public String GetUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Admin userDetails = (Admin) principal;
        String username = userDetails.getUsername();
        return username;
    }

    //현재 사용자가 "ROLE_ADMIN"이라는 ROLE을 가지고 있는지 확인
    public static boolean hasAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent();
    }
}