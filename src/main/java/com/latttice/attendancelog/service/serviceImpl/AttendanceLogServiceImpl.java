package com.latttice.attendancelog.service.serviceImpl;

import com.latttice.attendancelog.entity.AttendanceLog;
import com.latttice.attendancelog.entity.User;
import com.latttice.attendancelog.repository.AttendanceLogRepository;
import com.latttice.attendancelog.repository.UserRepository;
import com.latttice.attendancelog.service.AttendanceLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AttendanceLogServiceImpl implements AttendanceLogService {

    private final AttendanceLogRepository attendanceLogRepository;
    private final UserRepository userRepository;

    /*
        This method is used for RawDataScript - where we get data from raw punching module
    */
    @Override
    @Scheduled(cron = "0 23 * ? * 1-5")
    public ResponseEntity<?> saveLog() throws Exception {

        FileInputStream excelFile = new FileInputStream("C:\\AttendanceLogger\\DownloadedExcel\\raw_punching_data.xlsx");
        XSSFWorkbook workBook = null;
        try {
            workBook = new XSSFWorkbook(excelFile);
        } catch (IOException e) {
            log.error("Unable to create workBook from provided file: {}", e);
            throw new Exception();
        }

        XSSFSheet workSheet = workBook.getSheetAt(0);

        log.info("Total rows : " + workSheet.getLastRowNum());
        List<AttendanceLog> attendanceLogList = new ArrayList<>();

        for (int i = 1; i <= workSheet.getLastRowNum(); i++) {

            var currentRow = workSheet.getRow(i);
            String empCode = String.valueOf(currentRow.getCell(0));
            String dateTime = String.valueOf(currentRow.getCell(1));

            log.info("Emp Code : " + empCode);
            var user = userRepository.findByEmpCode(empCode).orElseThrow(() -> new Exception("No user found"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

            log.info("-Code- " + empCode);
            log.info("-DateTime-" + localDateTime);

            AttendanceLog attendanceLog = new AttendanceLog();
            attendanceLog.setUserId(user.getUserId());
            attendanceLog.setDateTime(localDateTime);

            attendanceLogList.add(attendanceLog);

            }
        attendanceLogRepository.saveAll(attendanceLogList);
        return ResponseEntity.ok().body("All data stored in the system");
    }



    /*
    This method is used for LoggerScript - where we get data from Daily performance report

    @Override
    @Scheduled(cron = "0 23 * ? * 1-5")
    public ResponseEntity<?> saveLog() throws Exception {

        FileInputStream excelFile = new FileInputStream("C:\\AttendanceLogger\\DownloadedExcel\\daily_performance_report.xlsx");
        XSSFWorkbook workBook = null;
        try {
            workBook = new XSSFWorkbook(excelFile);
        } catch (IOException e) {
            log.error("Unable to create workBook from provided file: {}", e);
            throw new Exception();
        }

        XSSFSheet workSheet = workBook.getSheetAt(0);
        final String dateString = String.valueOf(workSheet.getRow(0).getCell(6));

        log.info("Total rows : " + workSheet.getLastRowNum());

        String id = "";
        for (int i = 4; i <= workSheet.getLastRowNum()-2; i++) {

            var currentRow = workSheet.getRow(i);
            String empCode = String.valueOf(currentRow.getCell(1));

            if (!empCode.equals(id) && empCode.length()<5){
                id = empCode;
            } else
                continue;

            String inTime = String.valueOf(currentRow.getCell(4));
            String outTime = String.valueOf(currentRow.getCell(7));
            if (inTime.equals("--:--")) {
               continue;
            }else{
                System.out.println("-Code- " + empCode);
                var user = userRepository.findByEmpCode(empCode).orElseThrow(() -> new Exception("No user found"));

                // Parse the date string into a LocalDate object
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                // Parse the time string into a LocalTime object
                LocalTime localTime = LocalTime.parse(inTime, DateTimeFormatter.ofPattern("HH:mm"));

                // Combine the date and time into a LocalDateTime object
                LocalDateTime dateTime = LocalDateTime.of(date, localTime);

                AttendanceLog attendanceLog = new AttendanceLog();
                attendanceLog.setUserId(user.getUserId());
                attendanceLog.setDateTime(dateTime);
                attendanceLogRepository.save(attendanceLog);

                if (!outTime.equals("--:--")){

                    // Parse the time string into a LocalTime object
                    LocalTime localTime2 = LocalTime.parse(outTime, DateTimeFormatter.ofPattern("HH:mm"));
                    LocalDateTime dateTime2 = LocalDateTime.of(date, localTime2);

                    AttendanceLog attendanceLog2 = new AttendanceLog();
                    attendanceLog2.setUserId(user.getUserId());
                    attendanceLog2.setDateTime(dateTime2);
                    attendanceLogRepository.save(attendanceLog2);
                }
                System.out.println("-inTime- " + inTime + " -outTime-" + outTime);
            }
        }
        return ResponseEntity.ok().body("Success");
    }*/

    /*
    * This method return all records of the users in the system with particular date range if any,
    * By default, date range will be start date - 1999-12-25 and end date - 9999-12-25
    * */
    @Override
    public ResponseEntity<List<AttendanceLog>> getAllUserLogList(LocalDate startDate, LocalDate endDate) {
        List<AttendanceLog> attendanceLogList = attendanceLogRepository.getAllUserLogs(startDate, endDate);
        return ResponseEntity.ok().body(attendanceLogList);
    }

    /*
     * This method return all records of the specific user by their provided user id(feeta user id) in the system
     * with particular date range if any,
     * By default, date range will be start date - 1999-12-25 and end date - 9999-12-25
     * */
    @Override
    public ResponseEntity<List<AttendanceLog>> getUserLogByUserId(String userId, LocalDate startDate, LocalDate endDate) {
        List<AttendanceLog> attendanceLogList = attendanceLogRepository.getUserLogsById(userId, startDate, endDate);
        return ResponseEntity.ok().body(attendanceLogList);
    }

    /*
    * This method record all the users of the lattice in the system.
    * User_master_data excel have all the emp_id, emp_code and Name of employee.
    * deleteAll() method is commented can be use in future.
    * */
    @Override
    public ResponseEntity<?> addAllUsers() throws Exception {
        final var excelFile = new FileInputStream("C:\\AttendanceLogger\\DownloadedExcel\\user_master_data.xlsx");
        XSSFWorkbook workBook = null;
        List<User> userList = new ArrayList<>();
        try {
            workBook = new XSSFWorkbook(excelFile);
        } catch (IOException e) {
            log.error("Unable to create workBook from provided file: {}", e);
            throw new Exception();
        }

        //Deleting the previous data of all employees
        //userRepository.deleteAll();

        final var workSheet = workBook.getSheetAt(0);
        for (int i = 1; i <= workSheet.getLastRowNum(); i++) {

            final var currentRow = workSheet.getRow(i);
            String empId = String.valueOf(currentRow.getCell(0));
            String empCode = String.valueOf(currentRow.getCell(1));
            User user = new User();
            user.setUserId(empId);
            user.setEmpCode(empCode);
            userList.add(user);
        }

        //Saves all the records in the system
        userRepository.saveAll(userList);

        return ResponseEntity.ok().body("All users record stored in the system");
    }
}

/*
name: Backend build deploy

on:
  push:
    branches:
      - cd-cd-test

jobs:
  build:
    name: Build
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v2

      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '17'

      - name: Build with Maven
        run: mvn clean install

  deploy:
    name: Deploy to Server
    runs-on: ubuntu-latest
    needs: build

    steps:
      - name: Checkout code
        uses: actions/checkout@v2

      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '17'

      - name: Build and Package
        run: mvn clean package

      - name: Deploy to Server
        # Add your deployment commands here
        with:
          host: ${{ secrets.HOST }}
          username: ${{ secrets.USERNAME }}
          password: ${{ secrets.PASSWORD }}



* */