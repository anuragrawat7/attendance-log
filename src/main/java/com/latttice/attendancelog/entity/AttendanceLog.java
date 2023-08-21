package com.latttice.attendancelog.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = StringConstants.ATTENDANCE_LOG_TABLE)
@Data
public class AttendanceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = StringConstants.TRANSACTION_ID, unique = true)
    private int transactionId;

    @Column(name = StringConstants.USER_ID)
    private String userId;

    @Column(name = StringConstants.DATE_TIME)
    private LocalDateTime dateTime;
}
