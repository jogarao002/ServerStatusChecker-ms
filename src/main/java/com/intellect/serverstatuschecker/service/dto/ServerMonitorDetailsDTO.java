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
    
    private String serverName;

    private String serverProtocolType;

    private String serverIpAddress;

    private String serverPort;
    
    private Boolean serverStatus;

    private String serverStatusName;
    
    private Integer inactiveCount;

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

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, inactiveCount, serverDate, serverIpAddress, serverName, serverPort, serverProtocolType,
				serverStatus, serverStatusName, serverTime);
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
				&& Objects.equals(serverName, other.serverName) && Objects.equals(serverPort, other.serverPort)
				&& Objects.equals(serverProtocolType, other.serverProtocolType)
				&& Objects.equals(serverStatus, other.serverStatus)
				&& Objects.equals(serverStatusName, other.serverStatusName)
				&& Objects.equals(serverTime, other.serverTime);
	}

	@Override
	public String toString() {
		return "ServerMonitorDetailsDTO [id=" + id + ", serverDate=" + serverDate + ", serverTime=" + serverTime
				+ ", serverName=" + serverName + ", serverProtocolType=" + serverProtocolType + ", serverIpAddress="
				+ serverIpAddress + ", serverPort=" + serverPort + ", serverStatus=" + serverStatus
				+ ", serverStatusName=" + serverStatusName + ", inactiveCount=" + inactiveCount + "]";
	}
    
}
