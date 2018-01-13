package pl.schoolms.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.schoolms.entity.Division;
import pl.schoolms.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
	
	Subject findById(Long id);
	
	@Query(value = "SELECT * FROM subject "
			+ "JOIN subject_division ON subject.id=subject_division.subject_id "
			+ "JOIN division ON division.id=subject_division.division_id WHERE subject_division.division_id = ?1", nativeQuery = true)
	List<Subject>sqlFindAllSubjectsInGroup(Long id);


	@Query(value = "SELECT * FROM subject WHERE subject.id NOT IN ("
		+ "SELECT Distinct(subject.id) FROM subject "
		+ "JOIN subject_division ON subject.id=subject_division.subject_id " 
		+ "JOIN division ON division.id=subject_division.division_id WHERE subject_division.division_id = ?1) ", nativeQuery = true)
	List<Subject>sqlFindAllSubjectsNotInGroup(Long id);
	
	List<Subject> findAllByDivision (Division d);
	
}	