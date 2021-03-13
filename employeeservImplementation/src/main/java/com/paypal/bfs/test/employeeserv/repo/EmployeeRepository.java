package com.paypal.bfs.test.employeeserv.repo;

import com.paypal.bfs.test.employeeserv.dto.EmployeeDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeDTO, Integer> {
}
