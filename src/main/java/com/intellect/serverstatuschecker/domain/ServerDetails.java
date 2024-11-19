package com.intellect.serverstatuschecker.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Region.
 */
@Entity
@Table(name = "server_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServerDetails implements Serializable {

    private static final long serialVersionUID = 1L;

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
    
    
    
    


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
		ServerDetails other = (ServerDetails) obj;
		return Objects.equals(id, other.id) && Objects.equals(serverIpAddress, other.serverIpAddress)
				&& Objects.equals(hostName, other.hostName) && Objects.equals(serverPort, other.serverPort)
				&& Objects.equals(serverProtocolType, other.serverProtocolType)
				&& Objects.equals(serviceName, other.serviceName)
				&& Objects.equals(serverStatus, other.serverStatus);
	}

	@Override
	public String toString() {
		return "ServerDetails [id=" + id + ", hostName=" + hostName + ", serverProtocolType=" + serverProtocolType
				+ ", serverIpAddress=" + serverIpAddress + ", serverPort=" + serverPort + ", serverStatus="
				+ serverStatus + ", serviceName=" + serviceName + "]";
	}

}
