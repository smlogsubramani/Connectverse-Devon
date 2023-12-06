package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreateOrUpdateCompanyModel implements Serializable {
	
	private static final long serialVersionUID = -8412708273905440781L;
	
	private String id;
	
	private String name;
	
	private String email;
	
	private String imageUrl;
	
	private Integer noOfEmployees;
	
	private String description;
	
	private String ownedBy;
	
	private String gstNumber;
	
	private String employerId;
	
	private EmployerCreateModel employerUpdated;

}
