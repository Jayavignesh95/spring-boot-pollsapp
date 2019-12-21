package com.poll.pollsapp.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.poll.pollsapp.models.Poll;
import com.poll.pollsapp.payload.ApiResponse;
import com.poll.pollsapp.payload.PagedResponse;
import com.poll.pollsapp.payload.PollRequest;
import com.poll.pollsapp.payload.PollResponse;
import com.poll.pollsapp.payload.VoteRequest;
import com.poll.pollsapp.repository.PollRepository;
import com.poll.pollsapp.repository.UserRepository;
import com.poll.pollsapp.repository.VoteRepository;
import com.poll.pollsapp.security.CurrentUser;
import com.poll.pollsapp.security.UserPrincipal;
import com.poll.pollsapp.service.PollService;
import com.poll.pollsapp.utils.AppConstants;

@CrossOrigin("http://localhost:300") 
@RestController
@RequestMapping("/api/polls")
public class PollController {

	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private PollService pollService;

	
	private static final Logger logger = LoggerFactory.getLogger(PollController.class);
	
    @GetMapping
    public PagedResponse<PollResponse> getPolls(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getAllPolls(currentUser, page, size);
    }

	
	@GetMapping("/{pollId}")
	public PollResponse getPollById(@CurrentUser UserPrincipal currentUser,@PathVariable Long pollId)
	{
		return pollService.getByPollId(pollId,currentUser);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createPOll(@Valid @RequestBody PollRequest pollRequest)
	{
		System.out.println("I am here to create poll");
		System.out.println("values are "+pollRequest.getQuestion());
		System.out.println("values are as "+pollRequest.getPollLength().getDays());
		System.out.println("values are 3"+pollRequest.getChoices().size());
		Poll poll = pollService.createPoll(pollRequest);
		URI	location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pollId}")
				.buildAndExpand(poll.getId()).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true,"Poll Created SuccessFully"));
	}
	
	@PostMapping("/{pollId}/votes")
	@PreAuthorize("hasRole('USER')")
	public PollResponse castVote(@CurrentUser UserPrincipal user,@PathVariable Long pollId,@Valid @RequestBody VoteRequest voteRequest )
	{
		return pollService.castVoteandGetUpdatePoll(pollId,user,voteRequest);
		
	}
}
