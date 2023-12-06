package gmc.project.connectversev3.userservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginRequestModel implements Serializable {
	
	private static final long serialVersionUID = -7851814934448737511L;
	
	private String userName;
	
	private String password;

}
