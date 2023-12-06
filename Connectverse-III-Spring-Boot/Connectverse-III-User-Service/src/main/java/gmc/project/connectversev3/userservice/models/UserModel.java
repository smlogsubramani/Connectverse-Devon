package gmc.project.connectversev3.userservice.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gmc.project.connectversev3.userservice.entities.HamletEntity;
import lombok.Data;

@Data
public class UserModel implements Serializable {

	private static final long serialVersionUID = -36337820892021186L;
	
	private String id;

	private String firstName;
	
	private String lastName;
	
	private Integer age;
	
	private Gender gender;
	
	private WorkType prefferedWorkType;

	private String cvUrl;
	
	private String aadharId;

	private String email;
	
	private Integer batchNo;
	
	private Long mobileNumber;
		
	private String address;
	
	private String location;
	
	private State state;
	
	private Boolean readyToRelocate;
	
	private Boolean hasDrivingLicence;
	
	private Boolean hasVehicle;
	
	private Integer expectedWagePerHour;
	
	private Integer expectedWorkingHoursPerWeek;
	
	private Boolean isTechnicalWorker;
		
	private Boolean isOccupied;
	
	private Boolean isBlocked;
	
	private Integer physicalHealthPoints;
	
	private Integer mentalHealthPoints;
	
	private Integer waitingForJobTime;
	
	private Integer inactiveJobSeekTime;
	
	private Integer jobReports;
	
	private Boolean knowsToOperateMobile;
	
	private Boolean knowsToReadAndWrite;
	
	private Boolean isEmployer;
	
	private Integer creditPoints;
	
	private HamletEntity hamlet;
	
	private ProjectModel adminOfProject;

	private List<ProjectModel> projects;
	
	private List<JobModel> jobsApplied;
	
	private JobModel job;
	
	public UserModel() {
		super();
		this.projects = new ArrayList<>();
		this.jobsApplied = new ArrayList<>();
	}
	
	private CompanyModel company;

}
