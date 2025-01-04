package com.intellect.serverstatuschecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intellect.serverstatuschecker.domain.Users;

/**
 * Spring Data JPA repository for the Region entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByEmail(String username);

}
