package com.intellect.serverstatuschecker.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ServerHistoryDetailsDTO {

    private Long id;

    private String hostName;

    private String serviceName;
    
    private String serverProtocolType;

    private String serverIpAddress;

    private String serverPort;

    private Boolean serverStatus;
    
	private LocalDate serverDate;

	private LocalTime serverTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServerProtocolType() {
		return serverProtocolType;
	}

	public void setServerProtocolType(String serverProtocolType) {
		this.serverProtocolType = serverProtocolType;
	}

	public String getServerIpAddress() {
		return serverIpAddress;
	}

	public void setServerIpAddress(String serverIpAddress) {
		this.serverIpAddress = serverIpAddress;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public Boolean getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(Boolean serverStatus) {
		this.serverStatus = serverStatus;
	}

	public LocalDate getServerDate() {
		return serverDate;
	}

	public void setServerDate(LocalDate serverDate) {
		this.serverDate = serverDate;
	}

	public LocalTime getServerTime() {
		return serverTime;
	}

	public void setServerTime(LocalTime serverTime) {
		this.serverTime = serverTime;
	}

	@Override
	public String toString() {
		return "ServerHistoryDetailsDTO [id=" + id + ", hostName=" + hostName + ", serviceName=" + serviceName
				+ ", serverProtocolType=" + serverProtocolType + ", serverIpAddress=" + serverIpAddress
				+ ", serverPort=" + serverPort + ", serverStatus=" + serverStatus + ", serverDate=" + serverDate
				+ ", serverTime=" + serverTime + "]";
	}
	
	
}
