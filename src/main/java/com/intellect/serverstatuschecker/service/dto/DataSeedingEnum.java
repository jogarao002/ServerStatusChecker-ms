package com.intellect.serverstatuschecker.service.dto;

public enum DataSeedingEnum {
	SERVER_NAME("server_name"), SERVER_PROTOCOL_TYPE("server_protocol_type"), SERVER_IP_ADDRESS("server_ip_address"),
	SERVER_PORT("server_port"), SERVER_STATUS("server_status"),
	
	PERSON_NAME("person_name"), PERSON_MAIL_ID("person_mail_id"), PERSON_PRIORITY("person_priority"),
	STATUS("status");

	private String columnName;

	private DataSeedingEnum(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
