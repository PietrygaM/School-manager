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
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "teacher")
public class Teacher {

	//TODO add new validation and parameters
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
	
	
	@ManyToMany (mappedBy = "teacher")
	List <Subject> subject = new ArrayList<>();
	

	@OneToOne (mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User user;
	
	public Teacher() {
		super();
	}

	
	
	public Teacher(String password, String email, String firstName, String lastName) {
		super();
		setPassword(password);
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
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

	

	public List<Subject> getSubject() {
		return subject;
	}

	public void setSubject(List<Subject> subject) {
		this.subject = subject;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	@Override
	public String toString() {
		return "Teacher [id=" + id + ", password=" + password + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", student=" + "]";
	}

	
	
}