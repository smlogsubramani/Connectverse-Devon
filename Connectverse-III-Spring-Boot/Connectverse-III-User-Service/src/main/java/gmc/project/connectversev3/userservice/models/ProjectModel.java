package gmc.project.connectversev3.userservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProjectModel implements Serializable {

	private static final long serialVersionUID = -2900387168892016601L;
	
	private Long id;
	
	private String tittle;

	private String subTittle;

}
