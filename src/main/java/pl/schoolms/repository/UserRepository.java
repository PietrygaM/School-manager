package pl.schoolms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.schoolms.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findById(Long id);
	
	User findOneByEmail(String email);
	
	@Query(value = "SELECT student_id FROM user WHERE user.id= ?1 ", nativeQuery = true)
		Long sqlStudentId(Long id);
	
}
