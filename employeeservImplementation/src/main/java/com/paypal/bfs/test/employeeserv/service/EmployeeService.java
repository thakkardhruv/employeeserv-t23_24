package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.dto.EmployeeDTO;
import com.paypal.bfs.test.employeeserv.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<EmployeeDTO> getEmployee(Integer id) {
        return employeeRepository.findById(id);
    }

    public void saveEmployee(EmployeeDTO emp) {
        employeeRepository.save(emp);
    }

}
