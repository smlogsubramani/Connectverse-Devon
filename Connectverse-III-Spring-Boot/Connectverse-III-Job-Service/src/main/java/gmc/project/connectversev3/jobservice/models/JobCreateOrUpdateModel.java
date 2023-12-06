package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class JobCreateOrUpdateModel implements Serializable {
	
	private static final long serialVersionUID = -6176132699901165434L;
	
	private Long id;
	
	private String tittle;
	
	private String description;
	
	private Integer noOfDays;
	
	private Integer workHoursPerDay;
	
	private Integer payPerHour;
	
	private String address;
	
	private String location;
	
	private State state;
	
	private WorkType jobType;
	
	private Boolean drivingLicenceRequired;
	
	private Boolean vehicleWanted;
	
	private Integer requiredWorkers;
	
	private Boolean workStarted;
	
	private Boolean isTechnicalJob;
	
	private Boolean isHidden;
	
	private List<String> skillIds = new ArrayList<>();
	
	private String employerId;

}
