package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SchemeModel implements Serializable {

	private static final long serialVersionUID = -3102883583695432482L;

	private Long id;
	
	private String tittle;
	
	private String description;
	
	private WorkType workType;
	
	private List<ListEmployeeModel> appliedEmployees = new ArrayList<>();

	private List<ListEmployeeModel> benifittedEmployees = new ArrayList<>();
	
	private LocalDate introducedIn;
	
}
