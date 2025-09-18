package edu.cit.pael.neilrossulysses.campusequipmentloan.repository;
import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    long countByStudentIdAndStatus(Long studentId, String status);

    // Find all loans with a specific status (e.g. OVERDUE)
    List<Loan> findByStatus(String status);

    // Find loans that are overdue: not returned and due date before today
    List<Loan> findByReturnDateIsNullAndDueDateBefore(LocalDate date);
}
