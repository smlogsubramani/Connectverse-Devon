package gmc.project.connectversev3.learningservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class ListEmployeeModel implements Serializable {

	private static final long serialVersionUID = 1826044080248062397L;
	
	private String id;
	
	private String firstName;

}
