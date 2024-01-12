package com.college.students.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

	Optional<Student> findFirstByAdhaarNumOrEmail(Long adhaarNum, String email);

	@Query(nativeQuery = true, value = "SELECT * FROM Student s WHERE ( s.adhaar_num=:adhaarNum OR s.email=:email ) AND id <> :id LIMIT 1")
	Optional<Student> findFirstByAdhaarNumOrEmailAndIdNot(@Param(value = "adhaarNum") Long adhaarNum,
			@Param(value = "email") String email, @Param(value = "id") Long studentId);

	Optional<Student> findFirstByAdhaarNumAndIdNot(Long adhaarNum, Long studentId);

	Optional<Student> findFirstByEmailAndIdNot(String email, Long studentId);

}
