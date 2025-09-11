package edu.cit.pael.neilrossulysses.campusequipmentloan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentNo;
    private String name;
    private String email;

    // getters and setters
    public Long getID(){
        return id;
    }

    public void setID(Long id){
        this.id = id;
    }

    public String getStudentNo(){
        return studentNo;
    }

    public void setStudentNo(String studentNo){
        this.studentNo = studentNo;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
