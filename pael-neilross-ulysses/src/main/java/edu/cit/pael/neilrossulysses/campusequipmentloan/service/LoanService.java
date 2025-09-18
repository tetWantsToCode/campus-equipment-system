package edu.cit.pael.neilrossulysses.campusequipmentloan.service;

import edu.cit.pael.neilrossulysses.campusequipmentloan.exception.LoanNotFoundException;
import edu.cit.pael.neilrossulysses.campusequipmentloan.exception.MaxActiveLoansException;
import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Equipment;
import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Loan;
import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Student;
import edu.cit.pael.neilrossulysses.campusequipmentloan.repository.EquipmentRepository;
import edu.cit.pael.neilrossulysses.campusequipmentloan.repository.LoanRepository;
import edu.cit.pael.neilrossulysses.campusequipmentloan.repository.StudentRepository;
import edu.cit.pael.neilrossulysses.campusequipmentloan.exception.EquipmentNotAvailableException;
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

    public Loan createLoan(Long equipmentId, Long studentId, LocalDate startDate) {
        // Rule 1: Max 2 active loans
        if (loanRepo.countByStudentIdAndStatus(studentId, "ACTIVE") >= 2) {
            throw new MaxActiveLoansException("Max 2 active loans allowed per student");
        }

        Equipment equipment = equipRepo.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        if (!equipment.isAvailable()) {
            throw new EquipmentNotAvailableException("Equipment is not available");
        }

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Loan loan = new Loan();
        loan.setEquipment(equipment);
        loan.setStudent(student);
        loan.setStartDate(startDate != null ? startDate : LocalDate.now());
        loan.setDueDate(loan.getStartDate().plusDays(7));  // Automatically set dueDate 7 days after startDate
        loan.setStatus("ACTIVE");

        equipment.setAvailable(false);
        equipRepo.save(equipment);

        return loanRepo.save(loan);
    }

    public Loan returnLoan(Long loanId, LocalDate returnDate) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + loanId));

        loan.setReturnDate(returnDate);

        if (returnDate.isAfter(loan.getDueDate())) {
            loan.setStatus("OVERDUE");
            long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), returnDate);
            double penalty = daysLate * 50.0;  // ₱50.0 per day late as double
            loan.setPenalty(penalty);
            System.out.println("Penalty: ₱" + penalty);
        } else {
            loan.setStatus("RETURNED");
            loan.setPenalty(0.0);  // no penalty
        }

        Equipment equipment = loan.getEquipment();
        equipment.setAvailable(true);
        equipRepo.save(equipment);

        return loanRepo.save(loan);
    }

    public List<Loan> getAllLoans() {
        List<Loan> loans = loanRepo.findAll();
        // Update statuses on the fly for all loans that are overdue but not returned
        loans.forEach(this::updateLoanStatusIfOverdue);
        return loans;
    }

    public Loan saveOrUpdateLoan(Loan loan) {
        updateLoanStatusIfOverdue(loan);

        // Also manage equipment availability if needed (optional, depends on your logic)
        if ("ACTIVE".equals(loan.getStatus())) {
            loan.getEquipment().setAvailable(false);
        } else {
            loan.getEquipment().setAvailable(true);
        }
        equipRepo.save(loan.getEquipment());

        return loanRepo.save(loan);
    }

    private void updateLoanStatusIfOverdue(Loan loan) {
        if (loan.getReturnDate() == null &&
                (loan.getStatus() == null || "ACTIVE".equals(loan.getStatus())) &&
                LocalDate.now().isAfter(loan.getDueDate())) {
            loan.setStatus("OVERDUE");
        }
    }
}
