package com.gsm.jupjup.service.laptop;

import com.gsm.jupjup.dto.laptopSpec.LaptopSpecDto;
import com.gsm.jupjup.model.LaptopSpec;
import com.gsm.jupjup.repo.LaptopSpecRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LaptopSpecServiceImpl implements LaptopSpecService {

    //DI
    @Autowired
    private LaptopSpecRepo laptopSpecRepo;

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
        laptopSpecRepo.deleteById(SpecIdx);
    }

//    public void SpecIsBlank(LaptopSpecDto laptopSpecDto){
//        if
//    }

}
