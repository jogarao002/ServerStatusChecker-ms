package com.intellect.serverstatuschecker.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intellect.serverstatuschecker.exception.ServerDetailsBusinessException;
import com.intellect.serverstatuschecker.service.ServerDetailsService;
import com.intellect.serverstatuschecker.service.dto.ServerDetailsDTO;
import com.intellect.serverstatuschecker.service.dto.ServerMonitorDetailsDTO;
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
	public ResponseObject createAgentAnalyzer(@RequestHeader(required = true) Long userid,
			@RequestHeader(required = true) String authToken, @RequestBody ServerDetailsDTO serverDetailsDTO){
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
	
	
	@GetMapping("/get_all")
	public ResponseObject getAllAgentAnalyzers(@RequestHeader(required = true) Long userid,
			@RequestHeader(required = true) String authToken) {
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
