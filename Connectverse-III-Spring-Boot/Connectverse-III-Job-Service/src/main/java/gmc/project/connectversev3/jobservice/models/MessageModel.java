package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageModel implements Serializable {

	private static final long serialVersionUID = 6243218614908509733L;
	
	private Long id;
	
	private String message;
	
	private String sentBy;
	
	private LocalDateTime sentAt;

}
