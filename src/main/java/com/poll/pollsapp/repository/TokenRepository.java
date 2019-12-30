 package com.poll.pollsapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poll.pollsapp.models.User;
import com.poll.pollsapp.models.VerificationToken;

public interface TokenRepository extends JpaRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(String token);

}
