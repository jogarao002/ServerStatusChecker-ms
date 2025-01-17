package com.intellect.serverstatuschecker.service.impl;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.intellect.serverstatuschecker.domain.ServerDetails;
import com.intellect.serverstatuschecker.domain.ServerHistoryDetails;
import com.intellect.serverstatuschecker.domain.ServerMonitorDetails;
import com.intellect.serverstatuschecker.domain.Users;
import com.intellect.serverstatuschecker.exception.ServerDetailsBusinessException;
import com.intellect.serverstatuschecker.repository.ServerDetailsRepository;
import com.intellect.serverstatuschecker.repository.ServerHistoryDetailsRepository;
import com.intellect.serverstatuschecker.repository.ServerMonitorDetailsRepository;
import com.intellect.serverstatuschecker.repository.UserRepository;
import com.intellect.serverstatuschecker.service.ServerDetailsService;
import com.intellect.serverstatuschecker.service.dto.LogInDataDTO;
import com.intellect.serverstatuschecker.service.dto.LoginDTO;
import com.intellect.serverstatuschecker.service.dto.ServerDetailsDTO;
import com.intellect.serverstatuschecker.service.dto.ServerMonitorDetailsDTO;
import com.intellect.serverstatuschecker.service.dto.UsersDTO;
import com.intellect.serverstatuschecker.service.mapper.ServerDetailsMapper;
import com.intellect.serverstatuschecker.service.mapper.ServerMonitorDetailsMapper;
import com.intellect.serverstatuschecker.service.mapper.UsersMapper;
import com.intellect.serverstatuschecker.util.ApplicationConstants;
import com.intellect.serverstatuschecker.util.EmailUtils;

@Service
public class ServerDetailsServiceImpl implements ServerDetailsService {

	@Autowired
	private ServerDetailsRepository serverDetailsRepository;

	@Autowired
	private ServerMonitorDetailsMapper serverMonitorDetailsMapper;

	@Autowired
	private ServerMonitorDetailsRepository serverMonitorDetailsRepository;

	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ServerDetailsMapper serverDetailsMapper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTServiceImpl jwtServiceImpl;

	@Autowired
	private ServerHistoryDetailsRepository serverHistoryDetailsRepository;

	@Autowired
	private Environment env;

	private static final Logger logger = LoggerFactory.getLogger(ServerDetailsServiceImpl.class);

	@Override
	public ServerDetailsDTO save(ServerDetailsDTO serverDetailsDTO) throws ServerDetailsBusinessException {
		if (null != serverDetailsDTO) {
			duplicateCheck(serverDetailsDTO);
			if (null != serverDetailsDTO.getId()) {
				Optional<ServerDetails> optServerDetails = serverDetailsRepository.findById(serverDetailsDTO.getId());
				if (optServerDetails.isPresent()) {
					ServerDetails serverDetails = optServerDetails.get();
					serverDetailsDTO.setServerIpAddress(serverDetails.getServerIpAddress());
					serverDetailsDTO.setServerPort(serverDetails.getServerPort());
					serverDetailsDTO.setServerStatus(ApplicationConstants.TRUE);
				}
			}
			serverDetailsRepository.save(serverDetailsMapper.toEntity(serverDetailsDTO));
		}
		return serverDetailsDTO;
	}

	private void duplicateCheck(ServerDetailsDTO serverDetailsDTO) throws ServerDetailsBusinessException {
		ServerDetails serverDetails = serverDetailsRepository.findByServerIpAddressAndServerPortAndServerStatus(
				serverDetailsDTO.getServerIpAddress(), serverDetailsDTO.getServerPort(), ApplicationConstants.TRUE);

		if (null != serverDetails && null != serverDetails.getId()) {
			if (!serverDetails.getId().equals(serverDetailsDTO.getId())) {
				String errorMessage = String.format(ApplicationConstants.SERVER_DETAILS_ALREADY_EXISTED,
						serverDetailsDTO.getServerIpAddress(), serverDetailsDTO.getServerPort());
				throw new ServerDetailsBusinessException(errorMessage);
			}
		}
	}

