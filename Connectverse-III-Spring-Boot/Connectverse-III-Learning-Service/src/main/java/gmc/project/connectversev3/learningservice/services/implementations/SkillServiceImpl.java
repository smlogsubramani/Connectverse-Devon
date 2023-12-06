package gmc.project.connectversev3.learningservice.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.project.connectversev3.learningservice.daos.CommentDao;
import gmc.project.connectversev3.learningservice.daos.EmployeeDao;
import gmc.project.connectversev3.learningservice.daos.SkillDao;
import gmc.project.connectversev3.learningservice.entities.CommentEntity;
import gmc.project.connectversev3.learningservice.entities.EmployeeEntity;
import gmc.project.connectversev3.learningservice.entities.ReplyEntity;
import gmc.project.connectversev3.learningservice.entities.SkillEntity;
import gmc.project.connectversev3.learningservice.exceptions.CommentNotFoundException;
import gmc.project.connectversev3.learningservice.exceptions.ReplyNotFoundException;
import gmc.project.connectversev3.learningservice.exceptions.SkillNotFoundException;
import gmc.project.connectversev3.learningservice.exceptions.UserNotFoundException;
import gmc.project.connectversev3.learningservice.models.CommentModel;
import gmc.project.connectversev3.learningservice.models.ReplyModel;
import gmc.project.connectversev3.learningservice.models.SkillListModel;
import gmc.project.connectversev3.learningservice.models.SkillModel;
import gmc.project.connectversev3.learningservice.services.SkillService;


@Service
public class SkillServiceImpl implements SkillService {

	@Autowired
	private SkillDao skillDao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public SkillEntity findById(String id) {
		SkillEntity foundSkill = skillDao.findById(id).orElse(null);
		if (foundSkill == null)
			throw new SkillNotFoundException("Skill id: " + id);
		return foundSkill;
	}

