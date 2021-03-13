package com.paypal.bfs.test.employeeserv.utils;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dto.EmployeeDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConversionUtils {
    private static DateFormat DF = new SimpleDateFormat("dd/MM/YYYY");
    public static EmployeeDTO convertToDbDTO(Employee employee) throws ParseException {
        EmployeeDTO e = new EmployeeDTO();
        e.setId(employee.getId());
        e.setFirstName(employee.getFirstName());
        e.setAddLine2(employee.getLastName());
        e.setDateOfBirth(DF.parse(employee.getDateOfBirth()));
        e.setAddLine1(employee.getAddress().getLine1());
        e.setAddLine2(employee.getAddress().getLine2());
        e.setCity(employee.getAddress().getCity());
        e.setState(employee.getAddress().getState());
        e.setCountry(employee.getAddress().getCountry());
        e.setZipCode(employee.getAddress().getZipCode());
        return e;
    }

    public static Employee convertDbDtoToEmployee(EmployeeDTO dto) throws ParseException {
        Employee e = new Employee();
        Address address = new Address();
        e.setId(dto.getId());
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setDateOfBirth(DF.format(dto.getDateOfBirth()));
        address.setLine1(dto.getAddLine1());
        address.setLine2(dto.getAddLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setZipCode(dto.getZipCode());
        e.setAddress(address);
        return e;
    }

}
