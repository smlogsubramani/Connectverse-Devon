package gmc.project.connectversev3.jobservice.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "job_declines")
public class JobDeclineEntity implements Serializable {
	
	private static final long serialVersionUID = 4662461392954835615L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String reason;
	
	private String jobId;
	
	private String employeeId;
	
	private LocalDateTime happedAt;
	
}
