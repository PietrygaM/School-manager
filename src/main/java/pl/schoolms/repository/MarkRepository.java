package pl.schoolms.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.schoolms.entity.Mark;
import pl.schoolms.entity.Subject;

public interface MarkRepository extends JpaRepository<Mark, Long>{
	
	Mark findById(Long id);
	List <Mark> findAllBySubject (Subject s);
	
	// --------------------------- SQL ----------------------------------
	@Query(value = "SELECT * FROM mark WHERE subject_id=?1 AND student_id IN "
	+ "(SELECT id FROM student WHERE student.division_id = ?2)", nativeQuery = true)
	List<Mark>sqlAllMarksInGroup(Long subjectIdLong, Long divisionId);
	
	@Query(value = "SELECT * FROM mark WHERE student_id= ?1 ORDER BY subject_id", nativeQuery = true)
	List<Mark>sqlAllStudentMarks(Long studentId);

	@Query(value = "SELECT * FROM mark WHERE subject_id= ?1 AND student_id= ?2", nativeQuery = true)
	List<Mark>sqlAllStudentMarksFromSubject(Long subjectId, Long studentId);
		
}
