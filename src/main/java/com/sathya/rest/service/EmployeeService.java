package com.sathya.rest.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sathya.rest.models.Employee;
import com.sathya.rest.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;

	public Employee saveEmployee(Employee employee) {
		
		Employee emp=employeeRepository.save(employee);
		return emp;
	}

	public List<Employee> saveEmployees(List<Employee> employees) {
		List<Employee> emps = employeeRepository.saveAll(employees);
		return emps;
	}

	public List<Employee> getAllEmployees() {
		List<Employee> emps = employeeRepository.findAll();
		return emps;
		
	}

	public Optional<Employee> getById(Long id) {
		Optional<Employee> optionalemp = employeeRepository.findById(id);
		return optionalemp;
		
		
	}

	public Optional<Employee> getByEmail(String email) {
	Optional<Employee> optionalemployee = employeeRepository.getByEmail(email);
	return optionalemployee;
		
	}

	public boolean deleteEmployeeById(Long id) {
		boolean status = employeeRepository.existsById(id);
		if(status)
		{
			employeeRepository.deleteById(id);
			return true;
		}
		else
		{
			return false;
		}
		
	}

	public boolean deleteEmployeeByEmail(String email) {
		boolean status = employeeRepository.existsByEmail(email);
		if(status)
		{
			employeeRepository.deleteByEmail(email);
			return true;
		}
		else
		{
			return false;
		}
	}

	public void deleteAllEmployees() {
		employeeRepository.deleteAll();
		
	}

	public Optional<Employee> updateEmployeeById(Long id, Employee newEmployee)
	{
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		
		if(optionalEmployee.isPresent())
		{
			Employee existingEmployee = optionalEmployee.get();
			
			existingEmployee.setName(newEmployee.getName());
			existingEmployee.setSalary(newEmployee.getSalary());
			existingEmployee.setDepartment(newEmployee.getDepartment());
			existingEmployee.setAddress(newEmployee.getAddress());
			existingEmployee.setEmail(newEmployee.getEmail());
			Employee updatedEmployee = employeeRepository.save(existingEmployee);
			return Optional.of(updatedEmployee);
		}
		else
		{
			return Optional.empty();
		}
	}

	public Optional<Employee> updateEmployee(Long id, Map<String,Object> updates)
	{
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if(optionalEmployee.isPresent())
		{
			Employee existEmployee = optionalEmployee.get();
			updates.forEach((key, value) -> {
				switch (key) {
				case "name":
					existEmployee.setName((String) value);
					break;
				case "salary":
					existEmployee.setSalary((double) value);
					break;
				case "department":
					existEmployee.setDepartment((String) value);
					break;
				case "address":
					existEmployee.setAddress((String) value);
					break;
				case "email":
					existEmployee.setEmail((String) value);
					break;
				}
			}
			);
			Employee updatedEmployee = employeeRepository.save(existEmployee);
			return Optional.of(updatedEmployee);
		}
		else
		{
			return Optional.empty();
		}
	}

	@Cacheable("names")
	public List<String> getNames()
	{
		System.out.println("fetching the names");
		return List.of("pavan","anil","shiva");
	}
	
}
 