	@Override
	public List<SkillModel> findAllSkills() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<SkillModel> returnValue = new ArrayList<>();
		skillDao.findAll().stream().iterator().forEachRemaining(skill -> {
			returnValue.add(modelMapper.map(skill, SkillModel.class));
		});
		return returnValue;
	}

	@Override
	public SkillModel findASkill(String id) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		SkillModel skillModel = modelMapper.map(findById(id), SkillModel.class);
		return skillModel;
	}

	@Override
	public void saveSkill(SkillModel skillModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		if (skillModel.getId() == null) {
			SkillEntity detached = modelMapper.map(skillModel, SkillEntity.class);
			SkillEntity saved = skillDao.save(detached);
			saved.getCourseContents().forEach(courseContents -> {
				courseContents.setSkill(saved);
			});
			skillDao.save(saved);
		} else {
			SkillEntity existing = findById(skillModel.getId());
			existing.setTittle(skillModel.getTittle());
			existing.setSubTittle(skillModel.getSubTittle());
			existing.setSkillsGained(skillModel.getSkillsGained());
			existing.setProvider(skillModel.getProvider());
			existing.setJobTittles(skillModel.getJobTittles());
			existing.setPreRequirements(skillModel.getPreRequirements());
			existing.setAverageTimeToFinishCourse(skillModel.getAverageTimeToFinishCourse());
			existing.setJobsCanBeApplied(skillModel.getJobsCanBeApplied());
			existing.setImageUrl(skillModel.getImageUrl());
			existing.setIsHidden(skillModel.getIsHidden());
			skillDao.save(existing);
		}
	}

	@Override
	public void saveManySkills(List<SkillModel> skills) {
		skills.forEach(skill -> {
			saveSkill(skill);
		});
	}

	@Override
	public void deleteAllSkills() {
		skillDao.deleteAll();
	}

	@Override
	public void addCommentToSkill(CommentModel commentModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		CommentEntity detached = modelMapper.map(commentModel, CommentEntity.class);
		SkillEntity foundSkill = findById(commentModel.getSkillId());
		EmployeeEntity commentedBy = employeeDao.findById(commentModel.getCommentedBy()).orElse(null);
		if(commentedBy == null) 
			throw new UserNotFoundException("User Id: " + commentModel.getCommentedBy());
		detached.setSkill(foundSkill);
		detached.setCommentedBy(commentedBy.getFirstName());
		detached.setLikes(0);
		detached.setReports(0);
		foundSkill.getComments().add(detached);
		skillDao.save(foundSkill);
	}

	@Override
	public void addReplyToSkillComment(ReplyModel replyModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ReplyEntity detached = modelMapper.map(replyModel, ReplyEntity.class);
		SkillEntity foundSkill = findById(replyModel.getSkillId());
		EmployeeEntity commentedBy = employeeDao.findById(replyModel.getCommentedBy()).orElse(null);
		if(commentedBy == null) 
			throw new UserNotFoundException("User Id: " + replyModel.getCommentedBy());
		CommentEntity foundComment = commentDao.findById(replyModel.getCommentId()).orElse(null);
		if(foundComment == null)
			throw new CommentNotFoundException("Comment Id: " + replyModel.getCommentId());
		detached.setCommentedBy(commentedBy.getFirstName());
		detached.setLikes(0);
		detached.setReports(0);
		detached.setComment(foundComment);
		foundComment.getReplies().add(detached);
		CommentEntity saved = commentDao.save(foundComment);
		foundSkill.getComments().remove(foundComment);
		foundSkill.getComments().add(saved);
		skillDao.save(foundSkill);
	}

	@Override
	public void likeAComment(String commentId) {
		CommentEntity foundComment = commentDao.findById(commentId).orElse(null);
		if(foundComment == null)
			throw new CommentNotFoundException("Comment Id: " + commentId);
		foundComment.setLikes(foundComment.getLikes()+1);
		commentDao.save(foundComment);
	}

	@Override
	public void reportAComment(String commentId) {
		CommentEntity foundComment = commentDao.findById(commentId).orElse(null);
		if(foundComment == null)
			throw new CommentNotFoundException("Comment Id: " + commentId);
		foundComment.setReports(foundComment.getReports()+1);
		commentDao.save(foundComment);
	}

	@Override
	public void likeAReply(String commentId, String replyId) {
		CommentEntity foundComment = commentDao.findById(commentId).orElse(null);
		if(foundComment == null)
			throw new CommentNotFoundException("Comment Id: " + commentId);
		ReplyEntity foundReply = foundComment.getReplies().stream().filter(reply -> reply.getId().equals(replyId)).findFirst().orElse(null);
		if(foundReply == null)
			throw new ReplyNotFoundException("Reply Id: " + replyId);
		foundComment.getReplies().remove(foundReply);
		foundReply.setLikes(foundReply.getLikes()+1);
		foundComment.getReplies().add(foundReply);
		commentDao.save(foundComment);
	}

	@Override
	public void reportAReply(String commentId, String replyId) {
		CommentEntity foundComment = commentDao.findById(commentId).orElse(null);
		if(foundComment == null)
			throw new CommentNotFoundException("Comment Id: " + commentId);
		ReplyEntity foundReply = foundComment.getReplies().stream().filter(reply -> reply.getId().equals(replyId)).findFirst().orElse(null);
		if(foundReply == null)
			throw new ReplyNotFoundException("Reply Id: " + replyId);
		foundComment.getReplies().remove(foundReply);
		foundReply.setReports(foundReply.getReports()+1);
		foundComment.getReplies().add(foundReply);
		commentDao.save(foundComment);
	}

	@Override
	public List<SkillListModel> findAllSkillsList() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<SkillListModel> returnValue = new ArrayList<>();
		skillDao.findAll().stream().iterator().forEachRemaining(skill -> {
			SkillListModel skillListModel = new SkillListModel();
			skillListModel.setId(skill.getId());
			skillListModel.setName(skill.getTittle());
			returnValue.add(skillListModel);
		});
		return returnValue;
	}

}
