package com.poll.pollsapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.stereotype.Repository;

import com.poll.pollsapp.models.User;


@Repository
public interface UserRepository  extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByUsernameOrEmail(String email, String username);
	
    List<User> findByIdIn(List<Long> userIds);
    
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);
	
	
	
	

}
