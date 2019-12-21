package com.poll.pollsapp.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.poll.pollsapp.models.User;
import com.poll.pollsapp.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	
	@Autowired
	UserRepository userRepository;
	// This is the method that is used to get UserPrinicapal object for
	// token authentication
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsernameOrEmail(username, username).orElseThrow(()->new UsernameNotFoundException("User Not found in app"));
		return UserPrincipal.create(user);
	}
	
	@Transactional
	public UserDetails loadUserById(Long id)
	{
		User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found in app"));
		
		return UserPrincipal.create(user);
	}

}
