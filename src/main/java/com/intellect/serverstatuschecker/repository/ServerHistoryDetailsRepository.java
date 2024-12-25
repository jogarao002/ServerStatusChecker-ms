package com.intellect.serverstatuschecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intellect.serverstatuschecker.domain.ServerHistoryDetails;

@SuppressWarnings("unused")
@Repository
public interface ServerHistoryDetailsRepository extends JpaRepository<ServerHistoryDetails, Long> {

}
