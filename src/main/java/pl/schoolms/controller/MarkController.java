package pl.schoolms.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.schoolms.bean.SessionManager;
import pl.schoolms.entity.Mark;
import pl.schoolms.entity.Student;
import pl.schoolms.entity.Subject;
import pl.schoolms.entity.User;
import pl.schoolms.repository.MarkRepository;
import pl.schoolms.repository.StudentRepository;
import pl.schoolms.repository.SubjectRepository;


@Controller
@RequestMapping("mark")
public class MarkController {
	
	@Autowired
	StudentRepository studentRepo;

	@Autowired
	SubjectRepository subjectRepo;
	
	@Autowired
	MarkRepository markRepo;
	
	
	@GetMapping("/add")
	public String addformGet(@RequestParam Long student, @RequestParam Long subject, Model m) {
		Student Student = this.studentRepo.findById(student);
		Subject Subject = this.subjectRepo.findById(subject);
		Mark mark = new Mark(Student, Subject);
		m.addAttribute("mark", mark);
		return "mark/addMark";
	}

	@PostMapping("/add")
	@Transactional
	public String addformPost(@Valid @ModelAttribute Mark mark, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "mark/addMark";
		}
		HttpSession s = SessionManager.session();
		User user = (User) s.getAttribute("user");
		String description = mark.getDescription();
		description = description + " Added By "+ user.getEmail() + " " + new Date();
		mark.setDescription(description);
		this.markRepo.save(mark);
		Long divisionId = mark.getStudent().getDivision().getId();
		Long subjectId = mark.getSubject().getId();
		return "redirect:/division/newscast?divisionId="+divisionId+"&subjectId="+subjectId;
	}	

}
	
	

