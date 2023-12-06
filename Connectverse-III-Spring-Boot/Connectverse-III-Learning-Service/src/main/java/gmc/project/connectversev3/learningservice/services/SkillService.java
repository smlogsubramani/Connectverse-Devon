package gmc.project.connectversev3.learningservice.services;

import java.util.List;

import gmc.project.connectversev3.learningservice.entities.SkillEntity;
import gmc.project.connectversev3.learningservice.models.CommentModel;
import gmc.project.connectversev3.learningservice.models.ReplyModel;
import gmc.project.connectversev3.learningservice.models.SkillListModel;
import gmc.project.connectversev3.learningservice.models.SkillModel;

public interface SkillService {
	public SkillEntity findById(String id);
	public List<SkillModel> findAllSkills();
	public List<SkillListModel> findAllSkillsList();
	public SkillModel findASkill(String id);
	public void saveSkill(SkillModel skillModel);
	public void saveManySkills(List<SkillModel> skills);
	public void deleteAllSkills();
	public void addCommentToSkill(CommentModel commentModel);
	public void likeAComment(String commentId);
	public void reportAComment(String commentId);
	public void likeAReply(String commentId, String replyId);
	public void reportAReply(String commentId, String replyId);
	public void addReplyToSkillComment(ReplyModel replyModel);
}
