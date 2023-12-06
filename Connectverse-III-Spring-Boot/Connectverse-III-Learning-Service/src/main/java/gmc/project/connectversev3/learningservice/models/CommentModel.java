package gmc.project.connectversev3.learningservice.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CommentModel implements Serializable {

	private static final long serialVersionUID = -792418940678757633L;
	
	private String id;
	
	private Integer likes;
	
	private Integer reports;
	
	private String comment;
	
	private String commentedBy;
	
	private List<ReplyModel> replies = new ArrayList<>();
	
	private String skillId;

}
