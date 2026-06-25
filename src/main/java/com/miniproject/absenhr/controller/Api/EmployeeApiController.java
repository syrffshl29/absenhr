package com.miniproject.absenhr.controller.Api;

import com.miniproject.absenhr.core.dto.request.EmployeeRequestDto;
import com.miniproject.absenhr.core.dto.response.ApiResponseDto;
import com.miniproject.absenhr.core.dto.response.EmployeeResponseDto;
import com.miniproject.absenhr.core.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeApiController {

    private final EmployeeService employeeService;

    public EmployeeApiController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeResponseDto create(@RequestBody EmployeeRequestDto dto) {
        System.out.println(">>> CONTROLLER MASUK");
        System.out.println(dto);
        return employeeService.create(dto);
    }
    @GetMapping
    public List<EmployeeResponseDto> findAll() {
        return employeeService.findAll();
    }
    @GetMapping("/{id}")
    public EmployeeResponseDto findById(
            @PathVariable Long id) {

        return employeeService.findById(id);
    }
    @PutMapping("/{id}")
    public EmployeeResponseDto update(
            @PathVariable Long id,
            @RequestBody EmployeeRequestDto dto) {

        return employeeService.update(
                id,
                dto
        );
    }
    @GetMapping("/search")
    public List<EmployeeResponseDto> search(
            @RequestParam String keyword) {

        return employeeService.search(keyword);
    }
    @GetMapping("/paging")
    public Page<EmployeeResponseDto> findAllPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return employeeService.findAll(
                page,
                size);
    }
    @GetMapping("/filter")
    public List<EmployeeResponseDto> filter(
            @RequestParam(required = false) String employeeCode,
            @RequestParam(required = false) String fullName) {

        return employeeService.filter(
                employeeCode,
                fullName
        );
    }
    @DeleteMapping("/{id}")
    public ApiResponseDto delete(
            @PathVariable Long id) {

        employeeService.delete(id);

        return new ApiResponseDto(
                true,
                "Employee deleted successfully"
        );
    }
}