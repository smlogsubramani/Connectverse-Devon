package gmc.project.connectversev3.userservice.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "companies")
@EqualsAndHashCode(exclude = {"jobs"})
public class CompanyEntity implements Serializable {

	private static final long serialVersionUID = 4340008808212377674L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String name;
	
	private String email;
	
	@Lob
	private String imageUrl;
	
	private Integer noOfEmployees;
	
	@Lob
	private String description;
	
	private String ownedBy;
	
	private String passcode;
	
	private Boolean isEnabled;
	
	@Column(name = "gst_number", unique = true, nullable = false)
	private String gstNumber;
	
	@OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
	private EmployerEntity employer;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private Set<JobEntity> jobs = new HashSet<>();

}
