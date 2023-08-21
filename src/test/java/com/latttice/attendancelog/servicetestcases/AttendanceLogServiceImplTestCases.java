package com.latttice.attendancelog.servicetestcases;

import com.latttice.attendancelog.entity.AttendanceLog;
import com.latttice.attendancelog.entity.User;
import com.latttice.attendancelog.repository.AttendanceLogRepository;
import com.latttice.attendancelog.repository.UserRepository;
import com.latttice.attendancelog.service.serviceImpl.AttendanceLogServiceImpl;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AttendanceLogServiceImplTestCases {

    @InjectMocks
    private AttendanceLogServiceImpl attendanceLogServiceImpl;
    @Mock
    private AttendanceLogRepository attendanceLogRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private XSSFSheet workSheet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        attendanceLogRepository = mock(AttendanceLogRepository.class);
        userRepository = mock(UserRepository.class);
        workSheet = mock(XSSFSheet.class);
        attendanceLogServiceImpl = new AttendanceLogServiceImpl(attendanceLogRepository, userRepository);
    }

    @AfterEach
    public void tearDown(){
        attendanceLogRepository = null;
        userRepository = null;
        attendanceLogServiceImpl = null;
        workSheet = null;
    }

    @Test
    public void saveLogTestSuccess() throws Exception {
        User user = new User();
        user.setEmpCode("23");
        user.setUserId("1");
        when(userRepository.findByEmpCode(anyString())).thenReturn(Optional.of(user));
        ResponseEntity<?> actual = attendanceLogServiceImpl.saveLog();
        assertEquals("Success", actual.getBody());
    }

    @Test
    public void getAllUserLogListSuccess(){
        LocalDate startDate = LocalDate.parse("1999-12-25");
        LocalDate endDate = LocalDate.parse("9999-12-25");
        List<AttendanceLog> attendanceLogList = new ArrayList<>();
        attendanceLogList.add(mutuateAttendanceLog());
        when(attendanceLogRepository.getAllUserLogs(startDate, endDate)).thenReturn(attendanceLogList);
        ResponseEntity<List<AttendanceLog>> actual = attendanceLogServiceImpl.getAllUserLogList(startDate, endDate);
        List<AttendanceLog> expected = new ArrayList<>();
        expected.add(mutuateAttendanceLog());
        System.out.println(actual.getBody().size());
        assertEquals(expected.size(), actual.getBody().size());
    }

    @Test
    public void getUserLogByUserIdSuccess(){
        LocalDate startDate = LocalDate.parse("1999-12-25");
        LocalDate endDate = LocalDate.parse("9999-12-25");
        List<AttendanceLog> attendanceLogList = new ArrayList<>();
        attendanceLogList.add(mutuateAttendanceLog());
        when(attendanceLogRepository.getUserLogsById("5", startDate, endDate)).thenReturn(attendanceLogList);
        ResponseEntity<List<AttendanceLog>> actual = attendanceLogServiceImpl.getUserLogByUserId("5", startDate, endDate);
        List<AttendanceLog> expected = new ArrayList<>();
        expected.add(mutuateAttendanceLog());
        System.out.println(actual.getBody().size());
        assertEquals(expected.size(), actual.getBody().size());
    }

    @Test
    public void allAllUsersSuccess() throws Exception {
        ResponseEntity<?> actual = attendanceLogServiceImpl.addAllUsers();
        assertEquals("Success", actual.getBody());
    }

    public AttendanceLog mutuateAttendanceLog(){
        AttendanceLog attendanceLog = new AttendanceLog();
        attendanceLog.setUserId("1");
        return attendanceLog;
    }



}