	@Override
	public List<ServerMonitorDetailsDTO> findAll() throws ServerDetailsBusinessException {
		List<ServerMonitorDetailsDTO> serverMonitorDetailsDTOList = serverMonitorDetailsMapper
				.toDto(serverMonitorDetailsRepository.findAll());
		return serverMonitorDetailsDTOList;
	}

	@Scheduled(cron = "0 0/15 * * * ?")
	public void serverStatusMonitor() throws ServerDetailsBusinessException {
		logger.info("Server status monitor job started.");
		List<ServerDetails> serverDetailsList = serverDetailsRepository.findByServerStatus(ApplicationConstants.TRUE);
		if (null != serverDetailsList && !serverDetailsList.isEmpty()) {
			logger.info("Found {} active servers to monitor.", serverDetailsList.size());
			for (ServerDetails serverDetails : serverDetailsList) {
				logger.debug("Checking server status for IP: {} and Port: {}", serverDetails.getServerIpAddress(),
						serverDetails.getServerPort());
				findServerStatus(serverDetails);
			}
		} else {
			logger.info("No active servers found for monitoring.");
		}
		List<ServerMonitorDetails> serverMonitorDetailsList = serverMonitorDetailsRepository
				.findByServerStatus(ApplicationConstants.FALSE);
		// This list for vinod (If the inactive count is less than 3)
		List<ServerMonitorDetails> firstPriorityServerMonitorDetails = new ArrayList<ServerMonitorDetails>();
		// This list for balakrishna and vinod (if the inactive count is greater than 3
		// or 3)
		List<ServerMonitorDetails> secondPriorityServerMonitorDetails = new ArrayList<ServerMonitorDetails>();
		if (null != serverMonitorDetailsList && !serverMonitorDetailsList.isEmpty()) {
			for (ServerMonitorDetails serverMonitorDetails : serverMonitorDetailsList) {
				if (null != serverMonitorDetails.getInactiveCount() && serverMonitorDetails.getInactiveCount() < 3)
					firstPriorityServerMonitorDetails.add(serverMonitorDetails);
				else
					secondPriorityServerMonitorDetails.add(serverMonitorDetails);
			}
		}

		logger.info("First priority server monitor details count: {}", firstPriorityServerMonitorDetails.size());
		logger.info("Second priority server monitor details count: {}", secondPriorityServerMonitorDetails.size());

		if (null != firstPriorityServerMonitorDetails && !firstPriorityServerMonitorDetails.isEmpty()) {
			Integer inactiveCount = firstPriorityServerMonitorDetails.get(0).getInactiveCount();
			List<String> firstPriorityToMailId = new ArrayList<>();
			List<String> firstPriorityCCMailId = new ArrayList<>();
			firstPriorityToMailId.add("vinod.ogirala@intellectinfo.com");
			firstPriorityCCMailId.add("mokshasri.venkatesh@intellectinfo.com");
			if (inactiveCount < 3) {
				logger.info("Sending email for first priority servers (inactive count < 3).");
				sendsMail(firstPriorityServerMonitorDetails, firstPriorityToMailId, firstPriorityCCMailId);
			}

		} else {
			logger.info("No first priority servers to send email for.");
		}
		if (null != secondPriorityServerMonitorDetails && !secondPriorityServerMonitorDetails.isEmpty()) {
			Integer inactiveCount = secondPriorityServerMonitorDetails.get(0).getInactiveCount();
			List<String> secondPriorityToMailId = new ArrayList<>();
			List<String> secondPriorityCCMailId = new ArrayList<>();
			secondPriorityToMailId.add("bala@intellectinfo.com");
			secondPriorityCCMailId.add("vinod.ogirala@intellectinfo.com");
			secondPriorityCCMailId.add("mokshasri.venkatesh@intellectinfo.com");
			if (inactiveCount >= 3) {
				logger.info("Sending email for second priority servers (inactive count >= 3).");
				sendsMail(secondPriorityServerMonitorDetails, secondPriorityToMailId, secondPriorityCCMailId);
			}
		} else {
			logger.info("No second priority servers to send email for.");
		}

		logger.info("Server status monitor job completed.");
	}

