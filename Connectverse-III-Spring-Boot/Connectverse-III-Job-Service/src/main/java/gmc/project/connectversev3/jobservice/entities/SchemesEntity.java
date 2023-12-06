package gmc.project.connectversev3.jobservice.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import gmc.project.connectversev3.jobservice.models.WorkType;
import lombok.Data;

@Table(name = "schemes")
@Entity
@Data
public class SchemesEntity implements Serializable {
	
	private static final long serialVersionUID = 3534070445058569859L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tittle")
	private String tittle;
	
	@Column(name = "description")
	private String description;
	
	@Enumerated(value = EnumType.STRING)
	private WorkType workType;
	
	@ManyToMany(mappedBy = "appliedSchemes")
	private Set<EmployeeEntity> appliedEmployees = new HashSet<>();

	@ManyToMany(mappedBy = "benifittedSchemes")
	private Set<EmployeeEntity> benifittedEmployees = new HashSet<>();
	
	@Column(name = "introduced_in")
	private LocalDate introducedIn;

}
