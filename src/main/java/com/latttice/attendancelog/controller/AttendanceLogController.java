package com.latttice.attendancelog.controller;

import com.latttice.attendancelog.entity.AttendanceLog;
import com.latttice.attendancelog.entity.StringConstants;
import com.latttice.attendancelog.service.AttendanceLogService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = StringConstants.ATTENDANCE)
public class AttendanceLogController {

    private AttendanceLogService attendanceLogService;

    @Hidden
    @PostMapping(value = StringConstants.SAVE_LOG)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "API used to save attendance log in the database")
    public ResponseEntity<?> userListRetreivalHandler() throws Exception {
        return attendanceLogService.saveLog();
    }

    @GetMapping(value = StringConstants.ALL_USERS, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "1: Response the list of all users attendance log", description = "")
    public ResponseEntity<List<AttendanceLog>> getAllUserListRetreivalHandler(
            @Parameter(description = "YYYY-MM-DD -> 2021-12-25") @RequestParam(required = false, name = "beginDate", defaultValue = "1999-12-25") final String startDate,
            @Parameter(description = "YYYY-MM-DD -> 2021-12-25") @RequestParam(required = false, name = "endDate", defaultValue = "9999-12-25") final String endDate) {
        return attendanceLogService.getAllUserLogList(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "2: Response the list of user by user id attendance log", description = "")
    public ResponseEntity<List<AttendanceLog>> getUserListByIdRetreivalHandler(
            @PathVariable(required = true, name = "userId") final String userId,
            @Parameter(description = "YYYY-MM-DD -> 2021-12-25") @RequestParam(required = false, name = "beginDate", defaultValue = "1999-12-25") final String startDate,
            @Parameter(description = "YYYY-MM-DD -> 2021-12-25") @RequestParam(required = false, name = "endDate", defaultValue = "9999-12-25") final String endDate) {
        return attendanceLogService.getUserLogByUserId(userId, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @Hidden
    @PostMapping(value = StringConstants.ADD_USERS)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "API used to save all the users in the database")
    public ResponseEntity<?> addUserRecordsHandler() throws Exception {
        return attendanceLogService.addAllUsers();
    }

}
