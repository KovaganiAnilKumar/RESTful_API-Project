package com.sathya.rest.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employeerest")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	 @NotNull(message = "Name cannot be null")
	    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
	private String name;
	 
	 @NotNull(message = "Salary cannot be null")
	    @Positive(message = "Salary must be a positive number")
	    @Max(value = 1_000_000, message = "Salary must not exceed 1,000,000")
	private double salary;
	 
	 @NotNull(message = "Department cannot be null")
	    @Size(min = 1, max = 50, message = "Department must be between 1 and 50 characters")
	private String department;
	 
	 @NotBlank(message = "Address cannot be empty or whitespace")
	    @Size(max = 255, message = "Address must not exceed 255 characters")
	    @Pattern(regexp = "^[a-zA-Z0-9\\s,.-]+$", message = "Address can only contain letters, numbers, spaces, commas, dots, and hyphens")
	private String address;
	 
	 @NotNull(message = "Email cannot be null")
	    @Email(message = "Email should be valid")
	    @Size(max = 100, message = "Email must not exceed 100 characters")
	private String email;
}
