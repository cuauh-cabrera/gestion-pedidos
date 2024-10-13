package com.test.pedidos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.pedidos.exceptions.NoContentException;
import com.test.pedidos.model.ClienteResponse;
import com.test.pedidos.service.IClienteService;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {
	
	private final IClienteService clienteService;

	public ClienteController(IClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/clientes")
	public ClienteResponse readAll() throws NoContentException {
		return clienteService.readAll();
	}

}
