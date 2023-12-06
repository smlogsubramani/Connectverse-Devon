package gmc.project.connectversev3.userservice.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import gmc.project.connectversev3.userservice.models.Gender;
import lombok.Data;

@Data
@Entity
@Table(name = "employers")
public class EmployerEntity implements Serializable {

	private static final long serialVersionUID = -9127881262998178373L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id")
	private String id;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "age")
	private Integer age = 18;
	
	@Enumerated(value = EnumType.STRING)
	private Gender gender;
	
	@Column(name = "aadhar_id")
	private String aadharId;

	@Column(name = "email")
	private String email;
	
	@Column(name = "mobile_number")
	private Long mobileNumber;

	@Column(name = "address")
	private String address;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "is_blocked")
	private Boolean isBlocked = false;
	
	@Column(name = "is_contractor")
	private Boolean isContractor;
	
	@OneToOne(optional = true, cascade = CascadeType.PERSIST)
	private CompanyEntity company;

}
