package gmc.project.connectversev3.learningservice.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CourseContentModel implements Serializable {

	private static final long serialVersionUID = -7349380166488751440L;

	private String id;
	
	private String tittle;
	
	private List<String> subTittles = new ArrayList<>();
	
}
