package edu.cit.pael.neilrossulysses.campusequipmentloan.controller;

import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Equipment;
import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Loan;
import edu.cit.pael.neilrossulysses.campusequipmentloan.repository.EquipmentRepository;
import edu.cit.pael.neilrossulysses.campusequipmentloan.repository.LoanRepository;
import edu.cit.pael.neilrossulysses.campusequipmentloan.service.LoanService;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;
    private final EquipmentRepository equipmentRepo;
    private final LoanRepository loanRepo;

    public LoanController(LoanService loanService, EquipmentRepository equipmentRepo, LoanRepository loanRepo) {
        this.loanService = loanService;
        this.equipmentRepo = equipmentRepo;
        this.loanRepo = loanRepo;
    }

    @PostMapping
    public Loan createLoan(@RequestBody JsonNode json) {
        Long equipmentId = json.get("equipmentId").asLong();
        Long studentId = json.get("studentId").asLong();
        return loanService.createLoan(equipmentId, studentId);
    }

    @PostMapping("/{id}/return")
    public Loan returnLoan(@PathVariable Long id, @RequestBody JsonNode json) {
        String returnDateStr = json.get("returnDate").asText();
        LocalDate returnDate = LocalDate.parse(returnDateStr);
        return loanService.returnLoan(id, returnDate);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }
}