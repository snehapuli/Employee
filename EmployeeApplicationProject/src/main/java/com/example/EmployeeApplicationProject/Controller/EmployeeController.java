package com.example.EmployeeApplicationProject.Controller;

import com.example.EmployeeApplicationProject.Entity.Employee;
import com.example.EmployeeApplicationProject.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/employees")
@CrossOrigin(origins = "${allowed.origins}")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employee> createEmployee(
            @RequestParam("employee") Employee employee,
            @RequestParam(value = "resume", required = false) MultipartFile resume,
            @RequestParam(value = "idProof", required = false) MultipartFile idProof) {
        try {
            Employee saved = employeeService.saveEmployee(employee, resume, idProof);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed", e);
        }
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestPart("employee") Employee employee,
            @RequestPart(value = "resume", required = false) MultipartFile resume,
            @RequestPart(value = "idProof", required = false) MultipartFile idProof) {
        try {
            Employee updated = employeeService.updateEmployee(id, employee, resume, idProof);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File update failed", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
