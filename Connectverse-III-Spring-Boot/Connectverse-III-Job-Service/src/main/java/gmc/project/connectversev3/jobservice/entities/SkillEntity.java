package gmc.project.connectversev3.jobservice.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
//@Getter
//@Setter
@Entity
@EqualsAndHashCode(exclude = {"comments"})
@Table(name = "skills")
public class SkillEntity implements Serializable {

	private static final long serialVersionUID = -1050686383563171113L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String tittle;
	
	private String subTittle;
	
	private String skillsGained;
	
	private String provider;
	
	private String jobTittles;
	
	private String preRequirements;
	
	private String averageTimeToFinishCourse;
	
	private String jobsCanBeApplied;
	
	@Lob
	private String roadMapUrl;
	
	@Lob
	private String youtubeUrl;
	
	@Lob
	private String useFulLink;
	
	@Lob
	private String imageUrl;
	
	private Boolean isHidden;
	
	@OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
	private Set<CommentEntity> comments = new HashSet<>();
	
	@OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
	private Set<CourseContentEntity> courseContents = new HashSet<>();
	
	@ManyToMany(mappedBy = "skills", cascade = CascadeType.ALL)
	private Set<ProjectEntity> projects = new HashSet<>();

	@ManyToMany
	private Set<JobEntity> jobs = new HashSet<>();
	
}
