package com.example.school.controller;

import com.example.school.model.Student;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.school.repository.StudentRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author caian
 */
@RestController
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllProducts() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);

    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student>
            getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<Student> studentO = studentRepository.findById(id);
        if (studentO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentO.get(), HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> saveProduct(@RequestBody @Valid Student student) {
        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?>
            deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<Student> productO
                = studentRepository.findById(id);
        if (productO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentRepository.delete(productO.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student>
            updateProduct(@PathVariable(value = "id") UUID id,
                    @RequestBody @Valid Student student) {
        Optional<Student> studentO = studentRepository.findById(id);
        if (studentO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        student.setId(id);
        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}