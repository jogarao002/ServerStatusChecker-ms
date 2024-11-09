package com.intellect.serverstatuschecker.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "server_monitor_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServerMonitorDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;
	
	@Column(name = "server_date")
    private LocalDate serverDate;
	
	@Column(name = "server_time")
    private LocalTime serverTime;
    
	@Column(name = "server_name")
    private String serverName;

    @Column(name = "server_protocol_type")
    private String serverProtocolType;

    @Column(name = "server_ip_address")
    private String serverIpAddress;

    @Column(name = "server_port")
    private String serverPort;
    
    @Column(name = "server-status")
    private Boolean serverStatus;

    @Column(name = "server_status_name")
    private String serverStatusName;
    
    @Column(name = "inactive_count")
    private Integer inactiveCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalTime getServerTime() {
		return serverTime;
	}

	public void setServerTime(LocalTime serverTime) {
		this.serverTime = serverTime;
	}

	public LocalDate getServerDate() {
		return serverDate;
	}

	public void setServerDate(LocalDate serverDate) {
		this.serverDate = serverDate;
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

	public String getServerStatusName() {
		return serverStatusName;
	}

	public void setServerStatusName(String serverStatusName) {
		this.serverStatusName = serverStatusName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(Boolean serverStatus) {
		this.serverStatus = serverStatus;
	}

	public Integer getInactiveCount() {
		return inactiveCount;
	}

	public void setInactiveCount(Integer inactiveCount) {
		this.inactiveCount = inactiveCount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, serverDate, serverIpAddress, serverName, serverPort, serverProtocolType,
				serverStatusName, serverTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerMonitorDetails other = (ServerMonitorDetails) obj;
		return Objects.equals(id, other.id) && Objects.equals(serverDate, other.serverDate)
				&& Objects.equals(serverIpAddress, other.serverIpAddress)
				&& Objects.equals(serverName, other.serverName) && Objects.equals(serverPort, other.serverPort)
				&& Objects.equals(serverProtocolType, other.serverProtocolType)
				&& Objects.equals(serverStatusName, other.serverStatusName)
				&& Objects.equals(serverTime, other.serverTime);
	}

	@Override
	public String toString() {
		return "ServerMonitorDetails [id=" + id + ", serverDate=" + serverDate + ", serverTime=" + serverTime
				+ ", serverName=" + serverName + ", serverProtocolType=" + serverProtocolType + ", serverIpAddress="
				+ serverIpAddress + ", serverPort=" + serverPort + ", serverStatus=" + serverStatus
				+ ", serverStatusName=" + serverStatusName + ", inactiveCount=" + inactiveCount + "]";
	}

}
