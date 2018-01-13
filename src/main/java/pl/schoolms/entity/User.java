package pl.schoolms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "user")
public class User {

	// TODO Delete User entity and change database (Student.class and Teacher.class
	// used in login), first login using "admin account" with other privileges
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private long id;
	@NotEmpty
	private String password;
	@NotEmpty
	@Email
	@Column(unique = true)
	private String email;
	@NotEmpty
	private String role;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id")
	private Student student;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	public User() {
		super();
	}

	public User(String password, String email, String role) {
		super();
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public User(String role) {
		super();
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public boolean isPasswordCorrect(String pwd) {
		return BCrypt.checkpw(pwd, this.password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}