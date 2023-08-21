package com.latttice.attendancelog.controllertestcases;

import com.latttice.attendancelog.controller.AttendanceLogController;
import com.latttice.attendancelog.repository.AttendanceLogRepository;
import com.latttice.attendancelog.repository.UserRepository;
import com.latttice.attendancelog.service.AttendanceLogService;
import com.latttice.attendancelog.service.serviceImpl.AttendanceLogServiceImpl;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.ok;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class AttendancelogApplicationTests {

	@InjectMocks
	private AttendanceLogController attendanceLogController;
	@Mock
	private AttendanceLogService attendanceLogService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		attendanceLogService = mock(AttendanceLogService.class);
		attendanceLogController = new AttendanceLogController(attendanceLogService);
	}

	@AfterEach
	public void tearDown(){
		attendanceLogService = null;
		attendanceLogController = null;
	}

//	@Test
//	void contextLoads() {
//	}

}
