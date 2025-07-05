package com.gestioncafe.service.rh;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.gestioncafe.model.GradeEmploye;
import com.gestioncafe.repository.GradeEmployeRepository;

@Service
public class GradeEmployeService {

    @Autowired
    private GradeEmployeRepository gradeEmployeRepository;

    public GradeEmploye saveGradeEmploye(GradeEmploye gradeEmploye) {
        return gradeEmployeRepository.save(gradeEmploye);
    }
}
