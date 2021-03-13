package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dto.EmployeeDTO;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import com.paypal.bfs.test.employeeserv.utils.ConversionUtils;
import com.paypal.bfs.test.employeeserv.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployee(Integer.valueOf(id)).orElse(null);
            if(employeeDTO == null){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            Employee employee = ConversionUtils.convertDbDtoToEmployee(employeeDTO);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Employee> addEmployee(Employee employee) {
        if(ValidationUtils.isValidRequest(employee)) {
          try {
              EmployeeDTO dto = ConversionUtils.convertToDbDTO(employee);
              employeeService.saveEmployee(dto);
              employee.setId(dto.getId());
              employee.setAdditionalProperty("error", null);
              employee.setAdditionalProperty("success", true);
              return new ResponseEntity<>(employee, HttpStatus.OK);
          } catch (ParseException e) {
              employee.setAdditionalProperty("error","Invalid Date of Birth format, Please provide DOB as dd/MM/YYYY");
              employee.setAdditionalProperty("success", false);
              return new ResponseEntity<>(employee, HttpStatus.BAD_REQUEST);
          } catch (Exception e) {
              employee.setAdditionalProperty("error","Error while processing, Please check with the team.");
              employee.setAdditionalProperty("success", false);
              return new ResponseEntity<>(employee, HttpStatus.INTERNAL_SERVER_ERROR);
          }
        } else {
            employee.setAdditionalProperty("error","Invalid Request parameters");
            employee.setAdditionalProperty("success", false);
            return new ResponseEntity<>(employee, HttpStatus.BAD_REQUEST);
        }
    }
}
