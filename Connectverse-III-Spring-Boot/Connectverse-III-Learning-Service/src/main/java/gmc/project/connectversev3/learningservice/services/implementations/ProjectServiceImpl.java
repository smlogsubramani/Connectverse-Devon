package gmc.project.connectversev3.learningservice.services.implementations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.project.connectversev3.learningservice.daos.EmployeeDao;
import gmc.project.connectversev3.learningservice.daos.ProjectDao;
import gmc.project.connectversev3.learningservice.daos.SkillDao;
import gmc.project.connectversev3.learningservice.entities.EmployeeEntity;
import gmc.project.connectversev3.learningservice.entities.NoticeEntity;
import gmc.project.connectversev3.learningservice.entities.ProjectEntity;
import gmc.project.connectversev3.learningservice.entities.SkillEntity;
import gmc.project.connectversev3.learningservice.exceptions.ProjectNotFoundException;
import gmc.project.connectversev3.learningservice.exceptions.ProjectRulesViolationException;
import gmc.project.connectversev3.learningservice.exceptions.SkillNotFoundException;
import gmc.project.connectversev3.learningservice.exceptions.UserNotFoundException;
import gmc.project.connectversev3.learningservice.models.ListEmployeeModel;
import gmc.project.connectversev3.learningservice.models.ListProjectModel;
import gmc.project.connectversev3.learningservice.models.MessageModel;
import gmc.project.connectversev3.learningservice.models.NoticeModel;
import gmc.project.connectversev3.learningservice.models.ProjectCreateOrUpdateModel;
import gmc.project.connectversev3.learningservice.models.ProjectModel;
import gmc.project.connectversev3.learningservice.models.SkillModel;
import gmc.project.connectversev3.learningservice.services.ProjectsService;

@Service
public class ProjectServiceImpl implements ProjectsService {

	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SkillDao skillDao;

	@Override
	public ProjectEntity findById(Long id) {
		ProjectEntity returnValue = projectDao.findById(id).orElse(null);
		if (returnValue == null)
			throw new ProjectNotFoundException();
		return returnValue;
	}

