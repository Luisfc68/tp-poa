package com.poa.tp.servicios;

import java.time.LocalDateTime;

public class CustomError {
	
	private LocalDateTime timestamp;
    private int status;
    private String msg;
    private String error;
    
	public CustomError(LocalDateTime timestamp, int status, String msg, String error) {
		this.timestamp = timestamp;
		this.status = status;
		this.msg = msg;
		this.error = error;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
