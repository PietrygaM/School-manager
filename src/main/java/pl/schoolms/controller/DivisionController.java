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
import org.springframework.web.bind.annotation.RequestParam;

import pl.schoolms.entity.Division;
import pl.schoolms.entity.Mark;
import pl.schoolms.entity.Student;
import pl.schoolms.entity.Subject;
import pl.schoolms.repository.DivisionRepository;
import pl.schoolms.repository.MarkRepository;
import pl.schoolms.repository.StudentRepository;
import pl.schoolms.repository.SubjectRepository;

@Controller
@RequestMapping("division")
public class DivisionController {

	@Autowired
	StudentRepository studentRepo;

	@Autowired
	DivisionRepository divisionRepo;

	@Autowired
	SubjectRepository subjectRepo;

	@Autowired
	MarkRepository markRepo;

	@GetMapping("/all")
	public String all(Model m) {
		return "division/list";
	}

	@GetMapping("/add")
	public String addformGet(Model m) {
		m.addAttribute("division", new Division());
		return "division/addDivision";
	}

	@PostMapping("/add")
	@Transactional
	public String addformPost(@Valid @ModelAttribute Division division, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "division/addDivision";
		}
		this.divisionRepo.save(division);
		return "redirect:/division/add";
	}

	@GetMapping("/{id}/edit")
	public String editGet(@PathVariable long id, Model m) {
		Division division = this.divisionRepo.findById(id);
		m.addAttribute("division", division);
		return "division/addDivision";
	}

	@PostMapping("/{id}/edit")
	public String editPost(@ModelAttribute Division division) {
		this.divisionRepo.save(division);
		return "redirect:/division/all";
	}

	@GetMapping("/{id}/delete")
	public String deleteGet(@PathVariable long id, Model m) {
		Division division = this.divisionRepo.findById(id);
		if (division.getStudent() != null) {
			List<Student> allStudentsInGroup = this.studentRepo.sqlFindAllInGroup(id);
			for (Student s : allStudentsInGroup) {
				s.setDivision(null);
				this.studentRepo.save(s);
			}
		}
		m.addAttribute("division", division);
		m.addAttribute("del", id);
		return "division/list";
	}

	@PostMapping("/{id}/delete")
	@Transactional
	public String deletePost(@PathVariable long id) {
		try {
			this.divisionRepo.delete(id);
		} catch (ConstraintViolationException e) {
			return "redirect:/error";
		} catch (PersistenceException e) {
			return "redirect:/error";
		} catch (JpaSystemException e) {
			return "redirect:/error";
		}
		return "redirect:/division/all";
	}

	@GetMapping("{id}/addStudentToDivision")
	public String addStudentToDivision(@PathVariable Long id, Model m) {
		List<Student> allStudentsInGroup = this.studentRepo.sqlFindAllInGroup(id);
		m.addAttribute("allStudentsInGroup", allStudentsInGroup);

		Student tmpStudent = new Student();
		Division tmpDivison = this.divisionRepo.findById(id);
		m.addAttribute("addToDivision", tmpStudent);
		m.addAttribute("divisionParams", tmpDivison);
		return "division/addStudentToDivision";
	}

	@PostMapping("{id}/addStudentToDivision")
	@Transactional
	public String addStudentToDivision(@PathVariable Long id, @ModelAttribute Student tmpStudent) {
		Student student = this.studentRepo.findById(tmpStudent.getId());
		Division division = this.divisionRepo.findById(id);
		student.setDivision(division);
		this.studentRepo.save(student);
		return "redirect:/division/all";
	}

	// ---------------------------------------------------

	@GetMapping("{id}/addSubjectToDivision")
	public String addSubjectToDivision(@PathVariable Long id, Model m) {
		List<Subject> allSubjectsInGroup = this.subjectRepo.sqlFindAllSubjectsInGroup(id);
		List<Subject> allSubjectsNotInGroup = this.subjectRepo.sqlFindAllSubjectsNotInGroup(id);

		m.addAttribute("allSubjectsInGroup", allSubjectsInGroup);
		m.addAttribute("allSubjectsNotInGroup", allSubjectsNotInGroup);

		Subject tmpSubject = new Subject();
		Division tmpDivison = this.divisionRepo.findById(id);
		m.addAttribute("addToDivision", tmpSubject);
		m.addAttribute("divisionParams", tmpDivison);
		return "division/addSubjectToDivision";
	}

	@PostMapping("{id}/addSubjectToDivision")
	@Transactional
	public String addSubjectToDivision(@PathVariable Long id, @ModelAttribute Subject tmpSubject) {
		Subject subject = this.subjectRepo.findById(tmpSubject.getId());
		Division division = this.divisionRepo.findById(id);
		subject.getDivision().add(division);
		this.subjectRepo.save(subject);
		return "redirect:/division/all";
	}

	// ---------------------------------------------------

	@GetMapping("{id}/newscast")
	public String newscast(@PathVariable Long id, Model m) {
		List<Student> allStudentsInGroup = this.studentRepo.sqlFindAllInGroup(id);
		List<Subject> allSubjectsInGroup = this.subjectRepo.sqlFindAllSubjectsInGroup(id);
		m.addAttribute("allStudentsInGroup", allStudentsInGroup);
		m.addAttribute("allSubjectsInGroup", allSubjectsInGroup);
		m.addAttribute("divisionId", id);
		return "division/newscast_main";
	}

	@GetMapping("/newscast")
	public String newscastsubject(@RequestParam Long divisionId, @RequestParam String subjectId, Model m) {
		Long subjectIdLong = Long.parseLong(subjectId);
		List<Student> allStudentsInGroup = this.studentRepo.sqlFindAllInGroup(divisionId);
		List<Mark> allMarksInGroup = this.markRepo.sqlAllMarksInGroup(subjectIdLong, divisionId);
		m.addAttribute("allStudentsInGroup", allStudentsInGroup);
		m.addAttribute("allMarksInGroup", allMarksInGroup);
		m.addAttribute("subjectIdLong", subjectIdLong);
		return "division/newscast_subject";
	}

	// -------------------------------------------------- MODEL ATTRIBUTE
	// --------------------------------------------

	@ModelAttribute("allDivisions")
	public List<Division> allDivisions() {
		return divisionRepo.findAll();
	}

	@ModelAttribute("allAvailableStudents")
	public List<Student> allAvailableStudents() {
		return studentRepo.sqlFindAllWithoutDivision();
	}

}
