package gmc.project.connectversev3.userservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployeeModel implements Serializable {

	private static final long serialVersionUID = 8536826878930515827L;
	
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private Integer age;
	
	private Gender gender;
	
	private WorkType prefferedWorkType;
	
	private String cvUrl;
	
	private String aadharId;

	private String email;
	
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
	
	private Integer creditPoints;

}
