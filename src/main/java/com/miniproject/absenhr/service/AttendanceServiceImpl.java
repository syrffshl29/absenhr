package com.miniproject.absenhr.service;

import com.miniproject.absenhr.controller.exception.BusinessException;
import com.miniproject.absenhr.controller.exception.ResourceNotFoundException;
import com.miniproject.absenhr.core.dto.response.*;
import com.miniproject.absenhr.core.service.AttendanceService;
import com.miniproject.absenhr.model.Attendance;
import com.miniproject.absenhr.model.Employee;
import com.miniproject.absenhr.model.Users;
import com.miniproject.absenhr.model.enums.AttendanceStatus;
import com.miniproject.absenhr.repository.AttendanceRepository;
import com.miniproject.absenhr.repository.EmployeeRepository;
import com.miniproject.absenhr.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository,
                                 UserRepository userRepository,
                                 EmployeeRepository employeeRepository,
                                 ModelMapper modelMapper) {

        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void checkIn(String username, String location) {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LocalDate today = LocalDate.now();

        Optional<Attendance> existingAttendance =
                attendanceRepository.findByEmployeeAndAttendanceDate(
                        employee,
                        today
                );

        if (existingAttendance.isPresent()) {
            throw new BusinessException("Anda sudah check in hari ini");
        }

        Attendance attendance = new Attendance();

        attendance.setEmployee(employee);
        attendance.setAttendanceDate(today);
        attendance.setLocation(location);

        LocalTime now = LocalTime.now();

        attendance.setCheckInTime(now);

        if (now.isAfter(LocalTime.of(8, 0))) {
            attendance.setStatus(AttendanceStatus.Late);
        } else {
            attendance.setStatus(AttendanceStatus.Present);
        }

        attendanceRepository.save(attendance);
    }

    @Override
    public void checkOut(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository
                .findByEmployeeAndAttendanceDate(employee, today)
                .orElseThrow(() ->
                        new BusinessException("Anda belum check in hari ini"));

        if (attendance.getCheckOutTime() != null) {
            throw new BusinessException("Anda sudah check out");
        }

        attendance.setCheckOutTime(LocalTime.now());

        attendanceRepository.save(attendance);
    }

    @Override
    public List<AttendanceResponseDto> getAttendanceHistory(String username) {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        List<Attendance> attendances =
                attendanceRepository.findByEmployee(employee);

        return attendances.stream()
                .map(attendance -> modelMapper.map(
                        attendance,
                        AttendanceResponseDto.class
                ))
                .toList();
    }

    @Override
    public DashboardSummaryDto getDashboardSummary() {

        LocalDate today = LocalDate.now();

        List<Employee> allEmployees =
                employeeRepository.findAll();

        List<Attendance> todayAttendances =
                attendanceRepository.findByAttendanceDate(today);

        DashboardSummaryDto response =
                new DashboardSummaryDto();

        response.setTotalEmployees(allEmployees.size());

        response.setPresentToday(todayAttendances.size());

        long lateToday = todayAttendances.stream()
                .filter(attendance ->
                        attendance.getStatus() == AttendanceStatus.Late)
                .count();

        response.setLateToday(lateToday);

        List<String> lateEmployees =
                todayAttendances.stream()
                        .filter(attendance ->
                                attendance.getStatus() == AttendanceStatus.Late)
                        .map(attendance ->
                                attendance.getEmployee().getFullName())
                        .toList();

        response.setLateEmployees(lateEmployees);

        Set<Long> presentEmployeeIds =
                todayAttendances.stream()
                        .map(attendance ->
                                attendance.getEmployee().getId())
                        .collect(Collectors.toSet());

        List<Employee> absentEmployees =
                allEmployees.stream()
                        .filter(employee ->
                                !presentEmployeeIds.contains(employee.getId()))
                        .toList();

        response.setAbsentToday(absentEmployees.size());

        response.setAbsentEmployees(
                absentEmployees.stream()
                        .map(Employee::getFullName)
                        .toList()
        );

        return response;
    }

    @Override
    public List<AttendanceTodayResponseDto> getTodayAttendances() {

        List<Attendance> attendances =
                attendanceRepository.findByAttendanceDate(LocalDate.now());

        return attendances.stream()
                .map(attendance -> {

                    AttendanceTodayResponseDto dto =
                            new AttendanceTodayResponseDto();

                    dto.setEmployeeCode(
                            attendance.getEmployee().getEmployeeCode());

                    dto.setFullName(
                            attendance.getEmployee().getFullName());

                    dto.setStatus(
                            attendance.getStatus().name());

                    dto.setAttendanceDate(
                            attendance.getAttendanceDate());

                    dto.setCheckInTime(
                            attendance.getCheckInTime());

                    dto.setCheckOutTime(
                            attendance.getCheckOutTime());

                    dto.setLocation(
                            attendance.getLocation());

                    return dto;
                })
                .toList();
    }

    @Override
    public List<AttendanceResponseDto> getAttendanceHistory(
            String username,
            LocalDate startDate,
            LocalDate endDate) {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        List<Attendance> attendances =
                attendanceRepository.findByEmployeeAndAttendanceDateBetween(
                        employee,
                        startDate,
                        endDate
                );

        return attendances.stream()
                .map(attendance ->
                        modelMapper.map(
                                attendance,
                                AttendanceResponseDto.class))
                .toList();
    }

    @Override
    public List<HrAttendanceResponseDto> getAttendanceReport(
            LocalDate startDate,
            LocalDate endDate) {

        List<Attendance> attendances =
                attendanceRepository.findByAttendanceDateBetween(
                        startDate,
                        endDate
                );

        return attendances.stream()
                .map(attendance -> {

                    HrAttendanceResponseDto dto =
                            modelMapper.map(
                                    attendance,
                                    HrAttendanceResponseDto.class);

                    dto.setEmployeeCode(
                            attendance.getEmployee().getEmployeeCode());

                    dto.setFullName(
                            attendance.getEmployee().getFullName());

                    dto.setAttendanceDate(
                            attendance.getAttendanceDate());

                    dto.setCheckInTime(
                            attendance.getCheckInTime());

                    dto.setCheckOutTime(
                            attendance.getCheckOutTime());

                    dto.setStatus(
                            attendance.getStatus());

                    dto.setLocation(
                            attendance.getLocation());

                    return dto;

                })
                .toList();
    }

    @Override
    public List<MonthlyAttendanceReportDto> getMonthlyAttendancesReport(
            int month,
            int year) {

        LocalDate startDate =
                LocalDate.of(year, month, 1);

        LocalDate endDate =
                startDate.withDayOfMonth(
                        startDate.lengthOfMonth());

        List<Attendance> attendances =
                attendanceRepository.findByAttendanceDateBetween(
                        startDate,
                        endDate
                );

        List<Employee> employees =
                employeeRepository.findAll();

        return employees.stream()
                .map(employee -> {

                    List<Attendance> employeeAttendances =
                            attendances.stream()
                                    .filter(attendance ->
                                            attendance.getEmployee()
                                                    .getId() == employee.getId())
                                    .toList();

                    MonthlyAttendanceReportDto dto =
                            new MonthlyAttendanceReportDto();

                    dto.setEmployeeCode(
                            employee.getEmployeeCode());

                    dto.setEmployeeName(
                            employee.getFullName());

                    dto.setPresent(
                            employeeAttendances.stream()
                                    .filter(attendance ->
                                            attendance.getStatus()
                                                    == AttendanceStatus.Present)
                                    .count());

                    dto.setLate(
                            employeeAttendances.stream()
                                    .filter(attendance ->
                                            attendance.getStatus()
                                                    == AttendanceStatus.Late)
                                    .count());

                    dto.setAbsent(
                            employeeAttendances.stream()
                                    .filter(attendance ->
                                            attendance.getStatus()
                                                    == AttendanceStatus.Absent)
                                    .count());

                    return dto;
                })
                .toList();
    }

    @Override
    public DashboardEmployeeResponseDto getEmployeeDashboard(
            String username) {

        Users user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        List<Attendance> attendances =
                attendanceRepository.findByEmployee(employee);

        long presentCount = attendances.stream()
                .filter(att ->
                        att.getStatus() == AttendanceStatus.Present)
                .count();

        long lateCount = attendances.stream()
                .filter(att ->
                        att.getStatus() == AttendanceStatus.Late)
                .count();

        DashboardEmployeeResponseDto dto = new DashboardEmployeeResponseDto();

        dto.setEmployeeName(employee.getFullName());
        dto.setTotalPresent(presentCount);
        dto.setTotalLate(lateCount);
        dto.setTotalAttendance(attendances.size());

        return dto;
    }
    @Override
    public List<LateSummaryResponseDto> getLateSummary() {

        List<Employee> employees =
                employeeRepository.findAll();

        return employees.stream()
                .map(employee -> {

                    List<Attendance> attendances =
                            attendanceRepository.findByEmployee(employee);

                    long lateCount =
                            attendances.stream()
                                    .filter(att ->
                                            att.getStatus() == AttendanceStatus.Late)
                                    .count();

                    LateSummaryResponseDto dto = new LateSummaryResponseDto();

                    dto.setEmployeeCode(
                            employee.getEmployeeCode());

                    dto.setFullName(
                            employee.getFullName());

                    dto.setLateCount(
                            lateCount);

                    return dto;
                })
                .toList();
    }
}