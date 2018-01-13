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
		
		// TODO Delete User entity and change database (Student.class and Teacher.class
		// used in login), first login using "admin account" with other privileges
		@GetMapping("/addadmin")
		public String addformGetAdmin(Model m) {
			Teacher teacher = new Teacher("arek", "arkadiusz.jozwiak@coderslab.pl", "Arkadiusz", "Jozwiak");
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
	
	// TEST -------------------------------------------------------------------------------------------------------- \
//	@GetMapping("/addTeacherToStudent/{tid}/{sid}")
//	@ResponseBody
//	public String addRoleToUser(@PathVariable long tid, @PathVariable long sid) {
//		Teacher t = this.teacherRepo.findById(tid);
//		Student s = this.studentRepo.findById(sid);
//		t.setStudent(s);
//		this.teacherRepo.save(t);
//		return "added";
//	}
	
//	@GetMapping("/addTeacherToStudent1") // => nie działa
//	@ResponseBody
//	public String addRoleToUser1() {
//		Teacher t = this.teacherRepo.findById(1l);
//		Student s = this.studentRepo.findById(1l);
//		//t.setStudent(s);
//		s.setTeacher(t);
//		this.studentRepo.save(s);
//		return "added";
//	}
	
//	@GetMapping("/addTeacherToStudent2")  // => Prawidłowe dodanie relacji @OneToOne; pobieramy dane i zapisujemy w child
//	@ResponseBody
//	public String addRoleToUser2() {
//		Teacher t = this.teacherRepo.findById(4l);
//		Student s = this.studentRepo.findById(4l);
//		t.setStudent(s);
//		//s.setTeacher(t);
//		this.teacherRepo.save(t);
//		return "added";
//	}
	
//	@GetMapping("{id}/listadd")
//	public String listadd(@PathVariable long id,Model m) {
//		Teacher tmpTeacher = this.teacherRepo.findById(id);
//		m.addAttribute("setTeacher", tmpTeacher);
//		return "teacher/listadd";
//	}
//	
//	@PostMapping("{id}/listadd")
//	@Transactional
//	public String listadd(@PathVariable long id, @ModelAttribute Teacher tmpTeacher) {	
//		Teacher teacher = this.teacherRepo.findById(id);
//		teacher.setStudent(tmpTeacher.getStudent());
//		this.teacherRepo.save(teacher);
//		return "redirect:/teacher/listconn";
//	}
	

	
	// TEST -------------------------------------------------------------------------------------------------------- /
	
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
	
	

