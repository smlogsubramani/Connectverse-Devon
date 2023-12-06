package gmc.project.connectversev3.jobservice.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "reports")
public class ReportEntity implements Serializable {

	private static final long serialVersionUID = 26456801355732717L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id")
	private String id;
	
	private String description;
	
	private String cause;
	
	private String sentFrom;
	
	private String publicComment;
	
	private String systemLog;
	
	private String technicianComment;
	
	private Boolean isFixed;
	
	private LocalDateTime occuredAt;

}
