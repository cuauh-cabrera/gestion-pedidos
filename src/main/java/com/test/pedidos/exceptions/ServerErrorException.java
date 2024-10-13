package com.test.pedidos.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerErrorException extends RuntimeException{
	private String mensaje;
	private int codigo;
	private String Error;
	
	public ServerErrorException() {}
	
	public ServerErrorException(String Error) {
		super(Error,null,true,false);
	}

}
