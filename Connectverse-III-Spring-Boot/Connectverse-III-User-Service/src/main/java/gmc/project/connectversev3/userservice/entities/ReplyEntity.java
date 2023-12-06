package gmc.project.connectversev3.userservice.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(exclude = {"comment"})
@Embeddable
@Table(name = "replies")
@Entity
@Data
public class ReplyEntity implements Serializable {

	private static final long serialVersionUID = 2303677466166294005L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	private Integer likes;
	
    private Integer reports;
    
    private String reply;
    
	private String commentedBy;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private CommentEntity comment;

}
