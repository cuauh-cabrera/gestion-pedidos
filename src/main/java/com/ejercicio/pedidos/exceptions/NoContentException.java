package com.ejercicio.pedidos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentException extends Exception{
	private String mensaje;
	private String Error;
	
	public NoContentException() {}
	
	public NoContentException(String Error) {
		super(Error,null,true,false);
	}
}
