package gmc.project.connectversev3.userservice.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
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
@Table(name = "messages")
@EqualsAndHashCode(exclude = {"job", "project"})
public class MessageEntity implements Serializable {

	private static final long serialVersionUID = -2497034543041889090L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String message;
	
	private String sentBy;
	
	private LocalDateTime sentAt;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	private JobEntity job;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	private ProjectEntity project;
	
}
