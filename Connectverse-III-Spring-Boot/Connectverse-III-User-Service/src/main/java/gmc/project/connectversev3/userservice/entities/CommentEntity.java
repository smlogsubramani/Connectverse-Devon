package gmc.project.connectversev3.userservice.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(exclude = {"replies", "skill"})
@Table(name = "comments")
@Entity
@Data
public class CommentEntity implements Serializable {

	private static final long serialVersionUID = -4949418123705019550L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	private Integer likes;
	
	private Integer reports;
	
	private String comment;
	
	private String commentedBy;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private SkillEntity skill;
	
	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
	private Set<ReplyEntity> replies = new HashSet<>();

}
