package com.hywucapstone.face_attendance_api.repository;

import com.hywucapstone.face_attendance_api.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {
}
