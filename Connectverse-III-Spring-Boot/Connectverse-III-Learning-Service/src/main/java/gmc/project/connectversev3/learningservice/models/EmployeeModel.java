package gmc.project.connectversev3.learningservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployeeModel implements Serializable {

	private static final long serialVersionUID = -2825193861414147978L;
	
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private Integer age;
	
	private Gender gender;
	
	private WorkType prefferedWorkType;
		
	private String email;
	
	private Long mobileNumber;

}
