package com.poa.tp.servicios;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomErrorService {
	
	public ResponseEntity<Object> send(HttpStatus status, String msg){
		CustomError error = new CustomError(LocalDateTime.now(),status.value(),msg,status.getReasonPhrase());
		return ResponseEntity.status(status).body(error);
	}
	
}
