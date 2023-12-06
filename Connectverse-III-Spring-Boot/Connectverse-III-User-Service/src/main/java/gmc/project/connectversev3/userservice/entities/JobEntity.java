package gmc.project.connectversev3.userservice.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import gmc.project.connectversev3.userservice.models.State;
import gmc.project.connectversev3.userservice.models.WorkType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "jobs")
@EqualsAndHashCode(exclude = {"company", "employees"})
public class JobEntity implements Serializable {
	
	private static final long serialVersionUID = -8048295257824581897L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tittle")
	private String tittle;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "no_of_days")
	private Integer noOfDays = 10;
	
	@Column(name = "working_hours_per_week")
	private Integer workHoursPerWeek = 8;
	
	@Column(name = "pay_per_hour")
	private Integer payPerHour = 100;
	
	@Column(name = "location")
	private String location;
	
	@Enumerated(value = EnumType.STRING)
	private State state;
	
	@Enumerated(value = EnumType.STRING)
	private WorkType jobType;
	
	@Column(name = "driving_licence_required")
	private Boolean drivingLicenceRequired;
	
	@Column(name = "vechicle_wanted")
	private Boolean vehicleWanted;
	
	@Column(name = "required_workers")
	private Integer requiredWorkers = 10;
	
	@Column(name = "work_started")
	private Boolean workStarted = false;
	
	@Column(name = "is_technical_job")
	private Boolean isTechnicalJob = false;
	
	@ManyToMany(mappedBy = "jobs")
	private Set<SkillEntity> skills = new HashSet<>();
	
	@ManyToOne(cascade = CascadeType.ALL)
	private CompanyEntity company;
	
	@Column(name = "is_blocked")
	private Boolean isBlocked = false;
	
	@Column(name = "is_completed")
	private Boolean isCompleted = false;
	
	@Column(name = "is_hidden")
	private Boolean isHidden = false;
	
	@Column(name = "isHamletJob")
	private Boolean isHamletJob = false;
	
	@OneToOne(mappedBy = "job", optional = true)
	private HamletEntity hamlet;
	
	@OneToMany(mappedBy = "job")
	private Set<MessageEntity> messages = new HashSet<>();
	
	@ManyToMany(mappedBy = "jobsApplied", cascade = CascadeType.PERSIST)
	private Set<EmployeeEntity> employeesApplied = new HashSet<>();
	
	@OneToMany(mappedBy = "job", cascade = CascadeType.PERSIST)
	private Set<EmployeeEntity> employees = new HashSet<>();

}
