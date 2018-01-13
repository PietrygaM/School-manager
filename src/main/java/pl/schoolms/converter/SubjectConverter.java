package pl.schoolms.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.schoolms.entity.Subject;
import pl.schoolms.repository.SubjectRepository;

public class SubjectConverter implements Converter<String, Subject> {
	@Autowired
	private SubjectRepository subjectRepo;
	
	public Subject convert(String source) {
		return subjectRepo.findById(Long.parseLong(source));
	}
}