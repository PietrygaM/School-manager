package pl.schoolms.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.schoolms.entity.Teacher;
import pl.schoolms.repository.TeacherRepository;

public class TeacherConverter implements Converter<String, Teacher> {
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Override
	public Teacher convert(String source) {
		return teacherRepo.findById(Long.parseLong(source));
	}
}