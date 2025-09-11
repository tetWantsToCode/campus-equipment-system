package edu.cit.pael.neilrossulysses.campusequipmentloan.repository;

import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}