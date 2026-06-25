package com.miniproject.absenhr.repository;

import com.miniproject.absenhr.model.Employee;
import com.miniproject.absenhr.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUser(Users user);
    List<Employee> findByEmployeeCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(
            String employeeCode,
            String fullName,
            String email,
            String phone);
    List<Employee> findByEmployeeCodeContainingIgnoreCaseAndFullNameContainingIgnoreCase(
            String employeeCode,
            String fullName);
}
