package com.gestioncafe.service.rh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.Candidat;
import com.gestioncafe.model.StatutEmploye;
import com.gestioncafe.repository.CandidatRepository;
import com.gestioncafe.repository.StatutEmployeRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CandidatService {

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private StatutEmployeRepository statutEmployeRepository;

    public List<Candidat> getAllCandidats() {
        // Étape 1 : récupérer tous les StatutEmploye avec statut.id = 1
        List<StatutEmploye> statutEmployes = statutEmployeRepository.findByIdStatut(1L);

        // Étape 2 : extraire les id des candidats déjà recrutés
        Set<Long> candidatsRecrutesIds = statutEmployes.stream()
            .map(se -> se.getEmploye().getCandidat().getId())
            .collect(Collectors.toSet());

        // Étape 3 : filtrer les candidats qui ne sont pas encore recrutés
        return candidatRepository.findAll().stream()
            .filter(c -> !candidatsRecrutesIds.contains(c.getId()))
            .collect(Collectors.toList());
    }

    public Candidat getCandidatById(Long id) {
        return candidatRepository.findById(id).orElse(null);
    }

    public Candidat saveCandidat(Candidat candidat) {
        return candidatRepository.save(candidat);
    }

    public void deleteCandidat(Long id) {
        candidatRepository.deleteById(id);
    }

    public List<Candidat> getCandidatsByGenreId(Long genreId) {
    // 1. Récupérer les StatutEmploye avec statut.id = 1
    List<StatutEmploye> statutEmployes = statutEmployeRepository.findByIdStatut(1L);

    // 2. Obtenir les ID des candidats déjà recrutés
    Set<Long> candidatsRecrutesIds = statutEmployes.stream()
        .map(se -> se.getEmploye().getCandidat().getId())
        .collect(Collectors.toSet());

    // 3. Récupérer les candidats par genre, puis filtrer ceux qui ne sont pas encore recrutés
    return candidatRepository.findByGenreId(genreId).stream()
        .filter(c -> !candidatsRecrutesIds.contains(c.getId()))
        .collect(Collectors.toList());
}

}