	private void findServerStatus(ServerDetails serverDetails) throws ServerDetailsBusinessException {
		LocalTime localTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalTime();
		ServerMonitorDetails serverMonitorDetails = new ServerMonitorDetails();
		if (null != serverDetails.getServerIpAddress() && null != serverDetails.getServerPort()) {
			Boolean serverReachableStatus = isServerReachable(serverDetails.getServerIpAddress(),
					Integer.parseInt(serverDetails.getServerPort()));

			// Check for existing record with same IP and port, and update or insert as
			// necessary
			serverMonitorDetails = serverMonitorDetailsRepository.findByServerIpAddressAndServerPort(
					serverDetails.getServerIpAddress(), serverDetails.getServerPort());
			// If already existed
			if (null != serverMonitorDetails) {
				serverMonitorDetails.setServerDate(LocalDate.now());
				serverMonitorDetails.setServerTime(localTime);
				serverMonitorDetails.setId(serverDetails.getId());
			} else {
				// Otherwise, prepare a new record
				serverMonitorDetails = new ServerMonitorDetails();
				serverMonitorDetails.setServerDate(LocalDate.now());
				serverMonitorDetails.setServerTime(localTime);
				serverMonitorDetails.setHostName(serverDetails.getHostName());
				serverMonitorDetails.setServerProtocolType(serverDetails.getServerProtocolType());
				serverMonitorDetails.setServerIpAddress(serverDetails.getServerIpAddress());
				serverMonitorDetails.setServerPort(serverDetails.getServerPort());
				serverMonitorDetails.setServiceName(serverDetails.getServiceName());
			}

			serverMonitorDetails.setServerStatus(serverReachableStatus);
			if (serverReachableStatus) {
				serverMonitorDetails.setServerStatusName(ApplicationConstants.ACTIVE_STATUS);
				serverMonitorDetails.setInactiveCount(0); // Reset inactive count when the server is active
			} else {
				serverMonitorDetails.setServerStatusName(ApplicationConstants.IN_ACTIVE_STATUS);
				Optional<ServerMonitorDetails> optServerMonitorDetails = serverMonitorDetailsRepository
						.findById(serverDetails.getId());
				if (optServerMonitorDetails.isPresent() && null != optServerMonitorDetails.get().getInactiveCount()) {
					int inactiveCount = optServerMonitorDetails.get().getInactiveCount().intValue() + 1;
					serverMonitorDetails.setInactiveCount(inactiveCount);
				} else {
					serverMonitorDetails.setInactiveCount(1);
				}
			}
			ServerMonitorDetails monitor = serverMonitorDetailsRepository.save(serverMonitorDetails);
			ServerHistoryDetails history = new ServerHistoryDetails();
			history.setHostName(monitor.getHostName());
			history.setServerProtocolType(monitor.getServerProtocolType());
			history.setServerIpAddress(monitor.getServerIpAddress());
			history.setServerPort(monitor.getServerPort());
			history.setServiceName(monitor.getServiceName());
			history.setServerDate(monitor.getServerDate());
			history.setServerTime(monitor.getServerTime());
			history.setServerStatus(monitor.getServerStatus());
			history.setServerStatusName(monitor.getServerStatusName());

			serverHistoryDetailsRepository.save(history);

		}
	}

