package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CompanyModel implements Serializable {

	private static final long serialVersionUID = 3715327465996610440L;
	
	private String id;
	
	private String name;
	
	private String imageUrl;
	
	private String noOfEmployees;
	
	private String description;
	
	private String ownedBy;
	
	private EmployerModel employer;
	
	private List<JobModel> jobs = new ArrayList<>();

}
