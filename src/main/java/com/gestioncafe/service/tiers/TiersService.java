package com.gestioncafe.service.tiers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestioncafe.model.Tiers;
import com.gestioncafe.repository.tiers.TiersRepository;

@Service
public class TiersService {
    @Autowired
    private TiersRepository tiersRepository;

    public List<Tiers> findAll() {
        return tiersRepository.findAll();
    }

    public Optional<Tiers> findById(Integer id) {
        return tiersRepository.findById(id);
    }

    public Tiers save(Tiers tiers) {
        return tiersRepository.save(tiers);
    }

    public void deleteById(Integer id) {
        tiersRepository.deleteById(id);
    }
}
