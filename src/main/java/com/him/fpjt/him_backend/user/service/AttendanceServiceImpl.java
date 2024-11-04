package com.him.fpjt.him_backend.user.service;

import com.him.fpjt.him_backend.common.constants.ExpPoints;
import com.him.fpjt.him_backend.user.dao.AttendanceDao;
import com.him.fpjt.him_backend.user.domain.Attendance;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceServiceImpl implements AttendenceService{
    private AttendanceDao attendanceDao;
    private UserService userService;

    public AttendanceServiceImpl(AttendanceDao attendanceDao, UserService userService) {
        this.attendanceDao = attendanceDao;
        this.userService = userService;
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void generateDailyAttendance() {
        List<Long> userIds = userService.getAllUserIds();
        for (Long userId : userIds) {
            attendanceDao.insertAttendance(new Attendance(userId));
        }
    }

    @Override
    @Transactional
    public void addAttendanceExp(long userId) throws Exception {
        userService.modifyUserExp(userId, ExpPoints.DAILY_ATTENDANCE_EXP);
    }

    @Override
    @Transactional
    public void setAttendanceStatus(long userId, LocalDate attendDt) {
        int updatedRow = attendanceDao.updateAttendanceStatus(userId, attendDt);
        if (updatedRow == 0) {
            throw new NoSuchElementException("존재히지 않는 회원입니다.");
        }
    }
}
