package com.paypal.bfs.test.employeeserv.utils;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.apache.commons.lang.StringUtils;

public class ValidationUtils {
    public static boolean isValidRequest(Employee emp) {
        boolean isValidRequest = true;
        if(StringUtils.isEmpty(emp.getFirstName()))
            isValidRequest = false;
        if(StringUtils.isEmpty(emp.getLastName()))
            isValidRequest = false;
        if(StringUtils.isEmpty(emp.getDateOfBirth()))
            isValidRequest = false;
        if(emp.getAddress() == null)
            return false;
        if(StringUtils.isEmpty(emp.getAddress().getLine1()))
            isValidRequest = false;
        if(StringUtils.isEmpty(emp.getAddress().getCity()))
            isValidRequest = false;
        if(StringUtils.isEmpty(emp.getAddress().getState()))
            isValidRequest = false;
        if(StringUtils.isEmpty(emp.getAddress().getCountry()))
            isValidRequest = false;
        if(StringUtils.isEmpty(emp.getAddress().getZipCode()))
            isValidRequest = false;
        return isValidRequest;
    }
}
