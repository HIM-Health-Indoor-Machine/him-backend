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

    @Test
    @DisplayName("오늘의 출석 칼럼의 출석 상태를 true로 수정한다.")
    void setAttendanceStatus() {
        long userId = 1L;
        LocalDate attendDate = LocalDate.now();
        when(attendanceDao.updateAttendanceStatus(userId, attendDate)).thenReturn(1);

        attendanceService.setAttendanceStatus(userId, attendDate);

        verify(attendanceDao, times(1)).updateAttendanceStatus(userId, attendDate);
    }

    @Test
    @DisplayName("출석 상태 업데이트 실패 시, 예외가 발생한다.")
    void setAttendanceStatus_UserNotFound() {
        long userId = 1L;
        LocalDate attendDate = LocalDate.now();
        when(attendanceDao.updateAttendanceStatus(userId, attendDate)).thenReturn(0);

        assertThrows(NoSuchElementException.class, () ->
                attendanceService.setAttendanceStatus(userId, attendDate)
        );
    }

    @Test
    @DisplayName("해당 월의 출석 리스트를 조회한다.")
    void getMonthlyAttendance() {
        long userId = 1L;
        int year = 2024;
        int month = 10;
        YearMonth yearMonth = YearMonth.of(year, month);
        List<Attendance> expectedAttendances = Arrays.asList(
                new Attendance(1L, yearMonth.atDay(1), true, userId),
                new Attendance(2L, yearMonth.atDay(2), false, userId)
        );

        when(attendanceDao.selectAttendanceByUserIdAndDateRange(userId, yearMonth.atDay(1), yearMonth.atEndOfMonth()))
                .thenReturn(expectedAttendances);

        List<Attendance> actualAttendances = attendanceService.getMonthlyAttendance(userId, year, month);

        assertEquals(expectedAttendances, actualAttendances);
    }

    @Test
    @DisplayName("출석 기록이 없는 경우, 예외가 발생한다.")
    void getMonthlyAttendance_NoAttendanceRecords() {
        long userId = 1L;
        int year = 2024;
        int month = 10;
        YearMonth yearMonth = YearMonth.of(year, month);

        when(attendanceDao.selectAttendanceByUserIdAndDateRange(userId, yearMonth.atDay(1), yearMonth.atEndOfMonth()))
                .thenReturn(Collections.emptyList());

        assertThrows(NoSuchElementException.class, () ->
                attendanceService.getMonthlyAttendance(userId, year, month)
        );
    }
}