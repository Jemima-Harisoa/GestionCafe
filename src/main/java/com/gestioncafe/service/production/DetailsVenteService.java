package com.gestioncafe.service.production;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestioncafe.dto.BeneficePeriodeStatDTO;
import com.gestioncafe.dto.VentePeriodeStatDTO;
import com.gestioncafe.dto.VenteProduitStatDTO;
import com.gestioncafe.model.DetailsVente;
import com.gestioncafe.repository.DetailsVenteRepository;

@Service
public class DetailsVenteService {
    private final DetailsVenteRepository detailsVenteRepository;

    public DetailsVenteService(DetailsVenteRepository detailsVenteRepository) {
        this.detailsVenteRepository = detailsVenteRepository;
    }

    public List<DetailsVente> findAll() {
        return detailsVenteRepository.findAll();
    }

    public List<VenteProduitStatDTO> getVenteStatParProduit() {
        return detailsVenteRepository.getVenteStatParProduit();
    }

    public List<VentePeriodeStatDTO> getVenteStatParPeriode(String periode) {
        return detailsVenteRepository.getVenteStatParPeriode(periode);
    }

    public List<VentePeriodeStatDTO> getVenteStatParPeriodeEtProduit(String periode, Integer produitId) {
        return detailsVenteRepository.getVenteStatParPeriodeEtProduit(periode, produitId);
    }

    public List<BeneficePeriodeStatDTO> getBeneficeStatParPeriode(String periode) {
        return detailsVenteRepository.getBeneficeStatParPeriode(periode);
    }

    public Optional<DetailsVente> findById(Integer id) {
        return detailsVenteRepository.findById(id);
    }

    public DetailsVente save(DetailsVente detailsVente) {
        return detailsVenteRepository.save(detailsVente);
    }

    public void deleteById(Integer id) {
        detailsVenteRepository.deleteById(id);
    }

    public List<VenteProduitStatDTO> getVenteStatParProduitAvecDate(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return detailsVenteRepository.getVenteStatParProduitAvecDate(dateDebut, dateFin);
    }

    public List<VenteProduitStatDTO> getVenteStatParProduitFiltre(
        LocalDate dateExacte,
        Integer annee,
        Integer mois,
        Integer jourMois,
        Integer jourSemaine,
        LocalDateTime dateDebut,
        LocalDateTime dateFin
    ) {
            List<Object[]> rows = detailsVenteRepository.getVenteStatParProduitFiltreNative(
                dateExacte, annee, mois, jourMois, jourSemaine, dateDebut, dateFin
            );
            List<VenteProduitStatDTO> result = new ArrayList<>();
            for (Object[] row : rows) {
                Integer produitId = ((Number) row[0]).intValue();
                String nom = (String) row[1];
                BigDecimal quantite = new BigDecimal(row[2].toString());
                BigDecimal montant = new BigDecimal(row[3].toString());
                result.add(new VenteProduitStatDTO(produitId, nom, quantite, montant));
            }
            return result;
    }

    public List<VentePeriodeStatDTO> getTotalProduitVenduParPeriode(String periode) {
        List<Object[]> rows = detailsVenteRepository.getTotalProduitVenduParPeriode(periode);
        List<VentePeriodeStatDTO> result = new ArrayList<>();
        for (Object[] row : rows) {
            String label = (String) row[0];
            BigDecimal quantite = new BigDecimal(row[1].toString());
            result.add(new VentePeriodeStatDTO(label, quantite));
        }
        return result;
    }

    // Ajout : bénéfice moyen par période
    public List<VentePeriodeStatDTO> getBeneficeMoyenParPeriode(String periode) {
        List<Object[]> rows = detailsVenteRepository.getBeneficeMoyenParPeriode(periode);
        List<VentePeriodeStatDTO> result = new ArrayList<>();
        for (Object[] row : rows) {
            String label = (String) row[0];
            BigDecimal beneficeMoyen = new BigDecimal(row[1].toString());
            result.add(new VentePeriodeStatDTO(label, null, beneficeMoyen));
        }
        return result;
    }

    // Retourne le bénéfice estimé total pour la période sélectionnée
    public BigDecimal getBeneficeEstimeParPeriode(String periode) {
        return detailsVenteRepository.getBeneficeEstimeParPeriode(periode);
    }

    // Retourne la liste des bénéfices par période (alignée sur la liste des périodes)
    public List<BigDecimal> getBeneficeMoyenParPeriodeList(String periode, List<String> periodes) {
        List<Object[]> rows = detailsVenteRepository.getBeneficeTotalParPeriode(periode);
        java.util.Map<String, BigDecimal> map = new java.util.HashMap<>();
        for (Object[] row : rows) {
            String label = (String) row[0];
            BigDecimal benefice = new BigDecimal(row[1].toString());
            map.put(label, benefice);
        }
        List<BigDecimal> result = new java.util.ArrayList<>();
        for (String p : periodes) {
            // Si la période n'existe pas dans la map, on met zéro
            result.add(map.getOrDefault(p, BigDecimal.ZERO));
        }
        
        return result;
    }
}
