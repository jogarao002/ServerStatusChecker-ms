package com.intellect.serverstatuschecker.util;

import java.util.ArrayList;
import java.util.List;

public class ResponseObject {
	
	private String status;

	private String statusMsg;

	private List<?> data = new ArrayList<>();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseObject [status=" + status + ", statusMsg=" + statusMsg + ", data=" + data + "]";
	}

}
