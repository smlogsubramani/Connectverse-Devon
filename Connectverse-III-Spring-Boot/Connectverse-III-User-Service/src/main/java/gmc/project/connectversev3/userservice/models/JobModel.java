package gmc.project.connectversev3.userservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class JobModel implements Serializable {

	private static final long serialVersionUID = -5126093076300019728L;
	
	private Long id;
	
	private String tittle;

}
