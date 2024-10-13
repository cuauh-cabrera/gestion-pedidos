package com.test.pedidos.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends Exception{
	private String mensaje;
	private int codigo;
	private String Error;
	
	public NotFoundException() {}
	
	public NotFoundException(String Error) {
		super(Error,null,true,false);
	}

}