	private Boolean isServerReachable(String ipAddress, Integer port) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(ipAddress, port), 2000); // 2-second timeout
			return true; // If connection is successful
		} catch (Exception e) {
			return false; // If there's any exception, the server is not reachable
		}
	}

	@Override
	public UsersDTO register(UsersDTO usersDTO) throws ServerDetailsBusinessException {
		if (null != usersDTO) {
			duplicateCheckForExistingUser(usersDTO);
			Users users = usersMapper.toEntity(usersDTO);
			users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
			userRepository.save(users);
		}
		return usersDTO;
	}

	private void duplicateCheckForExistingUser(UsersDTO usersDTO) throws ServerDetailsBusinessException {
		Users users = userRepository.findByEmail(usersDTO.getEmail());

		if (null != users && null != users.getEmail()) {
			if (users.getEmail().equals(usersDTO.getEmail())) {
				String errorMessage = String.format(ApplicationConstants.USER_EMAIL_IS_ALREADY_EXISTED,
						usersDTO.getEmail());
				throw new ServerDetailsBusinessException(errorMessage);
			}
		}
	}

	@Override
	public LogInDataDTO login(LoginDTO loginDTO) throws ServerDetailsBusinessException {
		try {
			// Authenticate the user
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDTO.getLoginUserName(), loginDTO.getLoginPassword()));

			// If authentication is successful
			if (authentication.isAuthenticated()) {
				// After successful authentication, you may want to create a token
				String token = jwtServiceImpl.generateToken(loginDTO.getLoginUserName());
				LogInDataDTO logInDataDTO = new LogInDataDTO();
				logInDataDTO.setUserName(loginDTO.getLoginUserName());
				logInDataDTO.setToken(token);
				Users user = userRepository.findByEmail(loginDTO.getLoginUserName());
				logInDataDTO.setUserRole(user.getUserRole());
				String expirationToken = jwtServiceImpl.generateExpirationToken();
				logInDataDTO.setExpirationToken(expirationToken);

				return logInDataDTO;
			} else {
				throw new ServerDetailsBusinessException(ApplicationConstants.AUTHENTICATION_FAILED);
			}

		} catch (BadCredentialsException e) {
			// Handle incorrect credentials
			throw new ServerDetailsBusinessException(ApplicationConstants.INVAILD_USERNAME_OR_PASSWORD);
		} catch (Exception e) {
			// Handle other unexpected errors
			throw new ServerDetailsBusinessException(ApplicationConstants.AN_ERROR_OCCURED_DURING_AUTHENTICATION);
		}
	}

	@Override
	public List<ServerMonitorDetailsDTO> delete(Long id) throws ServerDetailsBusinessException {
		List<ServerMonitorDetailsDTO> serverMonitorDetailsDTOList = new ArrayList<>();
		if (id != null) {
			Optional<ServerDetails> optServerDetails = serverDetailsRepository.findById(id);
			if (optServerDetails.isPresent()) {
				ServerDetails serverDetails = optServerDetails.get();
				serverDetails.setServerStatus(ApplicationConstants.FALSE);
				serverDetailsRepository.save(serverDetails);
			}
			Optional<ServerMonitorDetails> optServerMonitorDetails = serverMonitorDetailsRepository.findById(id);
			if (optServerMonitorDetails.isPresent()) {
				ServerMonitorDetails serverMonitorDetails = optServerMonitorDetails.get();
				serverMonitorDetailsRepository.delete(serverMonitorDetails);
			}
			serverMonitorDetailsDTOList = findAll();
		}
		return serverMonitorDetailsDTOList;
	}

	@Scheduled(cron = "0 0 1 * * *")
	public void deleteHistoryDetails() {
		logger.info("Deleting server history details job started.");
		try {
			serverHistoryDetailsRepository.deleteAll();
			logger.info("All server history details deleted successfully.");
		} catch (Exception e) {
			logger.error("Error occurred while deleting server history details.", e);
		}
		logger.info("Delete server history details job completed.");
	}

	public void sendsMail(List<ServerMonitorDetails> serverMonitorDetailsList, List<String> toMails,
			List<String> ccMails) {
		String fromEmail = "admin@intellectinfo.com";
		emailUtils.sendServerStatusListMail(serverMonitorDetailsList, ccMails, fromEmail, toMails, env);
		System.out.println("mail sent sucess");
	}

}
