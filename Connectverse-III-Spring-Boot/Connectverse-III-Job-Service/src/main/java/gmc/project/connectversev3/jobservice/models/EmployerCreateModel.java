package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployerCreateModel implements Serializable {

	private static final long serialVersionUID = -5141819818871196659L;
	
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private Integer age;
	
	private Gender gender;
	
	private String aadharId;

	private String email;
	
	private Long mobileNumber;

	private String address;
	
	private String location;

}
