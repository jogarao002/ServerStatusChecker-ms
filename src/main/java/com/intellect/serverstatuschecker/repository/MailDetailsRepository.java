package com.intellect.serverstatuschecker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intellect.serverstatuschecker.domain.MailDetails;

public interface MailDetailsRepository extends JpaRepository<MailDetails, Long> {

	MailDetails findByPersonPriorityAndStatus(Integer one, Boolean status);

	List<MailDetails> findByStatus(Boolean status);

}
