package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProjectModel implements Serializable {
	
	private static final long serialVersionUID = 6394745010227075586L;
	
	private Long id;
	
	private String tittle;
	
	private String subTittle;

	private String description;

	private String detailedDescription;
	
	private Integer durationInMonths;
	
	private DifficultyLevel difficultyLevel;
	
	private Integer totalMembers;
		
	private Boolean isCompleted;
	
	private LocalDate startedOn;
	
	private String projectAdminId;
	
	private String projectAdminName;

	private List<SkillModel> skills;
	
	private List<ListEmployeeModel> joiningRequests;
	
	private List<ListEmployeeModel> team;
		
	private List<MessageModel> messages;
	
	private List<NoticeModel> notices;
	
	public ProjectModel() {
		super();
		this.skills = new ArrayList<>();
		this.joiningRequests = new ArrayList<>();
		this.team = new ArrayList<>();
		this.messages = new ArrayList<>();
		this.notices = new ArrayList<>();
	}

}
