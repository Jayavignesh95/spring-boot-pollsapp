package com.poll.pollsapp.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poll.pollsapp.models.Mail;
import com.poll.pollsapp.models.EmailQueuePayload;
import com.poll.pollsapp.models.User;
import com.poll.pollsapp.models.VerificationToken;
import com.poll.pollsapp.repository.MailRepository;
import com.poll.pollsapp.repository.TokenRepository;
import com.poll.pollsapp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	public MailRepository mailRepo;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public TokenRepository tokenRepo;

	@Autowired
	EmailQueueService emailQueueService;
	
	public User processAccountCreation(User user)
	{
			System.out.println("I am in Userservice for saving and sending email");
		 	User result = userRepository.save(user);
	        String randToken = UUID.randomUUID().toString();
	        VerificationToken token = new VerificationToken(randToken,result);
	        tokenRepo.save(token);
	        //mail to service
	        Mail mail = new Mail();
	        mail.setAttachment(false);
	        mail.setInstant(true);
	        mail.setTo(user.getEmail());
	        mail.setSubject("Welcome to Polls App");
	        mail.setText("Account is successfully created. Thank you");
	        Mail mailObj =mailRepo.save(mail);
	        EmailQueuePayload msg = new EmailQueuePayload();
	        msg.setId(mailObj.getId());
	        msg.setType("EmailObject");
	        emailQueueService.SendMessage(msg);
	        return result;
	}

}
