package pl.schoolms.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import pl.schoolms.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	
	Teacher findById(Long id);	
	
}
