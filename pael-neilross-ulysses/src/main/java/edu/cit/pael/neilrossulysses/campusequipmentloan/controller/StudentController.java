package edu.cit.pael.neilrossulysses.campusequipmentloan.controller;

import edu.cit.pael.neilrossulysses.campusequipmentloan.model.Student;
import edu.cit.pael.neilrossulysses.campusequipmentloan.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentRepository studentRepo;

    public StudentController(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    // Add new student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentRepo.save(student);
    }

    // List all students (optional)
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
}