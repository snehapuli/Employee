package com.example.EmployeeApplicationProject.Service;

import com.example.EmployeeApplicationProject.Entity.Employee;
import com.example.EmployeeApplicationProject.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class EmployeeService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee, MultipartFile resume, MultipartFile idProof) throws IOException {
        System.out.println("Here");
        if (resume != null && !resume.isEmpty()) {
            employee.setResumePath(storeFile(resume));
        } else
        {
            employee.setResumePath("empty");
        }
        if (idProof != null && !idProof.isEmpty()) {
            employee.setIdProofPath(storeFile(idProof));
        } else {
            employee.setIdProofPath("empty");
        }
        return employeeRepository.save(employee);
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());
        return filePath.toString();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public Employee updateEmployee(Long id, Employee employeeData, MultipartFile resume, MultipartFile idProof) throws IOException {
        Employee employee = getEmployeeById(id);
        employee.setFullName(employeeData.getFullName());
        employee.setEmployeeId(employeeData.getEmployeeId());
        employee.setDepartment(employeeData.getDepartment());
        employee.setDesignation(employeeData.getDesignation());
        employee.setDateOfJoining(employeeData.getDateOfJoining());
        employee.setEmail(employeeData.getEmail());
        employee.setPhoneNumber(employeeData.getPhoneNumber());
        employee.setAddress(employeeData.getAddress());

        if (resume != null && !resume.isEmpty()) {
            employee.setResumePath(storeFile(resume));
        }
        if (idProof != null && !idProof.isEmpty()) {
            employee.setIdProofPath(storeFile(idProof));
        }

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }
}
