package com.gestioncafe.model;

import java.sql.Date;

public class JourCongeFerie {
    private Date jour;
    private JourFerie jourFerie; // peut être null
    private Employe employe;     // peut être null

    public JourCongeFerie(Date jour, JourFerie jourFerie, Employe employe) {
        this.jour = jour;
        this.jourFerie = jourFerie;
        this.employe = employe;
    }

    public Date getJour() {
        return jour;
    }

    public void setJour(Date jour) {
        this.jour = jour;
    }

    public JourFerie getJourFerie() {
        return jourFerie;
    }

    public void setJourFerie(JourFerie jourFerie) {
        this.jourFerie = jourFerie;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
}

