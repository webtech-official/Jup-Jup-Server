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
@Service
public class LaptopSpecServiceImpl implements LaptopSpecService {

    private final LaptopSpecRepo laptopSpecRepo;
    private final LaptopRepo laptopRepo;

    @Override
    public Long save(LaptopSpecDto laptopSpecDto){
        return laptopSpecRepo.save(laptopSpecDto.toEntity()).getSpecIdx();
    }

    @Override
    public List<LaptopSpec> findAll() {
        return laptopSpecRepo.findAll();
    }

    @Override
    public LaptopSpec findBySpecIdx(Long SpecIdx) {
        return laptopSpecRepo.findBySpecIdx(SpecIdx);
    }

    @Override
    @Transactional
    public void updateBySpecIdx(Long specIdx, LaptopSpecDto laptopSpecDto) {
        LaptopSpec laptopSpec = findBySpecIdx(specIdx);
        laptopSpec.update(laptopSpecDto);
    }

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
