package com.example.school.model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;
    
    @NotNull
    @Size(min = 3, max = 255)
    private String name;
    
    @NotBlank
    @Email
    private String email;
    
}
