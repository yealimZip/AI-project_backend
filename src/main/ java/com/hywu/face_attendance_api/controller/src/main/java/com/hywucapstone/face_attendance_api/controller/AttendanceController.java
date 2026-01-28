package com.hywucapstone.face_attendance_api.controller;

import com.hywucapstone.face_attendance_api.domain.Attendance;
import com.hywucapstone.face_attendance_api.domain.SchoolClass;
import com.hywucapstone.face_attendance_api.domain.Student;
import com.hywucapstone.face_attendance_api.repository.AttendanceRepository;
import com.hywucapstone.face_attendance_api.repository.SchoolClassRepository;
import com.hywucapstone.face_attendance_api.repository.StudentRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;


    @PostMapping
    public String checkIn(@RequestBody AttendanceRequest request) {
        Student student = studentRepository.findById(request.studentId).orElseThrow();
        SchoolClass schoolClass = schoolClassRepository.findById(request.classId).orElseThrow();


        boolean alreadyCheckedIn = attendanceRepository.findAll().stream()
                .anyMatch(a -> a.getStudent().getStudentId().equals(request.studentId) &&
                               a.getSchoolClass().getClassId().equals(request.classId) &&
                               a.getAttendanceDate().isEqual(LocalDate.now()));

        if (alreadyCheckedIn) {
            return "이미 출석했습니다.";
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setSchoolClass(schoolClass);
        attendance.setStatus("PRESENT");
        attendance.setAttendanceDate(LocalDate.now()); 
        attendance.setCheckInTime(LocalTime.now());    

        attendanceRepository.save(attendance);
        return "출석 완료!";
    }


    @GetMapping("/today/{studentId}")
    public List<Long> getTodayAttendanceClassIds(@PathVariable Long studentId) {

        return attendanceRepository.findAll().stream()
                .filter(a -> a.getStudent().getStudentId().equals(studentId))
                .filter(a -> a.getAttendanceDate().isEqual(LocalDate.now())) 
                .map(a -> a.getSchoolClass().getClassId())
                .collect(Collectors.toList());
    }


    @Data
    static class AttendanceRequest {
        private Long studentId;
        private Long classId;
    }

    @GetMapping("/admin/class/{classId}")
    public List<Student> getAttendedStudentsByClass(@PathVariable Long classId) {
        System.out.println("관리자가 " + classId + "번 수업의 오늘 출석 명단을 조회합니다.");

        return attendanceRepository.findAll().stream()
               
                .filter(a -> a.getSchoolClass().getClassId().equals(classId))

                .filter(a -> a.getAttendanceDate().isEqual(LocalDate.now()))

                .map(a -> a.getStudent())
                .collect(Collectors.toList());
    }
}
