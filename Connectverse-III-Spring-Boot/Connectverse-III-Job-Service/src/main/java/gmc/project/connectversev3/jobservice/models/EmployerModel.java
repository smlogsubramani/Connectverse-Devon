package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployerModel implements Serializable {
	
	private static final long serialVersionUID = 1188709380675490934L;
	
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private Integer age;
	
	private Gender gender;
	
	private String email;
	
	private Long mobileNumber;

	private String address;
	
	private String location;
	
	private Boolean isBlocked;

}
