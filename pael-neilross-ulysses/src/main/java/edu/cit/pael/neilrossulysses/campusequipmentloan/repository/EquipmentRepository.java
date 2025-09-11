package edu.cit.pael.neilrossulysses.campusequipmentloan.repository;

import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByAvailableTrue();
}