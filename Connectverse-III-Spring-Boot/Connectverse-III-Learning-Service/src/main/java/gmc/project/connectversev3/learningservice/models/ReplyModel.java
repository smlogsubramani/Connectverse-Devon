package gmc.project.connectversev3.learningservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReplyModel implements Serializable {

	private static final long serialVersionUID = 3770404667021371984L;
	
	private String id;
	
	private Integer likes;
	
    private Integer reports;
    
    private String reply;
    
	private String commentedBy;
	
	private String commentId;
	
	private String skillId;

}
