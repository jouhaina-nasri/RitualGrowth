package com.yoterra.hippo.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	
	private static final Logger log = LoggerFactory.getLogger(EmailSenderService.class);

	// https://www.baeldung.com/spring-email
	
    @Autowired
    private JavaMailSender emailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmailAddr;
    
    private String emailServerUrl;
    
    @Value("${com.hippo.email.test.receiver.enabled}")
    private boolean testReceiverEnabled;
    
    private volatile String testModeReceiver = null;		// hack

    @Autowired
    public EmailSenderService(@Value("${com.hippo.email.sender.server.url}") String emailServerUrl) {
    	this.emailServerUrl = StringUtils.removeEnd(StringUtils.trim(emailServerUrl), "/");
    }
    
    @Deprecated
    public void setTestReceiver(String email) {
		Validate.isTrue(testReceiverEnabled, "Test mode email receiver not enabled");
		Validate.isTrue(EmailValidator.getInstance().isValid(email), "Invalid email");
		this.testModeReceiver = email;
    }
    
    @Deprecated
    public void removeTestReceiver() {
		Validate.isTrue(testReceiverEnabled, "Test mode email receiver not enabled");
		this.testModeReceiver = null;
    }
    
	public void sendPaypalMethodVerificationEmail(Long uid, Long pmId, String email, String token){
		String url = buildVerificationUrl(uid, pmId, token);
        sendEmail(email, "Paypal payout method verification", "Follow the link: \n " + url);
        log.info("Successfully sent Paypal payout method verification email to: {}, url: {}", email, url);
        
	}
	
	public void sendAccountReactivationEmail(String email, String token){
		String url = buildAccReactivationUrl(email, token);
        sendEmail(email, "Hippo account re-activation", "Follow the link: \n " + url);
        log.info("Successfully sent account re-acctivation e-mail: {}, url: {}", email, url);
        
	}
	
    public void sendEmail(String to, String subject, String text) {
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setFrom(fromEmailAddr);
    	String receiver = testReceiverEnabled && testModeReceiver != null ? testModeReceiver : to;
    	message.setTo(receiver);
    	message.setSubject(subject);
    	message.setText(text);
		emailSender.send(message);
    }
	
	private String buildVerificationUrl(Long uid, Long pmId, String token){
		return emailServerUrl + "/eml/paypalconf?uid=" + uid + "&pmid=" + pmId + "&t=" + token; 
	}
	
	private String buildAccReactivationUrl(String email, String token){
		return emailServerUrl + "/eml/accreact?e=" + email + "&t=" + token; 
	}
}
