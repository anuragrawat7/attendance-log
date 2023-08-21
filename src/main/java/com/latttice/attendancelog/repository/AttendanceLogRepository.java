package com.latttice.attendancelog.repository;

import com.latttice.attendancelog.entity.AttendanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM attendance_log where date_time BETWEEN ?1 AND ?2")
    List<AttendanceLog> getAllUserLogs(final LocalDate startDate, final LocalDate endDate);

    @Query(nativeQuery = true, value = "SELECT * FROM attendance_log where user_id = ?1 AND date_time BETWEEN ?2 AND ?3")
    List<AttendanceLog> getUserLogsById(final String userId, final LocalDate startDate, final LocalDate endDate);
}
