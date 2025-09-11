package edu.cit.pael.neilrossulysses.campusequipmentloan.controller;

import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Equipment;
import edu.cit.pael.neilrossulysses.campusequipmentloan.repository.EquipmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    private final EquipmentRepository equipmentRepo;

    public EquipmentController(EquipmentRepository equipmentRepo) {
        this.equipmentRepo = equipmentRepo;
    }

    // Add new equipment
    @PostMapping
    public Equipment addEquipment(@RequestBody Equipment equipment) {
        equipment.setAvailable(true); // default available
        return equipmentRepo.save(equipment);
    }

    // List all available equipment
    @GetMapping("/available")
    public List<Equipment> getAvailableEquipment() {
        return equipmentRepo.findByAvailableTrue();
    }

    // List all equipment
    @GetMapping
    public List<Equipment> getAllEquipment() {
        return equipmentRepo.findAll();
    }
}
