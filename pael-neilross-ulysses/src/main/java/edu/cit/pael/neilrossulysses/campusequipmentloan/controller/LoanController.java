package edu.cit.pael.neilrossulysses.campusequipmentloan.controller;

import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Loan;
import edu.cit.pael.neilrossulysses.campusequipmentloan.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public Loan createLoan(@RequestParam Long equipmentId, @RequestParam Long studentId) {
        return loanService.createLoan(equipmentId, studentId);
    }

    @PostMapping("/{id}/return")
    public Loan returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }
}