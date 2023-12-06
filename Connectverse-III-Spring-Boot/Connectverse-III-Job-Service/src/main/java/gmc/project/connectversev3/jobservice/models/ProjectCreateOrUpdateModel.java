package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProjectCreateOrUpdateModel implements Serializable {

	private static final long serialVersionUID = -1493731332515397718L;
	
	private Long id;
	
	private String tittle;
	
	private String subTittle;

	private String description;
	
	private String detailedDescription;
	
	private Integer durationInMonths;
	
	private DifficultyLevel difficultyLevel;
	
	private Integer totalMembers;
	
	private Boolean isHidden;
	
	private Boolean isCompleted;
	
	private String projectAdminId;
	
	private List<String> skillIds = new ArrayList<>();
	
	private List<String> employeeIds = new ArrayList<>();

}
