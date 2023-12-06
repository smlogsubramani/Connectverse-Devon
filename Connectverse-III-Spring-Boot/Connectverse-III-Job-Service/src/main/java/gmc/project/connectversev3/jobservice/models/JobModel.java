package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class JobModel implements Serializable {
	
	private static final long serialVersionUID = -7558039150255939758L;
	
	private Long id;
	
	private String tittle;
	
	private String description;
	
	private Integer noOfDays;
	
	private Integer workHoursPerWeek;
	
	private Integer payPerHour;
	
	private String location;
	
	private State state;
	
	private WorkType jobType;
	
	private Boolean drivingLicenceRequired;
	
	private Boolean vehicleWanted;
	
	private Integer requiredWorkers;
	
	private Boolean workStarted;
	
	private Boolean isTechnicalJob;
	
	private List<SkillModel> skills = new ArrayList<>();
	
//	private CompanyEntity company;
	
	private Boolean isBlocked;
	
	private Boolean isCompleted;
	
	private Boolean isHidden;
	
	private Boolean isHamletJob;
	
	private List<MessageModel> messages = new ArrayList<>();
	
	private List<ListEmployeeModel> employeesApplied = new ArrayList<>();
	
	private List<ListEmployeeModel> employees = new ArrayList<>();
	
	private String employerId;

}
