package com.intellect.serverstatuschecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intellect.serverstatuschecker.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByUserName(String username);

}
