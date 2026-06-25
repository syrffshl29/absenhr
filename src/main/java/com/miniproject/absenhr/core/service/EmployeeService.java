package com.miniproject.absenhr.core.service;

import com.miniproject.absenhr.core.dto.request.EmployeeRequestDto;
import com.miniproject.absenhr.core.dto.response.EmployeeResponseDto;
import com.miniproject.absenhr.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDto create (EmployeeRequestDto dto);
    Employee save(Employee employee);
    EmployeeResponseDto update(
            Long id,
            EmployeeRequestDto dto);
    List<EmployeeResponseDto>findAll();
    EmployeeResponseDto findById(Long id);
    List<EmployeeResponseDto> search(String keyword);
    Page<EmployeeResponseDto> findAll(
            int page,
            int size);
    List<EmployeeResponseDto> filter(
            String employeeCode,
            String fullName);
    void delete(Long id);
}
