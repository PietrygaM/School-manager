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
import pl.schoolms.entity.Teacher;
import pl.schoolms.entity.User;
import pl.schoolms.repository.StudentRepository;
import pl.schoolms.repository.TeacherRepository;
import pl.schoolms.repository.UserRepository;


@Controller
@RequestMapping("teacher")
public class TeacherController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TeacherRepository teacherRepo;

	@Autowired
	StudentRepository studentRepo;
	
	
	@GetMapping("/all")
	public String all(Model m) {
		return "teacher/list";
	}
	
	
	@GetMapping("/add")
	public String addformGet(Model m) {
		m.addAttribute("teacher", new Teacher());
		return "teacher/addTeacher";
	}
	
	
	// Add teacher and user
		@PostMapping("/add")
		@Transactional
		public String addformPost(@Valid @ModelAttribute Teacher teacher, 
									BindingResult bindingResult) {
			if(bindingResult.hasErrors()) {
				return "teacher/addTeacher";
			}
			User u = new User(teacher.getPassword(), teacher.getEmail(), "ROLE_TEACHER"); 
			this.userRepo.save(u);			//save in DB
			u.setTeacher(teacher);			//set connection between student and user
			this.teacherRepo.save(teacher);	//save in DB
			this.userRepo.save(u);			//save in DB
			return "redirect:/teacher/add";
		}
		
		@GetMapping("/addadmin")
		public String addformGetAdmin(Model m) {
			Teacher teacher = new Teacher("teacher1", "teacher1@mail.pl", "TeacherFirstName", "TeacherLastName");
			User u = new User(teacher.getPassword(), teacher.getEmail(), "ROLE_TEACHER"); 
			this.userRepo.save(u);			//save in DB
			u.setTeacher(teacher);			//set connection between student and user
			this.teacherRepo.save(teacher);	//save in DB
			this.userRepo.save(u);			//save in DB
			return "redirect:home";
		}
	
		
	@GetMapping("/{id}/edit")
	public String editGet(@PathVariable long id, Model m) {
		Teacher teacher = this.teacherRepo.findById(id);
		m.addAttribute("teacher", teacher);
		return "teacher/addTeacher";
	}
	
	@PostMapping("/{id}/edit")
	public String editPost(@ModelAttribute Teacher teacher) {
		this.teacherRepo.save(teacher);
		return "redirect:/teacher/all";
	}
	
	@GetMapping("/{id}/delete")
	public String deleteGet(@PathVariable long id, Model m) {
		Teacher teacher = this.teacherRepo.findById(id);
		m.addAttribute("teacher", teacher);
		m.addAttribute("del", id);
		return "teacher/list";
	}
	
	@PostMapping("/{id}/delete")
	public String deletePost(@PathVariable long id) {
		try {
			this.teacherRepo.delete(id);
		} catch (ConstraintViolationException e) {
			return "redirect:/error";
		} catch (PersistenceException e) {
			return "redirect:/error";
		} catch (JpaSystemException e) {
			return "redirect:/error";
		}
		return "redirect:/teacher/all";
	}
	
	
	// -------------------------------------------------- MODEL ATTRIBUTE --------------------------------------------

	@ModelAttribute("allTeachers")
	public List<Teacher> allTeachers() {
		return teacherRepo.findAll();
	}
		
	@ModelAttribute("allStudents")
	public List<Student> allStudents() {
		return studentRepo.findAll();
	}
	
	
}
	
	

