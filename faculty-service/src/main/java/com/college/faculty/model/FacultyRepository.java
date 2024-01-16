package com.college.faculty.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

	Optional<Faculty> findFirstByAdhaarNumOrEmail(Long adhaarNum, String email);

	@Query(nativeQuery = true, value = "SELECT * FROM Faculty s WHERE ( s.adhaar_num=:adhaarNum OR s.email=:email ) AND id <> :id LIMIT 1")
	Optional<Faculty> findFirstByAdhaarNumOrEmailAndIdNot(@Param(value = "adhaarNum") Long adhaarNum,
			@Param(value = "email") String email, @Param(value = "id") Long studentId);

	Optional<Faculty> findFirstByAdhaarNumAndIdNot(Long adhaarNum, Long studentId);

	Optional<Faculty> findFirstByEmailAndIdNot(String email, Long studentId);

}
