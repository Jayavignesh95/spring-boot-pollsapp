package com.poll.pollsapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poll.pollsapp.exception.ResourceNotFoundException;
import com.poll.pollsapp.models.User;
import com.poll.pollsapp.payload.PagedResponse;
import com.poll.pollsapp.payload.PollResponse;
import com.poll.pollsapp.payload.UserIdentityAvailability;
import com.poll.pollsapp.payload.UserProfile;
import com.poll.pollsapp.payload.UserSummary;
import com.poll.pollsapp.repository.PollRepository;
import com.poll.pollsapp.repository.UserRepository;
import com.poll.pollsapp.repository.VoteRepository;
import com.poll.pollsapp.security.CurrentUser;
import com.poll.pollsapp.security.UserPrincipal;
import com.poll.pollsapp.service.PollService;
import com.poll.pollsapp.utils.AppConstants;

@CrossOrigin("http://localhost:3000") 
@RestController
@RequestMapping("api")
public class UserController {

	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	
	private PollService pollService;

	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
	@GetMapping("/user/checkUsernameAvailability")
	public UserIdentityAvailability checkUserNameAvailability(@RequestParam(value="username") String name)
	{
		System.out.println("Hey User Check");
    	Boolean isAvailable = !userRepository.existsByEmail(name);

		return new UserIdentityAvailability(isAvailable);
	}
	

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        System.out.println("Hey email check");
    	Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal user)
    {
    	UserSummary usersummary = new UserSummary(user.getId(), user.getUsername(),user.getName());
    	return usersummary;
    }
    

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value="username") String username)
    {
    	User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User", "username", username));
    	long pollCount = pollRepository.countByCreatedBy(user.getId());
    	long voteCount = voteRepository.countByUserId(user.getId());
    	UserProfile userProfile = new UserProfile(username, user.getName(), user.getId(), user.getCreatedAt(), voteCount, pollCount);
    	return userProfile;
    }
    
    @GetMapping("/users/{username}/polls")
    public PagedResponse<PollResponse> getPollsCreatedBy(
    		@PathVariable(value="username") String username,
    		@CurrentUser UserPrincipal currentUser,
    		@RequestParam(value="page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
    		@RequestParam(value ="size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size)
    {
    	return pollService.getPollsCreatedBy(username,currentUser,page,size);
    	
    }
    
    
    @GetMapping("/user/{username}/votes")
    public PagedResponse<PollResponse> getPollsVotedBy(
    		@PathVariable(value="username") String username,
    		@CurrentUser UserPrincipal currentUser,
    		@RequestParam(value="page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
    		@RequestParam(value ="size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) 
{
    	return pollService.getPollsVotedBy(username, currentUser, page, size);
}

}
