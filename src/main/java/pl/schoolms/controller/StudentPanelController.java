package pl.schoolms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.schoolms.bean.SessionManager;
import pl.schoolms.entity.Division;
import pl.schoolms.entity.Mark;
import pl.schoolms.entity.Student;
import pl.schoolms.entity.Subject;
import pl.schoolms.entity.User;
import pl.schoolms.repository.DivisionRepository;
import pl.schoolms.repository.MarkRepository;
import pl.schoolms.repository.StudentRepository;
import pl.schoolms.repository.SubjectRepository;
import pl.schoolms.repository.UserRepository;

@Controller
@RequestMapping("studentpanel")
public class StudentPanelController {

	@Autowired
	StudentRepository studentRepo;

	@Autowired
	DivisionRepository divisionRepo;

	@Autowired
	SubjectRepository subjectRepo;

	@Autowired
	MarkRepository markRepo;

	@Autowired
	UserRepository userRepo;

	@GetMapping("/all")
	public String all(Model m) {
		return "studentpanel/list";
	}

	@GetMapping("/newscast")
	public String newscastsubject(Model m) {
		Student student = getStudent();
		List<Mark> allStudentMarks = this.markRepo.sqlAllStudentMarks(student.getId());
		Division tmpDivision = this.divisionRepo.findByStudent(student);
		List<Subject> allStudentSubjects = this.subjectRepo.findAllByDivision(tmpDivision);
		m.addAttribute("allStudentMarks", allStudentMarks);
		m.addAttribute("allStudentSubjects", allStudentSubjects);
		return "studentpanel/newscast";
	}

	@GetMapping("/markdetails")
	public String markdetails(@RequestParam Long subjectId, Model m) {
		Student student = getStudent();
		List<Mark> allStudentMarksFromSubject = this.markRepo.sqlAllStudentMarksFromSubject(subjectId, student.getId());
		m.addAttribute("allStudentMarksFromSubject", allStudentMarksFromSubject);
		return "studentpanel/markdetails";
	}

	public Student getStudent() {
		HttpSession s = SessionManager.session();
		User user = (User) s.getAttribute("user");
		Long userId = user.getId();
		Student student = this.studentRepo.findById(this.userRepo.sqlStudentId(userId));
		return student;
	}

}
