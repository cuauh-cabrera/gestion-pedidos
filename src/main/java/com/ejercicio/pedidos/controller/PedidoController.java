package com.ejercicio.pedidos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.pedidos.exceptions.NoContentException;
import com.ejercicio.pedidos.exceptions.NotFoundException;
import com.ejercicio.pedidos.model.PedidoDTO;
import com.ejercicio.pedidos.model.PedidoResponse;
import com.ejercicio.pedidos.model.PedidoResponseSave;
import com.ejercicio.pedidos.service.impl.PedidoService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class PedidoController {
	
	private final PedidoService pedidoService;

	
	private PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

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
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/pedidos")
	public PedidoResponseSave insert(@RequestBody @Valid PedidoDTO pedidoDTO) {
		return pedidoService.insert(pedidoDTO);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping("/pedidos/{id}")
	public PedidoResponseSave update(@PathVariable Long id, @RequestBody @Valid PedidoDTO pedidoDTO) throws NotFoundException {
		pedidoDTO.setId(id);
		return pedidoService.update(id, pedidoDTO);
	}

}
