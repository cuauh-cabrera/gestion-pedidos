package com.test.pedidos.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoContentException extends Exception{
	private String mensaje;
	private int codigo;
	private String Error;
	
	public NoContentException() {}
	
	public NoContentException(String Error) {
		super(Error,null,true,false);
	}
}
