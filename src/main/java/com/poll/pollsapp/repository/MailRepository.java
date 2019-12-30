package com.poll.pollsapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poll.pollsapp.models.Mail;

public interface MailRepository extends JpaRepository<Mail, Long> {
	Optional<Mail> findById(Long pollId);

}
