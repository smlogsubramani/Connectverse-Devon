package gmc.project.connectversev3.learningservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.project.connectversev3.learningservice.models.CommentModel;
import gmc.project.connectversev3.learningservice.models.ReplyModel;
import gmc.project.connectversev3.learningservice.models.SkillListModel;
import gmc.project.connectversev3.learningservice.models.SkillModel;
import gmc.project.connectversev3.learningservice.services.SkillService;

@RestController
@RequestMapping(path = "/skill")
public class SkillController {
	
	@Autowired
	private SkillService skillService;
	
	@GetMapping(path = "/list")
	private ResponseEntity<List<SkillListModel>> getAllSkillsList() {
		List<SkillListModel> returnValue = skillService.findAllSkillsList();
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@GetMapping
	private ResponseEntity<List<SkillModel>> getAllSkills() {
		List<SkillModel> returnValue = skillService.findAllSkills();
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@GetMapping(path = "/{skillId}")
	private ResponseEntity<SkillModel> getASkills(@PathVariable String skillId) {
		SkillModel returnValue = skillService.findASkill(skillId);
		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@PostMapping
	private ResponseEntity<String> saveSkills(@RequestBody SkillModel skillModel) {
		skillService.saveSkill(skillModel);
		return ResponseEntity.status(HttpStatus.OK).body("Skill Successfully saved...");
	}
	
	@PostMapping(path = "/comment/add")
	private ResponseEntity<String> addComment(@RequestBody CommentModel commentModel) {
		skillService.addCommentToSkill(commentModel);
		return ResponseEntity.status(HttpStatus.OK).body("Comment Posted...");
	}
	
	@GetMapping(path = "/comment/{commentId}/like")
	private ResponseEntity<String> likeComment(@PathVariable String commentId) {
		skillService.likeAComment(commentId);
		return ResponseEntity.status(HttpStatus.OK).body("Liked...");
	}
	
	@GetMapping(path = "/comment/{commentId}/report")
	private ResponseEntity<String> reportComment(@PathVariable String commentId) {
		skillService.reportAComment(commentId);
		return ResponseEntity.status(HttpStatus.OK).body("Reported...");
	}
	
	@PostMapping(path = "/reply/add")
	private ResponseEntity<String> addReply(@RequestBody ReplyModel replyModel) {
		skillService.addReplyToSkillComment(replyModel);
		return ResponseEntity.status(HttpStatus.OK).body("Reply Posted...");
	}
	
	@GetMapping(path = "/comment/{commentId}/reply/{replyId}/like")
	private ResponseEntity<String> likeReply(@PathVariable String commentId, @PathVariable String replyId) {
		skillService.likeAReply(commentId, replyId);
		return ResponseEntity.status(HttpStatus.OK).body("Liked...");
	}
	
	@GetMapping(path = "/comment/{commentId}/reply/{replyId}/report")
	private ResponseEntity<String> reportReply(@PathVariable String commentId, @PathVariable String replyId) {
		skillService.reportAReply(commentId, replyId);
		return ResponseEntity.status(HttpStatus.OK).body("Reported...");
	}
	
	@PostMapping(path = "/many")
	private ResponseEntity<String> saveManySkills(@RequestBody List<SkillModel> skills) {
		skillService.saveManySkills(skills);
		return ResponseEntity.status(HttpStatus.OK).body("Skills Successfully saved...");
	}

	@DeleteMapping
	private ResponseEntity<String> deleteAllSkills() {
		skillService.deleteAllSkills();
		return ResponseEntity.status(HttpStatus.OK).body("Skills Successfully deleted...");
	}
	
}
