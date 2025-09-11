package edu.cit.pael.neilrossulysses.campusequipmentloan.repository;

import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    long countByStudentIdAndStatus(Long studentId, String status);
}