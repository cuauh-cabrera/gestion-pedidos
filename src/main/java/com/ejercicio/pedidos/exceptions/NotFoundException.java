package com.ejercicio.pedidos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception{
	private String mensaje;
	private String Error;
	
	public NotFoundException() {}
	
	public NotFoundException(String Error) {
		super(Error,null,true,false);
	}

}
