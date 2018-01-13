package pl.schoolms.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.schoolms.entity.Student;
import pl.schoolms.repository.StudentRepository;

public class StudentConverter implements Converter<String, Student> {
	@Autowired
	private StudentRepository studRepo;
	
	public Student convert(String source) {
		return studRepo.findById(Long.parseLong(source));
	}
}