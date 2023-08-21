package com.latttice.attendancelog.service;

import com.latttice.attendancelog.entity.AttendanceLog;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceLogService {

    ResponseEntity<?> saveLog() throws Exception;

    ResponseEntity<List<AttendanceLog>> getAllUserLogList(final LocalDate startDate, final LocalDate endDate);
    ResponseEntity<List<AttendanceLog>> getUserLogByUserId(final String userId, final LocalDate startDate, final LocalDate endDate);

    ResponseEntity<?> addAllUsers() throws Exception;

}
