package com.intellect.serverstatuschecker.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "server_history_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServerHistoryDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "host_name")
    private String hostName;

    @Column(name = "service_name")
    private String serviceName;
    
    @Column(name = "server_protocol_type")
    private String serverProtocolType;

    @Column(name = "server_ip_address")
    private String serverIpAddress;

    @Column(name = "server_port")
    private String serverPort;

    @Column(name = "server_status")
    private Boolean serverStatus;
    
    @Column(name = "server_date")
	private LocalDate serverDate;

	@Column(name = "server_time")
	private LocalTime serverTime;
	
    @Column(name = "server_status_name")
    private String serverStatusName;

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

	
	public String getServerStatusName() {
		return serverStatusName;
	}

	public void setServerStatusName(String serverStatusName) {
		this.serverStatusName = serverStatusName;
	}

	@Override
	public String toString() {
		return "ServerHistoryDetails [id=" + id + ", hostName=" + hostName + ", serviceName=" + serviceName
				+ ", serverProtocolType=" + serverProtocolType + ", serverIpAddress=" + serverIpAddress
				+ ", serverPort=" + serverPort + ", serverStatus=" + serverStatus + ", serverDate=" + serverDate
				+ ", serverTime=" + serverTime + ", serverStatusName=" + serverStatusName + "]";
	}

	
}
