package com.intellect.serverstatuschecker.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ServerMonitorDetailsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Long id;
	
    private LocalDate serverDate;
	
    private LocalTime serverTime;
    
    private String serviceName;

    private String serverProtocolType;

    private String serverIpAddress;

    private String serverPort;
    
    private Boolean serverStatus;

    private String serverStatusName;
    
    private Integer inactiveCount;
    
    private String hostName;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getServerStatusName() {
		return serverStatusName;
	}

	public void setServerStatusName(String serverStatusName) {
		this.serverStatusName = serverStatusName;
	}

	public Integer getInactiveCount() {
		return inactiveCount;
	}

	public void setInactiveCount(Integer inactiveCount) {
		this.inactiveCount = inactiveCount;
	}

	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, inactiveCount, serverDate, serverIpAddress, serviceName, serverPort, serverProtocolType,
				serverStatus, serverStatusName, serverTime,hostName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerMonitorDetailsDTO other = (ServerMonitorDetailsDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(inactiveCount, other.inactiveCount)
				&& Objects.equals(serverDate, other.serverDate)
				&& Objects.equals(serverIpAddress, other.serverIpAddress)
				&& Objects.equals(serviceName, other.serviceName) && Objects.equals(serverPort, other.serverPort)
				&& Objects.equals(serverProtocolType, other.serverProtocolType)
				&& Objects.equals(serverStatus, other.serverStatus)
				&& Objects.equals(serverStatusName, other.serverStatusName)
				&& Objects.equals(hostName, other.hostName)
				&& Objects.equals(serverTime, other.serverTime);
	}

	@Override
	public String toString() {
		return "ServerMonitorDetailsDTO [id=" + id + ", serverDate=" + serverDate + ", serverTime=" + serverTime
				+ ", serviceName=" + serviceName + ", serverProtocolType=" + serverProtocolType + ", serverIpAddress="
				+ serverIpAddress + ", serverPort=" + serverPort + ", serverStatus=" + serverStatus
				+ ", serverStatusName=" + serverStatusName + ", inactiveCount=" + inactiveCount + ", hostName="
				+ hostName + "]";
	}

    
}
