package pl.schoolms.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import pl.schoolms.entity.Division;
import pl.schoolms.entity.Student;

public interface DivisionRepository extends JpaRepository<Division, Long>{
	
	// --------------------------- JPA ----------------------------------
	Division findById(Long id);
	Division findByStudent(Student s);
	
}
