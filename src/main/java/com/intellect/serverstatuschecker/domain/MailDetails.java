package com.intellect.serverstatuschecker.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mail_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MailDetails implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;
	
	@Column(name = "person_name")
	private String personName;
	
	@Column(name = "person_mail_address")
	private String personMailAddress;
	
	@Column(name = "person_priority")
	private Integer personPriority;
	
	@Column(name = "status")
	private Boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonMailAddress() {
		return personMailAddress;
	}

	public void setPersonMailAddress(String personMailAddress) {
		this.personMailAddress = personMailAddress;
	}

	public Integer getPersonPriority() {
		return personPriority;
	}

	public void setPersonPriority(Integer personPriority) {
		this.personPriority = personPriority;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, personMailAddress, personName, personPriority, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailDetails other = (MailDetails) obj;
		return Objects.equals(id, other.id) && Objects.equals(personMailAddress, other.personMailAddress)
				&& Objects.equals(personName, other.personName) && Objects.equals(personPriority, other.personPriority)
				&& Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "MailDetails [id=" + id + ", personName=" + personName + ", personMailAddress=" + personMailAddress
				+ ", personPriority=" + personPriority + ", status=" + status + "]";
	}
	
}
