package pl.schoolms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.schoolms.bean.LoginData;
import pl.schoolms.bean.SessionManager;
import pl.schoolms.entity.User;
import pl.schoolms.repository.UserRepository;

@Controller
public class HomeController {

	@Autowired
	UserRepository userRepo;

	@GetMapping({ "", "home" })
	public String home() {
		return "home/home";
	}

	@GetMapping({ "information" })
	public String information() {
		return "home/information";
	}

	@GetMapping({ "error" })
	public String error() {
		return "home/errorpage";
	}

	@GetMapping("user/login")
	public String login(Model m) {
		m.addAttribute("loginData", new LoginData());
		return "user/login";
	}

	@PostMapping("user/login")
	public String loginPost(@ModelAttribute LoginData loginData, Model m, RedirectAttributes ra) {
		User u = this.userRepo.findOneByEmail(loginData.getEmail());
		if (u != null && u.isPasswordCorrect(loginData.getPassword())) {
			HttpSession s = SessionManager.session();
			if (u.getRole().equals("ROLE_TEACHER")) {
				s.setAttribute("user_role", "ROLE_TEACHER");
			} else if (u.getRole().equals("ROLE_STUDENT")) {
				s.setAttribute("user_role", "ROLE_STUDENT");
			} else {
				s.setAttribute("user_role", "UNKNOWN");
			}
			s.setAttribute("user", u);
			ra.addFlashAttribute("msg", "Jesteś zalogowany");
			return "redirect:/home";
		}
		m.addAttribute("msg", "Wprowadz poprawne dane");
		return "user/login";
	}

	@GetMapping("user/logout")
	public String logout(Model m) {
		m.addAttribute("loginData", new LoginData());
		HttpSession s = SessionManager.session();
		s.invalidate();
		return "redirect:/home";
	}

	// TODO Delete User entity and change database (Student.class and Teacher.class
	// used in login), first login using "admin account" with other privileges

}
