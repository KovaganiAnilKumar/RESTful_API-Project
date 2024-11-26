package com.sathya.rest.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.metamodel.mapping.EntityValuedModelPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sathya.rest.exceptions.EmployeeNotFoundException;
import com.sathya.rest.models.Employee;
import com.sathya.rest.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	@PostMapping("/saveemp")
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee)
	{
		Employee emp = employeeService.saveEmployee(employee);
		return ResponseEntity.status(HttpStatus.CREATED)
							.header("employee status", "employee saved successfully")
							.body(emp);
	}
	@PostMapping("/saveemps")
	public ResponseEntity<List<Employee>> saveemployees(@RequestBody List<Employee> employees)
	{
		List<Employee> emps = employeeService.saveEmployees(employees);
		return ResponseEntity.status(HttpStatus.CREATED)
				             .header("employees status", "employees saves successfully")
				             .body(emps);
		}
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<Employee>> getAllEmployees()
	{
		List<Employee> emps = employeeService.getAllEmployees();
		return ResponseEntity.status(HttpStatus.OK)
								.header("status", "Data reading is successful")
								.body(emps);
	}
	
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id)
	{
		Optional<Employee> optionalemp = employeeService.getById(id);
		if(optionalemp.isPresent())
		{
//			return ResponseEntity.status(HttpStatus.OK)
//								.body(optionalemp.get());
			
			Employee employee = optionalemp.get();
			
			//create an entityModel for he user
			EntityModel<Employee> entityModel = EntityModel.of(employee);
			
			//add self tag
	entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getById(id)).withSelfRel());
			
	
		//add link to update the user
	entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).updateEmployeeById(id,employee)).withRel("update"));
	
	//add link to delete the user
	entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).deleteEmployeeById(id)).withRel("delete"));
	
	//add link  to get all the users
	entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).getAllEmployees()).withRel("all-users"));
	  return ResponseEntity.status(HttpStatus.OK)
			  .body(entityModel);
	
		}
		else
		{
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					  			  .body("Emp is not found with id.."+id);
			
			throw new EmployeeNotFoundException("employee not found with id "+id);
		}
	}
	
	@GetMapping("/getbyemail/{email}")
	public ResponseEntity<?> getByEmail(@PathVariable String email)
	{
		Optional<Employee> optionalemployee = employeeService.getByEmail(email);
		if(optionalemployee.isPresent())
		{
			return ResponseEntity.status(HttpStatus.OK)
					 			 .body(optionalemployee.get());
			}
		else
		{
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					             .body("employee is not found with email"+email);
			
			throw new EmployeeNotFoundException("employee not found with email "+email);
		}
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id)
	{
		boolean status=employeeService.deleteEmployeeById(id);
		if(status)
		{
			return ResponseEntity.noContent().build();
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
								 .header("status", "data is not found")
								 .body("data is not present with id....."+id);
			
		}
	}
	
	@DeleteMapping("/deletebyemail/{email}")
	public ResponseEntity<?> deleteEmployeeByEmail(@PathVariable String email)
	{
		boolean status = employeeService.deleteEmployeeByEmail(email);
		if(status)
		{
			return ResponseEntity.noContent().build();
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
								 .header("status", "data is not found")
								 .body("data is not present with email....."+email);
			
		}
	}
	@DeleteMapping("/deleteall")
	public void deleteAllEmployees()
	{
		employeeService.deleteAllEmployees();
	}
	
	@PutMapping("/updatebyid/{id}")
	public ResponseEntity<?> updateEmployeeById(@PathVariable Long id, @RequestBody Employee newEmployee)
	{
		Optional<Employee> updatedEmployee = employeeService.updateEmployeeById(id,newEmployee);
		if(updatedEmployee.isPresent())
		{
			return ResponseEntity.status(HttpStatus.OK)
								 .body(updatedEmployee);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
								  .body("data is not found with id"+id);
		}
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Map<String,Object> updates)
	{
		Optional<Employee> optionalEmployee = employeeService.updateEmployee(id, updates);
		if(optionalEmployee.isPresent())
		{
			return ResponseEntity.status(HttpStatus.OK)
								 .body(optionalEmployee.get());
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
								 .body("data is not found with id..."+id);
		}
	}
	
	
	
	
	@GetMapping("/getnames")
	public List<String> getNames()
	{
		
		List<String> names = employeeService.getNames();
		return names;
		
	}
	
	
	
	
}
