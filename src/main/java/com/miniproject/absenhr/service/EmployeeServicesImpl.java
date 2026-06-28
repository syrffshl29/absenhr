package com.miniproject.absenhr.service;

import com.miniproject.absenhr.core.dto.request.EmployeeRequestDto;
import com.miniproject.absenhr.core.dto.response.EmployeeResponseDto;
import com.miniproject.absenhr.core.service.EmployeeService;
import com.miniproject.absenhr.model.Employee;
import com.miniproject.absenhr.model.Users;
import com.miniproject.absenhr.model.enums.Role;
import com.miniproject.absenhr.repository.AttendanceRepository;
import com.miniproject.absenhr.repository.EmployeeRepository;
import com.miniproject.absenhr.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EmployeeServicesImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AttendanceRepository attendanceRepository;

    public EmployeeServicesImpl(EmployeeRepository employeeRepository,
                                ModelMapper modelMapper,
                                UserRepository userRepository,
                                PasswordEncoder passwordEncoder,
                                AttendanceRepository attendanceRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.attendanceRepository = attendanceRepository;

    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);

    }

    @Override
    public EmployeeResponseDto create(EmployeeRequestDto dto) {

        Users user = new Users();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.EMPLOYEE);
        userRepository.save(user);


        Employee employee = new Employee();
        employee.setEmployeeCode(dto.getEmployeeCode());
        employee.setFullName(dto.getFullName());
        employee.setAddress(dto.getAddress());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setUser(user);

        Employee saved = employeeRepository.save(employee);

        EmployeeResponseDto res = new EmployeeResponseDto();
        res.setId(saved.getId());
        res.setEmployeeCode(saved.getEmployeeCode());
        res.setFullName(saved.getFullName());
        res.setAddress(saved.getAddress());
        res.setEmail(saved.getEmail());
        res.setPhone(saved.getPhone());

        return res;
    }

    @Override
    public List<EmployeeResponseDto> findAll() {

        return employeeRepository.findAll()
                .stream()
                .map(emp -> modelMapper.map(emp, EmployeeResponseDto.class))
                .toList();
    }

    @Override
    public EmployeeResponseDto findById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        return modelMapper.map(
                employee,
                EmployeeResponseDto.class
        );
    }
    @Override
    public EmployeeResponseDto update(
            Long id,
            EmployeeRequestDto dto) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));


        employee.setFullName(dto.getFullName());
        employee.setAddress(dto.getAddress());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());

        Users users = employee.getUser();
        users.setUsername(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            users.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(users);
        Employee updated = employeeRepository.save(employee);

        return modelMapper.map(
                updated,
                EmployeeResponseDto.class
        );
    }
    @Override
    public List<EmployeeResponseDto> search(String keyword) {

        return employeeRepository
                .findByEmployeeCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword,
                        keyword
                )
                .stream()
                .map(employee ->
                        modelMapper.map(
                                employee,
                                EmployeeResponseDto.class
                        ))
                .toList();
    }

    @Override
    public Page<EmployeeResponseDto> findAll(
            int page,
            int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        return employeeRepository
                .findAll(pageable)
                .map(employee ->
                        modelMapper.map(
                                employee,
                                EmployeeResponseDto.class
                        ));
    }

    @Override
    public List<EmployeeResponseDto> filter(
            String employeeCode,
            String fullName) {

        return employeeRepository
                .findByEmployeeCodeContainingIgnoreCaseAndFullNameContainingIgnoreCase(
                        employeeCode == null ? "" : employeeCode,
                        fullName == null ? "" : fullName
                )
                .stream()
                .map(employee ->
                        modelMapper.map(
                                employee,
                                EmployeeResponseDto.class
                        ))
                .toList();
    }

    @Override
    public void delete(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        Users user = employee.getUser();

        attendanceRepository.deleteByEmployee(employee);

        employeeRepository.delete(employee);

        if (user != null) {
            userRepository.delete(user);
        }
    }
    @Override
    public EmployeeResponseDto getProfile(String username) {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        return modelMapper.map(employee, EmployeeResponseDto.class);
    }
    @Override
    public EmployeeResponseDto updateProfile(
            String username,
            EmployeeRequestDto dto) {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        employee.setFullName(dto.getFullName());
        employee.setAddress(dto.getAddress());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());

        user.setUsername(dto.getUsername());

        if (dto.getPassword() != null &&
                !dto.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(user);
        employeeRepository.save(employee);

        return modelMapper.map(employee, EmployeeResponseDto.class);
    }
}