package com.him.fpjt.him_backend.user.controller;

import com.him.fpjt.him_backend.user.domain.Attendance;
import com.him.fpjt.him_backend.user.service.AttendenceService;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private AttendenceService attendenceService;

    public AttendanceController(AttendenceService attendenceService) {
        this.attendenceService = attendenceService;
    }

    @GetMapping("/check/{userId}")
    public ResponseEntity<String> setAttendanceStatus(@PathVariable("userId") long userId){
        try {
            attendenceService.setAttendanceStatus(userId, LocalDate.now());
            attendenceService.addAttendanceExp(userId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("{userId}")
    public ResponseEntity<?> getMonthlyAttendance(@PathVariable("userId") long userId,
                                                @RequestParam("year") int year,
                                                @RequestParam("month") int month) {
        try {
            List<Attendance> attendances = attendenceService.getMonthlyAttendance(userId, year, month);
            return ResponseEntity.ok().body(attendances);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}