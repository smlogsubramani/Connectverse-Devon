package gmc.project.connectversev3.authservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployerModel implements Serializable {

	private static final long serialVersionUID = -3825351129068564122L;
	
	private String firstName;
	
	private String lastName;
	
	private Integer age = 18;
	
	private Gender gender;
	
	private String aadharId;

	private String email;
	
	private Long mobileNumber;
		
	private String address;
	
	private String location;
	
	private Boolean isContractor;

}
