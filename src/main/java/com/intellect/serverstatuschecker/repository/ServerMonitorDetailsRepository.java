package com.intellect.serverstatuschecker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intellect.serverstatuschecker.domain.ServerMonitorDetails;

public interface ServerMonitorDetailsRepository extends JpaRepository<ServerMonitorDetails, Long> {

	ServerMonitorDetails findByServerIpAddressAndServerPort(String serverIpAddress, String serverPort);

	List<ServerMonitorDetails> findByServerStatus(Boolean status);

}
