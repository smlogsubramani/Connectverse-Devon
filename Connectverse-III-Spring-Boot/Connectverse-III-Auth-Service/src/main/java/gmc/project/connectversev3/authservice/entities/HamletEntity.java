package gmc.project.connectversev3.authservice.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import gmc.project.connectversev3.authservice.models.WorkType;
import lombok.Data;


@Data
@Entity
@Table(name = "hamlets")
public class HamletEntity implements Serializable {

	private static final long serialVersionUID = -5612680876197917601L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id")
	private String id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "expected_wage_per_day")
	private Integer expectedWagePerHour;
	
	@Column(name = "deliverable_work")
	private Integer deliverableWork;
	
	@Column(name = "total_count")
	private Integer totalCount = 6;
	
	@Column(name = "male_count")
	private Integer maleCount;
	
	@Column(name = "female_count")
	private Integer femaleCount;
	
	@Column(name = "transgender_count")
	private Integer transGenderCount;
	
	@Column(name = "is_working")
	private Boolean isWorking = false;
	
	@Column(name = "is_blocked")
	private Boolean isBlocked = false;
	
	@Enumerated(value = EnumType.STRING)
	private WorkType workType;
	
	@OneToMany(mappedBy = "hamlet")
	private Set<EmployeeEntity> employees = new HashSet<>();
	
	@OneToOne(optional = true)
	private JobEntity job;

}
