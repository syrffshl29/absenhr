package com.miniproject.absenhr.config;

import com.miniproject.absenhr.model.Employee;
import com.miniproject.absenhr.model.enums.Role;
import com.miniproject.absenhr.model.Users;
import com.miniproject.absenhr.repository.EmployeeRepository;
import com.miniproject.absenhr.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public DataInitializer(UserRepository userRepository
                        ,PasswordEncoder passwordEncoder
                        ,EmployeeRepository employeeRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Buat akses admin
            Users admin = new Users();
            admin.setUsername("Syarifaf29");
            admin.setPassword(passwordEncoder.encode("Admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);

            // EMPLOYEE 1
            createEmployee(
                    "employee01",
                    "Employee123",
                    "EMP001",
                    "Budi Santoso",
                    "Depok",
                    "budi@mail.com",
                    "081111111111"
            );

            // EMPLOYEE 2
            createEmployee(
                    "employee02",
                    "Employee123",
                    "EMP002",
                    "Andi Wijaya",
                    "Jakarta",
                    "andi@mail.com",
                    "082222222222"
            );

            // EMPLOYEE 3
            createEmployee(
                    "employee03",
                    "Employee123",
                    "EMP003",
                    "Rina Putri",
                    "Bandung",
                    "rina@mail.com",
                    "083333333333"
            );

            System.out.println("Data akses awal berhasil ditambahkan.");

        } else {
            System.out.println("Data akses sudah ada, tidak perlu inisialisasi.");
        }
    }
    private void createEmployee(
            String username,
            String password,
            String employeeCode,
            String fullName,
            String address,
            String email,
            String phone) {

        Users user = new Users();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.EMPLOYEE);

        userRepository.save(user);

        Employee employee = new Employee();
        employee.setEmployeeCode(employeeCode);
        employee.setFullName(fullName);
        employee.setAddress(address);
        employee.setEmail(email);
        employee.setPhone(phone);

        // Relasi Employee -> User
        employee.setUser(user);

        employeeRepository.save(employee);
    }
}