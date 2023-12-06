package gmc.project.connectversev3.prophetservice.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "mails_sent")
public class MailEntity implements Serializable {

	private static final long serialVersionUID = 7482689138908695260L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@Column(name = "sent_from")
	private String sentFromMailId;
	
	@Column(name = "sent_to")
	private String sentToMailId;
	
	@Column(name = "subject")
	private String subject;
	
	@Lob
	@Column(name = "body")
	private String body;
	
	@Column(name = "sent_at")
	private LocalDateTime sentAt;

}
