package com.intellect.serverstatuschecker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intellect.serverstatuschecker.domain.ServerMonitorDetails;

/**
 * Spring Data JPA repository for the Region entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServerMonitorDetailsRepository extends JpaRepository<ServerMonitorDetails, Long> {

	ServerMonitorDetails findByServerIpAddressAndServerPort(String serverIpAddress, String serverPort);

	List<ServerMonitorDetails> findByServerStatus(Boolean status);

}
