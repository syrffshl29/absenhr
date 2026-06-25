package com.miniproject.absenhr.controller.Web;


import com.miniproject.absenhr.core.dto.request.CheckInRequestDto;
import com.miniproject.absenhr.service.AttendanceServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AttendanceWebController {
    private final AttendanceServiceImpl attendanceServiceImpl;

    public AttendanceWebController(AttendanceServiceImpl attendanceServiceImpl) {
        this.attendanceServiceImpl = attendanceServiceImpl;
    }

    @GetMapping("/attendance")
    public String page() {
        return "attendance";
    }

    @PostMapping("/attendance/check-in")
    public String checkIn(
            Authentication authentication,
            @RequestBody CheckInRequestDto dto,
            RedirectAttributes redirectAttributes) {

        try {

            attendanceServiceImpl.checkIn(
                    authentication.getName(),
                    dto.getLocation());

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Check In berhasil"
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/attendance";
    }
}
