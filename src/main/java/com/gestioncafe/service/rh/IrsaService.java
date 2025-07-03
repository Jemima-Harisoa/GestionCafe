package com.gestioncafe.service.rh;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.Irsa;
import com.gestioncafe.repository.IrsaRepository;

@Service
public class IrsaService {

    @Autowired
    private IrsaRepository irsaRepository;

    public List<Irsa> findAll() {
        return irsaRepository.findAll();
    }

    public Irsa findById(Long id) {
        return irsaRepository.findById(id).orElseThrow();
    }

    public Irsa save(Irsa irsa) throws Exception {
        List<Irsa> all = findAll();

        double minSal = irsa.getSalaireMin();
        double maxSal = irsa.getSalaireMax();
        double taux = irsa.getTaux();

        // 1. S'assurer que la première tranche commence à 0
        boolean aucuneCommenceAZero = all.stream()
                .noneMatch(i -> i.getSalaireMin() == 0.0);
        if (aucuneCommenceAZero) {
            irsa.setSalaireMin(0.0);
            minSal = 0.0;
        }

        // 2. Vérification des champs obligatoires et cohérence
        if (minSal < 0)
            throw new Exception("Le salaire minimum doit être positif.");
        if (maxSal <= 0)
            throw new Exception("Le salaire maximum doit être strictement positif.");
        if (minSal >= maxSal)
            throw new Exception("Le salaire min doit être strictement inférieur au salaire max.");

        // 3. Vérifier le chevauchement avec les tranches existantes
        for (Irsa existant : all) {
            if (irsa.getId() != null && irsa.getId().equals(existant.getId()))
                continue;

            double minExist = existant.getSalaireMin();
            double maxExist = existant.getSalaireMax();
            double tauxExist = existant.getTaux();

            if (tauxExist == taux) {
                throw new Exception("Une tranche avec le taux " + taux + "% existe déjà.");
            }

            boolean chevauchement;
            if (Double.isNaN(maxSal)) {
                chevauchement = Double.isNaN(maxExist) || minSal <= maxExist;
            } else if (Double.isNaN(maxExist)) {
                chevauchement = maxSal > minExist;
            } else {
                chevauchement = !(maxSal <= minExist || minSal >= maxExist);
            }

            if (chevauchement) {
                throw new Exception("La tranche saisie chevauche une tranche existante.");
            }
        }

        // 4. Vérifier la continuité de toutes les tranches
        List<Irsa> toutes = new ArrayList<>(all);
        if (irsa.getId() != null) {
            toutes.removeIf(i -> i.getId().equals(irsa.getId()));
        }
        toutes.add(irsa);
        toutes.sort(Comparator.comparingDouble(Irsa::getSalaireMin));

        for (int i = 0; i < toutes.size() - 1; i++) {
            Irsa current = toutes.get(i);
            Irsa next = toutes.get(i + 1);

            if (Double.isNaN(current.getSalaireMax())) {
                throw new Exception("Une tranche ouverte (≥ " + current.getSalaireMin() + ") doit être la dernière.");
            }

            if (current.getSalaireMax() != next.getSalaireMin()) {
                throw new Exception("Les tranches IRSA ne sont pas continues entre " +
                        current.getSalaireMax() + " et " + next.getSalaireMin());
            }
        }

        return irsaRepository.save(irsa);
    }

    @Transactional
    public void delete(Irsa irsa) throws Exception {
        deleteById(irsa.getId());
    }

    @Transactional
    public void deleteById(Long id) throws Exception {
        List<Irsa> tranches = findAll();

        Irsa aSupprimer = tranches.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Tranche introuvable pour suppression"));

        tranches.remove(aSupprimer);
        tranches.sort(Comparator.comparingDouble(Irsa::getSalaireMin));

        for (int i = 0; i < tranches.size() - 1; i++) {
            Irsa actuelle = tranches.get(i);
            Irsa suivante = tranches.get(i + 1);

            if (Double.isNaN(actuelle.getSalaireMax())) {
                throw new Exception("La tranche ouverte ≥ " + actuelle.getSalaireMin() + " doit être la dernière.");
            }

            if (actuelle.getSalaireMax() != suivante.getSalaireMin()) {
                throw new Exception("Suppression invalide : discontinuité entre "
                        + actuelle.getSalaireMax() + " et " + suivante.getSalaireMin());
            }
        }

        irsaRepository.deleteById(id);
    }
}
