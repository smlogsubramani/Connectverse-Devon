package gmc.project.connectversev3.learningservice.services;

import java.util.List;

import gmc.project.connectversev3.learningservice.entities.ProjectEntity;
import gmc.project.connectversev3.learningservice.models.ProjectCreateOrUpdateModel;
import gmc.project.connectversev3.learningservice.models.ProjectModel;
import gmc.project.connectversev3.learningservice.models.ListProjectModel;
import gmc.project.connectversev3.learningservice.models.NoticeModel;

public interface ProjectsService {
	public ProjectEntity findById(Long id);
	public ProjectModel findOneProject(Long projectId);
	public void finishProject(Long projectId, String employeeId);
	public void addNotice(NoticeModel noticeModel);
	public void removeNotice(NoticeModel noticeModel);
	public List<ListProjectModel> getAllValidprojects(Boolean isHidden);
	public void saveProject(ProjectCreateOrUpdateModel projectCreateOrUpdateModel);
	public void requestJoinProject(Long projectId, String employeeId);
	public void joinProjectAccept(Long projectId, String employeeId);
	public void joinProjectReject(Long projectId, String employeeId);
	public void deleteAllProjects();
}
