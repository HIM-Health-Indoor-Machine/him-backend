package com.him.fpjt.him_backend.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.him.fpjt.him_backend.common.constants.ExpPoints;
import com.him.fpjt.him_backend.user.dao.AttendanceDao;
import com.him.fpjt.him_backend.user.domain.Attendance;
import com.him.fpjt.him_backend.user.service.AttendanceServiceImpl;
import com.him.fpjt.him_backend.user.service.UserService;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AttendanceServiceImplTest {
    @Mock
    private AttendanceDao attendanceDao;

    @Mock
    private UserService userService;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("사용자 목록을 기반으로 오늘 날짜의 출석 칼럼을 생성한다.")
    void generateDailyAttendance() {
        List<Long> userIds = Arrays.asList(1L, 2L, 3L);
        when(userService.getAllUserIds()).thenReturn(userIds);

        attendanceService.generateDailyAttendance();

        for (Long userId : userIds) {
            verify(attendanceDao, times(3)).insertAttendance(any(Attendance.class));
        }
    }

    @Test
    @DisplayName("사용자의 출석 경험치 1EXP를 추가한다.")
    void addAttendanceExp() throws Exception {
        long userId = 1L;

        attendanceService.addAttendanceExp(1L);

        verify(userService, times(1)).modifyUserExp(userId, ExpPoints.DAILY_ATTENDANCE_EXP);
    }
}