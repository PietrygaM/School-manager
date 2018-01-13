package pl.schoolms.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import pl.schoolms.entity.User;
import pl.schoolms.repository.UserRepository;

public class UserConverter implements Converter<String, User> {
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public User convert(String source) {
		return userRepo.findById(Long.parseLong(source));
	}
}