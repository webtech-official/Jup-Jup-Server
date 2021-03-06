package com.gsm.jupjup.service.laptop;

import com.gsm.jupjup.dto.laptopSpec.LaptopSpecDto;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.model.LaptopSpec;
import com.gsm.jupjup.repo.LaptopRepo;
import com.gsm.jupjup.repo.LaptopSpecRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LaptopSpecServiceImpl implements LaptopSpecService {

    private final LaptopSpecRepo laptopSpecRepo;
    private final LaptopRepo laptopRepo;

    /**
     * 노트북 스팩 저장
     * @param laptopSpecDto 노트북 스팩 저장 정보
     * @return 노트북 스팩 번호
     */
    @Override
    public Long save(LaptopSpecDto laptopSpecDto){
        return laptopSpecRepo.save(laptopSpecDto.toEntity()).getSpecIdx();
    }

    /**
     * 모든 기자재 스팩 검색
     * @return List<LaptopSpec>
     */
    @Override
    public List<LaptopSpec> findAll() {
        return laptopSpecRepo.findAll();
    }

    /**
     * 해당 노트북 스팩 검색
     * @param SpecIdx 노트북 스팩 번호
     * @return LaptopSpec
     */
    @Override
    public LaptopSpec findBySpecIdx(Long SpecIdx) {
        return laptopSpecRepo.findBySpecIdx(SpecIdx);
    }

    /**
     * 노트북 스팩 수정
     * @param specIdx 노트북 스팩 번호
     * @param laptopSpecDto 노트북 스팩 수정 정보
     */
    @Override
    @Transactional
    public void updateBySpecIdx(Long specIdx, LaptopSpecDto laptopSpecDto) {
        LaptopSpec laptopSpec = findBySpecIdx(specIdx);
        laptopSpec.update(laptopSpecDto);
    }

    /**
     * 노트북 스팩 삭제
     * @param SpecIdx 노트북 스팩 번호
     */
    @Override
    public void deleteBySpecIdx(Long SpecIdx) {
        //프론트에서 한번 분기 처리
        //Alert("지우면 사양이 등록된 노트북까지 전부 삭제 됩니다. 그래도 지우시겠습니까?")
        LaptopSpec laptopSpec = laptopSpecRepo.findBySpecIdx(SpecIdx);
        List<Laptop> laptopList = laptopRepo.findByLaptopSpec(laptopSpec);
        for (Laptop laptop : laptopList) {
            laptop.setLaptopSpec(null);
        }
        laptopSpecRepo.deleteById(SpecIdx);
    }
}
