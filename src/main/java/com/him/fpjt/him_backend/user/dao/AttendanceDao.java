package com.him.fpjt.him_backend.user.dao;

import com.him.fpjt.him_backend.user.domain.Attendance;
import java.time.LocalDate;

public interface AttendanceDao {
    int insertAttendance(Attendance attendance);
    int updateAttendanceStatus(long userId, LocalDate attendDt);
}
