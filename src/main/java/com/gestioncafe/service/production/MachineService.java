package com.gestioncafe.service.production;

import com.gestioncafe.model.Machine;
import com.gestioncafe.repository.MachineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {
    private final MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public List<Machine> findAll() {
        return machineRepository.findAll();
    }

    public Optional<Machine> findById(Integer id) {
        return machineRepository.findById(id);
    }

    public Machine save(Machine machine) {
        return machineRepository.save(machine);
    }

    public void deleteById(Integer id) {
        machineRepository.deleteById(id);
    }
}
