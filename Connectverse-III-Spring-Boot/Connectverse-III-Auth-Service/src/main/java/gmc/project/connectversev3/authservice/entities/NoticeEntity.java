package gmc.project.connectversev3.authservice.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Embeddable
@Table(name = "notices")
@EqualsAndHashCode(exclude = {"project"})
public class NoticeEntity implements Serializable {

	private static final long serialVersionUID = 2030939121610034671L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String message;
	
	private String sentBy;
	
	private LocalDateTime sentAt;
	
	@ManyToOne
	private ProjectEntity project;

}
