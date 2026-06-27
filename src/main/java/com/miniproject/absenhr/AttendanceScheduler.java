package com.miniproject.absenhr;

import com.miniproject.absenhr.model.Attendance;
import com.miniproject.absenhr.model.Employee;
import com.miniproject.absenhr.model.enums.AttendanceStatus;
import com.miniproject.absenhr.repository.AttendanceRepository;
import com.miniproject.absenhr.repository.EmployeeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class AttendanceScheduler {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceScheduler(
            AttendanceRepository attendanceRepository,
            EmployeeRepository employeeRepository) {

        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Scheduled(cron = "0 59 23 * * *")
//
    public void markAbsentEmployees() {

        LocalDate today = LocalDate.now();

        List<Employee> employees =
                employeeRepository.findAll();

        for (Employee employee : employees) {

            boolean alreadyAttendance =
                    attendanceRepository
                            .findByEmployeeAndAttendanceDate(
                                    employee,
                                    today
                            )
                            .isPresent();

            if (!alreadyAttendance) {

                Attendance attendance =
                        new Attendance();

                attendance.setEmployee(employee);
                attendance.setAttendanceDate(today);
                attendance.setStatus(
                        AttendanceStatus.Absent
                );

                attendanceRepository.save(attendance);
            }
        }

        System.out.println(
                "Auto absent selesai dijalankan"
        );
    }
}
