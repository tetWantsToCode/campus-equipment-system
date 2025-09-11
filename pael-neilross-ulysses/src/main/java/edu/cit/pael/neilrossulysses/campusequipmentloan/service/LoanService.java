package edu.cit.pael.neilrossulysses.campusequipmentloan.service;

import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Equipment;
import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Loan;
import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Student;
import edu.cit.pael.neilrossulysses.campusequipmentloan.repository.EquipmentRepository;
import edu.cit.pael.neilrossulysses.campusequipmentloan.repository.LoanRepository;
import edu.cit.pael.neilrossulysses.campusequipmentloan.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepo;
    private final EquipmentRepository equipRepo;
    private final StudentRepository studentRepo;

    public LoanService(LoanRepository loanRepo, EquipmentRepository equipRepo, StudentRepository studentRepo) {
        this.loanRepo = loanRepo;
        this.equipRepo = equipRepo;
        this.studentRepo = studentRepo;
    }

    public Loan createLoan(Long equipmentId, Long studentId) {
        // Rule 1: Max 2 active loans
        if (loanRepo.countByStudentIdAndStatus(studentId, "ACTIVE") >= 2) {
            throw new RuntimeException("Max 2 active loans allowed per student");
        }

        Equipment equipment = equipRepo.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        if (!equipment.isAvailable()) {
            throw new RuntimeException("Equipment is not available");
        }

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Loan loan = new Loan();
        loan.setEquipment(equipment);
        loan.setStudent(student);
        loan.setStartDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(7)); // Rule 2: loan length = 7 days
        loan.setStatus("ACTIVE");

        equipment.setAvailable(false);
        equipRepo.save(equipment);

        return loanRepo.save(loan);
    }

    public Loan returnLoan(Long loanId) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setReturnDate(LocalDate.now());
        if (loan.getReturnDate().isAfter(loan.getDueDate())) {
            loan.setStatus("OVERDUE");
            long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
            long penalty = daysLate * 50; // Rule 4: ₱50/day late
            System.out.println("Penalty: ₱" + penalty);
        } else {
            loan.setStatus("RETURNED");
        }

        Equipment equipment = loan.getEquipment();
        equipment.setAvailable(true);
        equipRepo.save(equipment);

        return loanRepo.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepo.findAll();
    }
}