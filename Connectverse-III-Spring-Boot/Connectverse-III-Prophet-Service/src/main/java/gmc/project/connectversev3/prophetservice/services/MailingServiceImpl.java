package gmc.project.connectversev3.prophetservice.services;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import gmc.project.connectversev3.prophetservice.configurations.MailConfig;
import gmc.project.connectversev3.prophetservice.daos.MailDao;
import gmc.project.connectversev3.prophetservice.entities.MailEntity;
import gmc.project.connectversev3.prophetservice.models.MailingModel;

@Service
public class MailingServiceImpl implements MailingService {
	
	@Autowired
	private MailDao mailDao;
	@Autowired
	private MailConfig mailConfig;
	@Autowired
	private JavaMailSender mailSender;

	@Async
	@Override
	public void sendMail(MailingModel mailingModel) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(mailingModel.getSubject());
			mimeMessageHelper.setFrom(new InternetAddress(mailConfig.getUsername(), "GMC@3d"));
			mimeMessageHelper.setTo(mailingModel.getTo());
			mimeMessageHelper.setText(mailingModel.getBody(), true);

			mailSender.send(mimeMessageHelper.getMimeMessage());
			
			MailEntity mail = new MailEntity();
			mail.setSentFromMailId(mailConfig.getUsername());
			mail.setSentToMailId(mailingModel.getTo());
			mail.setSubject(mailingModel.getSubject());
			mail.setBody(mailingModel.getBody());
			mail.setSentAt(LocalDateTime.now());
			mailDao.save(mail);

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}
