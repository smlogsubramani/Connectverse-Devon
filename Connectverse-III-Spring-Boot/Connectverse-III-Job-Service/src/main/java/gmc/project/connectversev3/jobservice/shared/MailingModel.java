package gmc.project.connectversev3.jobservice.shared;

import java.io.Serializable;

import lombok.Data;

@Data
public class MailingModel implements Serializable {

	private static final long serialVersionUID = 6636967083916248993L;

	private String to;

	private String subject;

	private String body;

}
