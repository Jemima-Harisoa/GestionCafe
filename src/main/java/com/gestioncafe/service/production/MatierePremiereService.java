package com.gestioncafe.service.production;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestioncafe.model.MatierePremiere;
import com.gestioncafe.repository.MatierePremiereRepository;

@Service
public class MatierePremiereService {
    private final MatierePremiereRepository repository;

    public MatierePremiereService(MatierePremiereRepository repository) {
        this.repository = repository;
    }

    public List<MatierePremiere> findAll() {
        return repository.findAll();
    }

    public Optional<MatierePremiere> findById(Integer id) {
        return repository.findById(id);
    }

    public MatierePremiere save(MatierePremiere mp) {
        // S'assurer que la catégorie d'unité est bien renseignée (si logique métier à ajouter)
        return repository.save(mp);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    // Retourne le prix unitaire courant de la matière première (à adapter selon votre modèle)
    public BigDecimal getPrixUnitaire(Integer idMatierePremiere) {
        return repository.findById(idMatierePremiere)
            .map(mp -> {
                var historiques = mp.getHistoriquesEstimation();
                if (historiques != null && !historiques.isEmpty()) {
                    // Prend le prix de la dernière estimation (date la plus récente)
                    return historiques.stream()
                        .filter(h -> h.getPrix() != null)
                        .max((h1, h2) -> {
                            if (h1.getDateApplication() == null) return -1;
                            if (h2.getDateApplication() == null) return 1;
                            return h1.getDateApplication().compareTo(h2.getDateApplication());
                        })
                        .map(h -> {
                            Number prix = h.getPrix();
                            return (prix instanceof BigDecimal)
                                ? (BigDecimal) prix
                                : new BigDecimal(prix.toString());
                        })
                        .orElse(BigDecimal.ZERO);
                }
                
                return BigDecimal.ZERO;
            })
            .orElse(BigDecimal.ZERO);
    }
}
