package pl.schoolms.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "student")
public class Student {

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
	private String firstName;
	@NotEmpty
	private String lastName;

	@OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User user;

	@ManyToOne
	@JoinColumn(name = "division_id")
	private Division division;

	@OneToMany (mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	List <Mark> mark = new ArrayList<>();
	
	
	public Student() {
		super();
	}

	public Student(String password, String email, String firstName, String lastName) {
		super();
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(String password, String email, String firstName, String lastName, User user) {
		super();
		setPassword(password);
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.user = user;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}
	
	public List<Mark> getMark() {
		return mark;
	}

	public void setMark(List<Mark> mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", password=" + password + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", teacher=" + ", user=" + user + "]";
	}

}