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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intellect.serverstatuschecker.domain.MailDetails;
import com.intellect.serverstatuschecker.domain.ServerDetails;
import com.intellect.serverstatuschecker.domain.ServerHistoryDetails;
import com.intellect.serverstatuschecker.domain.ServerMonitorDetails;
import com.intellect.serverstatuschecker.domain.Users;
import com.intellect.serverstatuschecker.exception.ServerDetailsBusinessException;
import com.intellect.serverstatuschecker.repository.MailDetailsRepository;
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

import jakarta.mail.MessagingException;

@Service
@Transactional
public class ServerDetailsServiceImpl implements ServerDetailsService {

	@Autowired
	private ServerDetailsRepository serverDetailsRepository;

	@Autowired
	private ServerMonitorDetailsMapper serverMonitorDetailsMapper;

	@Autowired
	private MailDetailsRepository mailDetailsRepository;

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

	@Override
	public ServerDetailsDTO save(ServerDetailsDTO serverDetailsDTO) throws ServerDetailsBusinessException {
		if(null != serverDetailsDTO) {
			duplicateCheck(serverDetailsDTO);
			if(null != serverDetailsDTO.getId()) {
				Optional<ServerDetails> optServerDetails = serverDetailsRepository.findById(serverDetailsDTO.getId());
				if(optServerDetails.isPresent()) {
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
		List<ServerMonitorDetailsDTO> serverMonitorDetailsDTOList = serverMonitorDetailsMapper.toDto(serverMonitorDetailsRepository.findAll());
		return serverMonitorDetailsDTOList;
	}

	//@Scheduled(cron = "0 0/1 * * * ?")
	public void serverStatusMonitor() throws ServerDetailsBusinessException, MessagingException {
		List<ServerDetails> serverDetailsList = serverDetailsRepository.findByServerStatus(ApplicationConstants.TRUE);
		if (null != serverDetailsList && !serverDetailsList.isEmpty()) {
			for (ServerDetails serverDetails : serverDetailsList) {
				findServerStatus(serverDetails);
			}
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

		if (null != firstPriorityServerMonitorDetails && !firstPriorityServerMonitorDetails.isEmpty()) {
			Integer inactiveCount = firstPriorityServerMonitorDetails.get(1).getInactiveCount();
			List<String> firstPriorityMailId = new ArrayList<>();
			List<String> firstPriorityPersonName = new ArrayList<>();
			if (inactiveCount < 3) {
				MailDetails mailDetails = mailDetailsRepository.findByPersonPriorityAndStatus(ApplicationConstants.ONE,
						ApplicationConstants.TRUE);
				if (null != mailDetails && null != mailDetails.getPersonMailAddress()) {
					firstPriorityMailId.add(mailDetails.getPersonMailAddress());
					firstPriorityPersonName.add(mailDetails.getPersonName()); 
				}
			}
			if (!firstPriorityMailId.isEmpty()) {
				sendEmail(firstPriorityServerMonitorDetails, firstPriorityMailId, firstPriorityPersonName);
			}
		} else if (null != secondPriorityServerMonitorDetails && !secondPriorityServerMonitorDetails.isEmpty()) {
			Integer inactiveCount = secondPriorityServerMonitorDetails.get(1).getInactiveCount();
			List<String> secondPriorityMailIds = new ArrayList<>();
			List<String> secondPriorityPersonNames = new ArrayList<>();
			if (inactiveCount >= 3) {
				List<MailDetails> mailDetailsList = mailDetailsRepository.findByStatus(ApplicationConstants.TRUE);
				secondPriorityMailIds = mailDetailsList.stream().map(MailDetails::getPersonMailAddress)
						.collect(Collectors.toList());
				secondPriorityPersonNames = mailDetailsList.stream().map(MailDetails::getPersonName)
						.collect(Collectors.toList());
			}
			if (!secondPriorityMailIds.isEmpty()) {
				sendEmail(secondPriorityServerMonitorDetails, secondPriorityMailIds, secondPriorityPersonNames);
			}
		}
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

	private Boolean sendEmail(List<ServerMonitorDetails> serverMonitorDetailsList, List<String> mailIdsList,
			List<String> priorityPersonNames) throws MessagingException {
		// Trim all email addresses, remove spaces, and filter out invalid emails
		List<String> validEmails = mailIdsList.stream().map(String::trim) // Remove leading/trailing whitespace
				.filter(this::isValidEmail) // Filter out invalid email formats
				.collect(Collectors.toList());

		if (validEmails.isEmpty()) {
			System.out.println("No valid email addresses found.");
			return false;
		}

		// Convert the list of valid emails to an array
		String[] to = validEmails.toArray(new String[0]);

		// Log the result of the email list
		System.out.println("Email list to send to: " + String.join(", ", to));

		String subject = ApplicationConstants.THESE_SERVERS_DOWN;
		StringBuilder body = new StringBuilder();
		body.append("<h2>Hi ").append(priorityPersonNames).append("</h2>");
		body.append("<br>");
		body.append("<h3>Below servers are down, please look at once.</h3>");
		body.append("<br>");
		body.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse;'>");
		body.append("<thead>");
		body.append("<tr>");
		body.append("<th> host Name</th>");
		body.append("<th>Service Name</th>");
		body.append("<th>Server Ip Address</th>");
		body.append("<th>Server Port</th>");
		body.append("<th>Server Status</th>");
		body.append("</tr>");
		body.append("</thead>");
		body.append("<tbody>");
		for (ServerMonitorDetails server : serverMonitorDetailsList) {
			body.append("<tr>");
			body.append("<td>").append(server.getHostName()).append("</td>");
			body.append("<td>").append(server.getServiceName()).append("</td>");
			body.append("<td>").append(server.getServerIpAddress()).append("</td>");
			body.append("<td>").append(server.getServerPort()).append("</td>");
			body.append("<td>").append(server.getServerStatusName()).append("</td>");
			body.append("</tr>");
		}
		body.append("</tbody>");
		body.append("</table>");
		body.append("<br>");
		body.append("<p>Kind regards,<br>Your Automated Monitoring System</p>");

		// Send email with properly formatted email addresses as an array
		boolean sendEmail = emailUtils.sendEmail(to, subject, body.toString());
		if (sendEmail) {
			System.out.println("Mail sent successfully to: " + String.join(", ", to));
		} else {
			System.out.println("Mail not sent.");
		}
		return true;
	}

	private boolean isValidEmail(String email) {
		// Regular expression to validate the email format properly
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		if (email == null || !email.matches(emailRegex)) {
			System.out.println("Invalid email: " + email); // Optional: log invalid email
			return false;
		}
		return true;
	}

	@Override
	public UsersDTO register(UsersDTO usersDTO) {
		if(null != usersDTO) {
			Users users = usersMapper.toEntity(usersDTO);
			users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
			userRepository.save(users);
		}
		return usersDTO;
	}

	@Override
	public LogInDataDTO login(LoginDTO loginDTO) throws ServerDetailsBusinessException {
	    try {
	        // Authenticate the user
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(loginDTO.getLoginUserName(), loginDTO.getLoginPassword())
	        );

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
	        if(optServerMonitorDetails.isPresent()) {
	        	ServerMonitorDetails serverMonitorDetails = optServerMonitorDetails.get();
	        	serverMonitorDetailsRepository.delete(serverMonitorDetails);
	        }
	        serverMonitorDetailsDTOList = findAll();
	    }
	    return serverMonitorDetailsDTOList;
	}

	//@Scheduled(cron = "59 23 28 1-12 *")
	public void deleteHistoryDetails() {
		serverHistoryDetailsRepository.deleteAll();
		
	}


}
