package com.hywucapstone.face_attendance_api.controller;

import com.hywucapstone.face_attendance_api.domain.Student;
import com.hywucapstone.face_attendance_api.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students") // 접속 주소: localhost:8080/api/students
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;


    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    public Student registerStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
}
