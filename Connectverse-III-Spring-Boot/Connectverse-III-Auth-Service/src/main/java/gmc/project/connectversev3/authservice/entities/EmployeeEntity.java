package gmc.project.connectversev3.authservice.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import gmc.project.connectversev3.authservice.models.Gender;
import gmc.project.connectversev3.authservice.models.State;
import gmc.project.connectversev3.authservice.models.WorkType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@Getter
//@Setter
@Entity
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // important
@EqualsAndHashCode(exclude = {"requestedProjects", "projects", "adminOfProject", "job", "jobsApplied"})
@Table(name = "employees")
public class EmployeeEntity implements Serializable {
	
	private static final long serialVersionUID = -2116205916321723098L;
	
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
	
	@Enumerated(value = EnumType.STRING)
	private WorkType prefferedWorkType;
	
	@Lob
	@Column(name = "cv_url")
	private String cvUrl;
	
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
	
	@Enumerated(value = EnumType.STRING)
	private State state;
	
	@Column(name = "ready_to_relocate")
	private Boolean readyToRelocate;
	
	@Column(name = "has_driving_license")
	private Boolean hasDrivingLicence;
	
	@Column(name = "has_vehicle")
	private Boolean hasVehicle;
	
	@Column(name = "expected_wage_per_hour")
	private Integer expectedWagePerHour;
	
	@Column(name = "expected_working_hour_per_week")
	private Integer expectedWorkingHoursPerWeek;
	
	@Column(name = "is_technical_worler")
	private Boolean isTechnicalWorker = false;
		
	@Column(name = "is_occupied")
	private Boolean isOccupied = false;
	
	@Column(name = "is_blocked")
	private Boolean isBlocked = true;
	
	@Column(name = "physical_health_points")
	private Integer physicalHealthPoints;
	
	@Column(name = "mental_health_points")
	private Integer mentalHealthPoints;
	
	@Column(name = "waiting_for_job_time")
	private Integer waitingForJobTime;
	
	@Column(name = "inactive_time")
	private Integer inactiveJobSeekTime;
	
	@Column(name = "job_reports")
	private Integer jobReports;
	
	@Column(name = "report_reports")
	private Integer reportPoints;
	
	@Column(name = "can_operate_mobile")
	private Boolean knowsToOperateMobile;
	
	@Column(name = "can_read_and_write")
	private Boolean knowsToReadAndWrite;
	
	@Column(name = "credit_points")
	private Integer creditPoints;
	
	@ManyToOne(optional = true)
	private HamletEntity hamlet;
	
	@OneToOne(mappedBy = "projectAdmin", cascade = CascadeType.PERSIST, targetEntity = ProjectEntity.class)
	private ProjectEntity adminOfProject;
	
	@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
	@JoinTable(name = "employees_projects_requests", 
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "project_id")
	)
	private Set<ProjectEntity> requestedProjects;
	
	@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
	@JoinTable(name = "employees_projects", 
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "project_id")
	)	
	private Set<ProjectEntity> projects;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private Set<JobEntity> jobsApplied;
	
	@ManyToOne
	private JobEntity job;
	
	public EmployeeEntity() {
		super();
		this.requestedProjects = new HashSet<>();
		this.projects = new HashSet<>();
		this.jobsApplied = new HashSet<>();
	}
	
}
