package com.him.fpjt.him_backend.user.dao;

import com.him.fpjt.him_backend.user.domain.Attendance;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceDao {
    int insertAttendance(Attendance attendance);
    int updateAttendanceStatus(long userId, LocalDate attendDt);
    List<Attendance> selectAttendanceByUserIdAndDateRange(long userId, LocalDate startOfMonth, LocalDate endOfMonth);
}
