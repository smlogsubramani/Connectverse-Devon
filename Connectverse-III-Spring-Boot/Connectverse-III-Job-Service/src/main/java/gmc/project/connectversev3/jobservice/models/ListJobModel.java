package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ListJobModel implements Serializable {
	
	private static final long serialVersionUID = -7558039150255939758L;
	
	private Long id;
	
	private String tittle;
	
	private String description;
	
	private Integer noOfDays;
	
	private Integer workHoursPerDay;
	
	private Integer payPerHour;
	
	private String location;
	
	private State state;
	
	private WorkType jobType;
	
	private Integer requiredWorkers;
	
	private Boolean workStarted;
	
	private List<SkillModel> skills = new ArrayList<>();
	
	private CreateOrUpdateCompanyModel company;
	
	private String employerId;

	private Integer smsHandler;
}