	@Override
	public List<ListProjectModel> getAllValidprojects(Boolean isHidden) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);		
		List<ListProjectModel> returnValue = new ArrayList<>();
		List<ProjectEntity> foundProjects = projectDao.findByIsHidden(isHidden);
		foundProjects.forEach(project -> {
			ListProjectModel conv = modelMapper.map(project, ListProjectModel.class);
			SkillEntity[] skills = project.getSkills().toArray(new SkillEntity[project.getSkills().size()]);
			try {
				conv.setSkillImage(skills[0].getImageUrl());
			} catch (Exception e) {
				conv.setSkillImage(null);
			}
			returnValue.add(conv);	
		});
		return returnValue;
	}

	private Boolean isLegal = true;

	public Boolean getIsLegal() {
		return isLegal;
	}

	public void setIsLegal(Boolean isLegal) {
		this.isLegal = isLegal;
	}

	@Override
	public void saveProject(ProjectCreateOrUpdateModel projectCreateOrUpdateModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		if (projectCreateOrUpdateModel.getId() == null) {
			ProjectEntity detached = modelMapper.map(projectCreateOrUpdateModel, ProjectEntity.class);
			EmployeeEntity projectAdmin = employeeDao.findById(projectCreateOrUpdateModel.getProjectAdminId())
					.orElse(null);
			if (projectAdmin == null)
				throw new UserNotFoundException();
			List<ProjectEntity> projects = projectDao.findByProjectAdmin(projectAdmin);
			for (ProjectEntity project : projects)
				if (!project.getIsCompleted())
					setIsLegal(false);
			if (!getIsLegal())
				throw new ProjectRulesViolationException("You are Already an Admin of unfinihsed Project");
			detached.setProjectAdmin(projectAdmin);
			Set<SkillEntity> skills = new HashSet<>();
			projectCreateOrUpdateModel.getSkillIds().forEach(skillId -> {
				SkillEntity foundSkill = skillDao.findById(skillId).orElse(null);
				if (foundSkill == null)
					throw new SkillNotFoundException(skillId);
				skills.add(foundSkill);
			});
			detached.setProjectAdmin(projectAdmin);
			detached.setIsCompleted(false);
			detached.setSkills(skills);
			detached.setIsHidden(false);
			detached.setStartedOn(LocalDate.now());
			detached.setProjectCreaterId(projectAdmin.getId());
			detached.setProjectCreaterName(projectAdmin.getFirstName());
			ProjectEntity saved = projectDao.save(detached);
			projectAdmin.getProjects().add(saved);
			employeeDao.save(projectAdmin);
		} else {
			ProjectEntity detached = findById(projectCreateOrUpdateModel.getId());
			EmployeeEntity projectAdmin = employeeDao.findById(projectCreateOrUpdateModel.getProjectAdminId()).orElse(null);
			if (projectAdmin == null)
				throw new UserNotFoundException();
			detached.setTittle(projectCreateOrUpdateModel.getTittle());
			detached.setSubTittle(projectCreateOrUpdateModel.getSubTittle());
			detached.setDescription(projectCreateOrUpdateModel.getDescription());
			detached.setDurationInMonths(projectCreateOrUpdateModel.getDurationInMonths());
			detached.setDifficultyLevel(projectCreateOrUpdateModel.getDifficultyLevel());
			detached.setTotalMembers(projectCreateOrUpdateModel.getTotalMembers());
			detached.setIsCompleted(projectCreateOrUpdateModel.getIsCompleted());
			Set<SkillEntity> skills = new HashSet<>();
			projectCreateOrUpdateModel.getSkillIds().forEach(skillId -> {
				SkillEntity foundSkill = skillDao.findById(skillId).orElse(null);
				if (foundSkill == null)
					throw new SkillNotFoundException(skillId);
				skills.add(foundSkill);
			});
			detached.setSkills(skills);
			
			if(!detached.getIsCompleted()) {
				detached.setIsHidden(false);
				detached.setProjectAdmin(projectAdmin);
			}
			ProjectEntity saved = projectDao.save(detached);
			projectAdmin.getProjects().add(saved);
			employeeDao.save(projectAdmin);
		}
	}

	@Override
	public void deleteAllProjects() {
		projectDao.deleteAll();	
	}

	@Override
	public ProjectModel findOneProject(Long projectId) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		ProjectEntity foundProject = findById(projectId);		
		ProjectModel returnValue =  new ProjectModel();
		returnValue.setId(projectId);
		returnValue.setTittle(foundProject.getTittle());
		returnValue.setSubTittle(foundProject.getSubTittle());
		returnValue.setDescription(foundProject.getDescription());
		returnValue.setDetailedDescription(foundProject.getDetailedDescription());
		returnValue.setDurationInMonths(foundProject.getDurationInMonths());
		returnValue.setDifficultyLevel(foundProject.getDifficultyLevel());
		returnValue.setTotalMembers(foundProject.getTotalMembers());
		returnValue.setIsCompleted(foundProject.getIsCompleted());
		returnValue.setStartedOn(foundProject.getStartedOn());
		
		List<SkillModel> projectSkills = new ArrayList<>();
		foundProject.getSkills().forEach(skill -> {
			SkillModel convSkill = modelMapper.map(skill, SkillModel.class);
			projectSkills.add(convSkill);
		});
		returnValue.setSkills(projectSkills);
		
		List<NoticeModel> noticesss = new ArrayList<>();
		foundProject.getNotices().forEach(noticeEntity -> {
			noticesss.add(modelMapper.map(noticeEntity, NoticeModel.class));
		});
		returnValue.setNotices(noticesss);
		
		if(!foundProject.getIsHidden() && !foundProject.getIsCompleted()) {
			returnValue.setProjectAdminId(foundProject.getProjectCreaterId());
			returnValue.setProjectAdminName(foundProject.getProjectCreaterName());
			List<ListEmployeeModel> joiningRequests = new ArrayList<>();
			foundProject.getPersonsRequested().forEach(person -> {
				ListEmployeeModel converted = new ListEmployeeModel();
				converted.setId(person.getId());
				converted.setFirstName(person.getFirstName());
				joiningRequests.add(converted);
			});		
			returnValue.setJoiningRequests(joiningRequests);
			
			List<ListEmployeeModel> team = new ArrayList<>();
			foundProject.getTeam().forEach(person -> {
				ListEmployeeModel converted = new ListEmployeeModel();
				converted.setId(person.getId());
				converted.setFirstName(person.getFirstName());
				team.add(converted);
			});		
			returnValue.setTeam(team);
			
			List<MessageModel> projectMessages = new ArrayList<>();
			foundProject.getMessages().forEach(msg -> {
				MessageModel msgMd = modelMapper.map(msg, MessageModel.class);
				projectMessages.add(msgMd);
			});
			returnValue.setMessages(projectMessages);
		}
		
		return returnValue;
	}

	@Override
	public void requestJoinProject(Long projectId, String employeeId) {
		ProjectEntity foundProject = findById(projectId);
		EmployeeEntity requestedPerson = employeeDao.findById(employeeId).orElse(null);
		if(requestedPerson == null)
			throw new UserNotFoundException("EmployeeId: " + employeeId);
		if(foundProject.getTotalMembers() == foundProject.getTeam().size())
			throw new ProjectRulesViolationException("The requirent of the project is already satisfied...");
		foundProject.getPersonsRequested().add(requestedPerson);
		ProjectEntity saved = projectDao.save(foundProject);
		requestedPerson.getRequestedProjects().add(saved);
		employeeDao.save(requestedPerson);
	}

	@Override
	public void joinProjectAccept(Long projectId, String employeeId) {
		ProjectEntity foundProject = findById(projectId);
		EmployeeEntity requestedPerson = employeeDao.findById(employeeId).orElse(null);
		if(requestedPerson == null)
			throw new UserNotFoundException("EmployeeId: " + employeeId);
		foundProject.getPersonsRequested().remove(requestedPerson);
		foundProject.getTeam().add(requestedPerson);
		ProjectEntity saved = projectDao.save(foundProject);
		requestedPerson.getRequestedProjects().remove(foundProject);
		requestedPerson.getProjects().add(saved);
		employeeDao.save(requestedPerson);
	}

	@Override
	public void joinProjectReject(Long projectId, String employeeId) {
		ProjectEntity foundProject = findById(projectId);
		EmployeeEntity requestedPerson = employeeDao.findById(employeeId).orElse(null);
		if(requestedPerson == null)
			throw new UserNotFoundException("EmployeeId: " + employeeId);
		foundProject.getPersonsRequested().remove(requestedPerson);
		projectDao.save(foundProject);
		requestedPerson.getRequestedProjects().remove(foundProject);
		employeeDao.save(requestedPerson);
	}

	@Override
	public void finishProject(Long projectId, String employeeId) {
		ProjectEntity foundProject = findById(projectId);
		EmployeeEntity requestedPerson = employeeDao.findById(employeeId).orElse(null);
		if(requestedPerson == null)
			throw new UserNotFoundException("EmployeeId: " + employeeId);
		foundProject.setIsCompleted(true);
		foundProject.setIsHidden(true);
		if(!foundProject.getProjectAdmin().equals(requestedPerson))
			throw new ProjectRulesViolationException("Only Project Admin can close Projects");
		foundProject.setProjectAdmin(null);
		projectDao.save(foundProject);
		requestedPerson.setAdminOfProject(null);
		employeeDao.save(requestedPerson);
	}

	@Override
	public void addNotice(NoticeModel noticeModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ProjectEntity foundProject = findById(noticeModel.getProjectId());
		EmployeeEntity sendBy = employeeDao.findById(noticeModel.getSentBy()).orElse(null);
		NoticeEntity notice = modelMapper.map(noticeModel, NoticeEntity.class);
		notice.setSentAt(LocalDateTime.now());
		notice.setSentBy(sendBy.getFirstName());
		foundProject.getNotices().add(notice);
		notice.setProject(foundProject);
		projectDao.save(foundProject);
	}

	@Override
	public void removeNotice(NoticeModel noticeModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ProjectEntity foundProject = findById(noticeModel.getProjectId());
		NoticeEntity foundNotice = foundProject.getNotices().stream().filter(notice -> notice.getId().equals(noticeModel.getId())).findFirst().orElse(null);
		foundProject.getNotices().remove(foundNotice);
		projectDao.save(foundProject);
	}

}
