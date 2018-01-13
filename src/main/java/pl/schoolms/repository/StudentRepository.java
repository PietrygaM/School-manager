package pl.schoolms.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.schoolms.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
	// --------------------------- JPA ----------------------------------
	Student findById(Long id);
		
	// --------------------------- JPQL ----------------------------------
	@Query("SELECT s FROM Student s WHERE s.division = null")
	List<Student>sqlFindAllWithoutDivision();
	
	// --------------------------- SQL ----------------------------------
	@Query(value = "SELECT * FROM student WHERE division_id = ?1", nativeQuery = true)
	List<Student>sqlFindAllInGroup(Long id);
	
		
}
