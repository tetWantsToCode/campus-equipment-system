package edu.cit.pael.neilrossulysses.campusequipmentloan.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EquipmentNotAvailableException.class)
    public ResponseEntity<String> handleEquipmentNotAvailableException(EquipmentNotAvailableException ex) {
        return ResponseEntity.ok(ex.getMessage());
    }

    @ExceptionHandler(MaxActiveLoansException.class)
    public ResponseEntity<String> handleMaxActiveLoansException(MaxActiveLoansException ex) {
        return ResponseEntity.ok(ex.getMessage());
    }

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<String> handleLoanNotFoundException(LoanNotFoundException ex) {
        // Return 200 OK with the exception message
        return ResponseEntity.ok(ex.getMessage());
    }
}
