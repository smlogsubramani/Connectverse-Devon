package gmc.project.connectversev3.authservice.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(exclude = {"skill"})
@Embeddable
@Entity
@Data
public class CourseContentEntity implements Serializable {

	private static final long serialVersionUID = -1750784679656026425L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String tittle;
	
	@ElementCollection
	private Set<String> subTittles = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	private SkillEntity skill;

}
