package gmc.project.connectversev3.learningservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class ListProjectModel implements Serializable {
	
	private static final long serialVersionUID = 6394745010227075586L;
	
	private Long id;
	
	private String tittle;
	
	private String subTittle;

	private String description;
	
	private String detailedDescription;
	
	private Integer durationInMonths;
		
	private Integer totalMembers;
	
	private String skillImage;
	
	private String content;

}
