package com.poll.pollsapp.controller;

import java.net.URI;
import java.util.Collections;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.poll.pollsapp.exception.AppException;
import com.poll.pollsapp.models.Role;
import com.poll.pollsapp.models.RoleName;
import com.poll.pollsapp.models.User;
import com.poll.pollsapp.models.VerificationToken;
import com.poll.pollsapp.payload.ApiResponse;
import com.poll.pollsapp.payload.JwtAuthenticationResponse;
import com.poll.pollsapp.payload.LoginRequest;
import com.poll.pollsapp.payload.SignUpRequest;
import com.poll.pollsapp.repository.RoleRepository;
import com.poll.pollsapp.repository.TokenRepository;
import com.poll.pollsapp.repository.UserRepository;
import com.poll.pollsapp.security.JwtTokenProvider;
import com.poll.pollsapp.service.EmailSenderService;
import com.poll.pollsapp.service.UserService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	private EmailSenderService notificationService;


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;	
    
    
    @Autowired
    UserService userService;
    
    @PostMapping("/signin")
    public ResponseEntity<?> UserSignIn(@Valid @RequestBody LoginRequest loginRequest)
    {
    	System.out.println("I am in the sign in Rest controller");
    	System.out.println("User Name and pass word is "+loginRequest.getUserNameorEMail()+"   Pass "+loginRequest.getPassword());
    	Authentication authentication = authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(loginRequest.getUserNameorEMail(),loginRequest.getPassword()));
		
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    	String jwt = tokenProvider.generateToken(authentication);
    	System.out.println("Token is generate with JWT  "+jwt);
    	return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
       	
    }
	
    @GetMapping("/mail")
    public void email()
    {
    	System.out.println("I am in Email");
    	notificationService.sendEmail();
    }
	@PostMapping("/signup")
	public ResponseEntity<?> UserSignUp(@Valid @RequestBody SignUpRequest signupRequest)
	{
		System.out.println("I am in Sign up controller");

		if(userRepository.existsByUsername(signupRequest.getUserName()))
		{
		return new ResponseEntity(new ApiResponse(false, "User Name is already Taken"),HttpStatus.BAD_REQUEST);	
		}

		if(userRepository.existsByEmail(signupRequest.getEmail()))
		{
		return new ResponseEntity(new ApiResponse(false, "Email ID  is already in user"),HttpStatus.BAD_REQUEST);	
		}

		// Creating new user 
		User user = new User(signupRequest.getName(), signupRequest.getUserName(), signupRequest.getEmail(), signupRequest.getPassword());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        
        System.out.println("I am going to save the user to DB");
        
        User result=userService.processAccountCreation(user);
       
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        System.out.println("Location is "+location.getPath());

        return new ResponseEntity(new ApiResponse(true, "User is registered to DB"),HttpStatus.ACCEPTED);
	}
}
