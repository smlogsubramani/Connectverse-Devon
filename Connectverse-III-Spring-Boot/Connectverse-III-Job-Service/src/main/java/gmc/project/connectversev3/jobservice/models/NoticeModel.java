package gmc.project.connectversev3.jobservice.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoticeModel implements Serializable {
	
	private static final long serialVersionUID = 7139607821015806072L;
	
	private Long id;
	
	private String message;
	
	private String sentBy;
	
	private LocalDateTime sentAt;
	
	private Long projectId;

}
