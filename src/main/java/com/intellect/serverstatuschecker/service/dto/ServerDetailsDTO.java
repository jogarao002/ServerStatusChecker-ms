package com.intellect.serverstatuschecker.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class ServerDetailsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String hostName;

	private String serverProtocolType;

	private String serverIpAddress;

	private String serverPort;

	private Boolean serverStatus;
	
	private String serviceName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, serverIpAddress, hostName, serverPort, serverProtocolType, serverStatus,serviceName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerDetailsDTO other = (ServerDetailsDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(serverIpAddress, other.serverIpAddress)
				&& Objects.equals(hostName, other.hostName) && Objects.equals(serverPort, other.serverPort)
				&& Objects.equals(serverProtocolType, other.serverProtocolType)
				&& Objects.equals(serviceName, other.serviceName)
				&& Objects.equals(serverStatus, other.serverStatus);
	}

	@Override
	public String toString() {
		return "ServerDetailsDTO [id=" + id + ", hostName=" + hostName + ", serverProtocolType=" + serverProtocolType
				+ ", serverIpAddress=" + serverIpAddress + ", serverPort=" + serverPort + ", serverStatus="
				+ serverStatus + ", serviceName=" + serviceName + "]";
	}

}
