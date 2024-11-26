package com.intellect.serverstatuschecker.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intellect.serverstatuschecker.exception.ServerDetailsBusinessException;
import com.intellect.serverstatuschecker.service.ServerDetailsService;
import com.intellect.serverstatuschecker.service.dto.LogInDataDTO;
import com.intellect.serverstatuschecker.service.dto.LoginDTO;
import com.intellect.serverstatuschecker.service.dto.ServerDetailsDTO;
import com.intellect.serverstatuschecker.service.dto.ServerMonitorDetailsDTO;
import com.intellect.serverstatuschecker.service.dto.UsersDTO;
import com.intellect.serverstatuschecker.util.ApplicationConstants;
import com.intellect.serverstatuschecker.util.ResponseObject;
import com.intellect.serverstatuschecker.util.ServerDetailsResponseUtil;

@RestController
@RequestMapping("/server_details")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class ServerDetailsResource {
	
	@Autowired
	private ServerDetailsService serverDetailsService;
	
	@PostMapping("/add")
	public ResponseObject createNewServer(@RequestBody ServerDetailsDTO serverDetailsDTO){
		List<ServerDetailsDTO> data = null;
		ServerDetailsDTO result = null;
		try {

			if (serverDetailsDTO.getId() != null) {
				return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_ERROR,
						ApplicationConstants.BAD_REQUEST, null);
			}
			result = serverDetailsService.save(serverDetailsDTO);
			data = new ArrayList<>();
			data.add(result);
		} catch (ServerDetailsBusinessException e) {
			return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_ERROR,
					ApplicationConstants.CREATE_RECORD_FAILED + "," + e.getMessage(), data);
		} catch (Exception e) {
			return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_ERROR,
					ApplicationConstants.SERVER_ERROR, null);
		}
		return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_SUCCESS,
				ApplicationConstants.SERVER_DETAILS + ApplicationConstants.CREATE_RECORD_SUCCESS, data);
	}
	
	@PostMapping("/register")
	public ResponseObject createNewUser(@RequestBody UsersDTO usersDTO){
		List<UsersDTO> data = null;
		UsersDTO result = null;
		try {

			if (usersDTO.getId() != null) {
				return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_ERROR,
						ApplicationConstants.BAD_REQUEST, null);
			}
			result = serverDetailsService.register(usersDTO);
			data = new ArrayList<>();
			data.add(result);
		} catch (ServerDetailsBusinessException e) {
			return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_ERROR,
					ApplicationConstants.REGISTER_FAILED + "," + e.getMessage(), data);
		} catch (Exception e) {
			return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_ERROR,
					ApplicationConstants.SERVER_ERROR, null);
		}
		return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_SUCCESS,
				ApplicationConstants.SERVER_DETAILS + ApplicationConstants.REGISTER_SUCCESS, data);
	}
	
	@PostMapping("/login")
	public ResponseObject createNewUser(@RequestBody LoginDTO loginDTO){
		List<LogInDataDTO> data = null;
		LogInDataDTO result = null;
		try {
			result = serverDetailsService.login(loginDTO);
			data = new ArrayList<>();
			data.add(result);
		} catch (ServerDetailsBusinessException e) {
			return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_ERROR,
					ApplicationConstants.LOGIN_FAILED + "," + e.getMessage(), data);
		} catch (Exception e) {
			return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_ERROR,
					ApplicationConstants.SERVER_ERROR, null);
		}
		return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_SUCCESS,
				ApplicationConstants.SERVER_DETAILS + ApplicationConstants.LOGIN_SUCCESS, data);
	}
	
	@GetMapping("/get_all")
	public ResponseObject getAllServers() {
		List<ServerMonitorDetailsDTO> result = null;
		try {
			System.out.println(".....");
			result = serverDetailsService.findAll();
			System.out.println(".....");
		} catch (ServerDetailsBusinessException e) {
			return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_ERROR,
					ApplicationConstants.FETCH_RECORD_FAILED, result);
		} catch (Exception e) {
			System.out.println(".....");
			return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_ERROR,
					ApplicationConstants.SERVER_ERROR, null);
		}
		return ServerDetailsResponseUtil.buildResponse(ApplicationConstants.RES_STATUS_SUCCESS,
				ApplicationConstants.SERVER_DETAILS + ApplicationConstants.FETCH_RECORD_SUCCESS, result);

	}
}
