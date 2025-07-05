package com.gestioncafe.service.marketing;

import com.gestioncafe.model.*;
import com.gestioncafe.repository.MarketingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.util.List;

@Service
public class MarketingService {

    @Autowired
    private MarketingRepository marketingRepository;

    public List<Marketing> getAllMarketing() {
        return marketingRepository.findAll();
    }

    public double getAverageAge() {
        List<Marketing> list = marketingRepository.findAll();
        if (list.isEmpty()) {
            return 0;
        }
        int totalAge = 0;
        for (Marketing m : list) {
            totalAge += m.getAge();
        }
        return (double) totalAge / list.size();
    }

    public double getPourcentageGenre(String genreValeur) {
        List<Marketing> list = marketingRepository.findAll();
        if (list.isEmpty()) {
            return 0;
        }
        long count = list.stream()
                         .filter(m -> m.getGenre() != null)
                         .filter(m -> genreValeur.equalsIgnoreCase(m.getGenre().getValeur()))
                         .count();
        return (double) count * 100 / list.size();
    }

    public BigDecimal getAverageBudgetMoyen() {
        List<Marketing> list = marketingRepository.findAll();
        if (list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Marketing m : list) {
            if (m.getBudgetMoyen() != null) {
                total = total.add(m.getBudgetMoyen());
            }
        }
        return total.divide(BigDecimal.valueOf(list.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getAverageEstimationPrixProduit() {
        List<Marketing> list = marketingRepository.findAll();
        if (list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Marketing m : list) {
            if (m.getEstimationPrixProduit() != null) {
                total = total.add(m.getEstimationPrixProduit());
            }
        }
        return total.divide(BigDecimal.valueOf(list.size()), 2, BigDecimal.ROUND_HALF_UP);
    }
    public Map<Lieux, Long> getLieuxWithCounts() {
        List<Marketing> list = marketingRepository.findAll();
        return list.stream()
                .filter(m -> m.getLieu() != null)
                .collect(Collectors.groupingBy(Marketing::getLieu, Collectors.counting()));
    }
    public Map<Produit, Long> getProduitsWithCounts() {
        List<Marketing> list = marketingRepository.findAll();
        return list.stream()
                   .filter(m -> m.getProduit() != null)
                   .collect(Collectors.groupingBy(Marketing::getProduit, Collectors.counting()));
    }
}
