package com.intellect.serverstatuschecker.service;

import java.util.List;

import com.intellect.serverstatuschecker.exception.ServerDetailsBusinessException;
import com.intellect.serverstatuschecker.service.dto.LogInDataDTO;
import com.intellect.serverstatuschecker.service.dto.LoginDTO;
import com.intellect.serverstatuschecker.service.dto.ServerDetailsDTO;
import com.intellect.serverstatuschecker.service.dto.ServerMonitorDetailsDTO;
import com.intellect.serverstatuschecker.service.dto.UsersDTO;

import jakarta.mail.MessagingException;

/**
 * Service Interface for managing
 * {@link com.intellect.demo.domain.AgentAnalyzer}.
 */

public interface ServerDetailsService {
	
	ServerDetailsDTO save(ServerDetailsDTO serverDetailsDTO)throws ServerDetailsBusinessException;
	
	List<ServerMonitorDetailsDTO> findAll() throws ServerDetailsBusinessException, MessagingException;

	UsersDTO register(UsersDTO userDTO) throws ServerDetailsBusinessException;

	LogInDataDTO login(LoginDTO loginDTO) throws ServerDetailsBusinessException;

	
}
