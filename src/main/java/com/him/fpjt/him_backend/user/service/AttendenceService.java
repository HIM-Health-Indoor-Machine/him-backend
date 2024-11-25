package com.him.fpjt.him_backend.user.service;

import com.him.fpjt.him_backend.user.domain.Attendance;
import java.time.LocalDate;
import java.util.List;

public interface AttendenceService {
    void generateDailyAttendance();
    void addAttendanceExp(long userId);
    void setAttendanceStatus(long userId, LocalDate attendDt);
    List<Attendance> getMonthlyAttendance(long userId, int year, int month);
}
