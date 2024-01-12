package com.college.students.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

	Optional<Student> findByAdhaarNumOrEmail(Long adhaarNum, String email);

}
