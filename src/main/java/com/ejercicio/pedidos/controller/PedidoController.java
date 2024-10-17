package com.ejercicio.pedidos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.pedidos.exceptions.NoContentException;
import com.ejercicio.pedidos.exceptions.NotFoundException;
import com.ejercicio.pedidos.model.PedidoResponse;
import com.ejercicio.pedidos.service.impl.PedidoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PedidoController {
	
	private final PedidoService pedidoService;

	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/pedidos")
	public PedidoResponse readAll() throws NoContentException {
		return pedidoService.readAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/pedidos/{id}")
	public PedidoResponse readById(@PathVariable Long id) throws NotFoundException {
		return pedidoService.readById(id);
	}

}
