package com.hywucapstone.face_attendance_api.controller;

import com.hywucapstone.face_attendance_api.domain.SchoolClass;
import com.hywucapstone.face_attendance_api.repository.SchoolClassRepository;
import com.hywucapstone.face_attendance_api.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class SchoolClassController {

    private final SchoolClassRepository schoolClassRepository;
    private final EnrollmentRepository enrollmentRepository;


    @GetMapping
    public List<SchoolClass> getAllClasses() {
        return schoolClassRepository.findAll();
    }

   
    @PostMapping
    public SchoolClass createClass(@RequestBody SchoolClass schoolClass) {
        return schoolClassRepository.save(schoolClass);
    }

    public List<SchoolClass> getStudentClasses(@PathVariable Long studentId) {
        System.out.println("학생 ID " + studentId + "의 모든 수강 과목을 조회합니다.");
        
     
        return enrollmentRepository.findAllClassesByStudentId(studentId);
    }
}
