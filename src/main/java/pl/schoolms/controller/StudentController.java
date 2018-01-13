package pl.schoolms.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.schoolms.entity.Student;
import pl.schoolms.entity.User;
import pl.schoolms.repository.StudentRepository;
import pl.schoolms.repository.TeacherRepository;
import pl.schoolms.repository.UserRepository;

@Controller
@RequestMapping("student")
public class StudentController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	StudentRepository studentRepo;

	@Autowired
	TeacherRepository teacherRepo;

	@GetMapping("/all")
	public String all(Model m) {
		return "student/list";
	}

	@GetMapping("/add")
	public String addformGet(Model m) {
		m.addAttribute("student", new Student());
		return "student/addStudent";
	}

	@PostMapping("/add")
	@Transactional
	public String addformPost(@Valid @ModelAttribute Student student, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "student/addStudent";
		}
		User u = new User(student.getPassword(), student.getEmail(), "ROLE_STUDENT");
		this.userRepo.save(u); // save in DB
		u.setStudent(student); // set connection between student and user
		this.studentRepo.save(student); // save in DB
		this.userRepo.save(u); // save in DB
		return "redirect:/student/add";
	}

	@GetMapping("/{id}/edit")
	public String editGet(@PathVariable long id, Model m) {
		Student student = this.studentRepo.findById(id);
		m.addAttribute("student", student);
		return "student/addStudent";
	}

	@PostMapping("/{id}/edit")
	public String editPost(@ModelAttribute Student student) {
		this.studentRepo.save(student);
		return "redirect:/student/all";
	}

	@GetMapping("/{id}/delete")
	public String deleteGet(@PathVariable long id, Model m) {
		Student student = this.studentRepo.findById(id);
		m.addAttribute("student", student);
		m.addAttribute("del", id);
		return "student/list";
	}

	@PostMapping("/{id}/delete")
	public String deletePost(@PathVariable long id) {
		try {
			this.studentRepo.delete(id);
		} catch (ConstraintViolationException e) {
			return "redirect:/error";
		} catch (PersistenceException e) {
			return "redirect:/error";
		} catch (JpaSystemException e) {
			return "redirect:/error";
		}
		return "redirect:/student/all";
	}

	// -------------------------------------------------- MODEL ATTRIBUTE --------------------------------------------

	@ModelAttribute("allStudents")
	public List<Student> allStudents() {
		return studentRepo.findAll();
	}

}
