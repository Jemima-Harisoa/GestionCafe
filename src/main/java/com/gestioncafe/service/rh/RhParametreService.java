package com.gestioncafe.service.rh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RhParametreService {
    @Autowired
    private JourFerieService jourFerieService;
    @Autowired
    private GradeService gradeService;

    @Autowired
    private IrsaService irsaService;

    public IrsaService getIrsaService() {
        return irsaService;
    }

    public void setIrsaService(IrsaService irsaService) {
        this.irsaService = irsaService;
    }

    public JourFerieService getJourFerieService() {
        return jourFerieService;
    }

    public void setJourFerieService(JourFerieService jourFerieService) {
        this.jourFerieService = jourFerieService;
    }

    public GradeService getGradeService() {
        return gradeService;
    }

    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

}
