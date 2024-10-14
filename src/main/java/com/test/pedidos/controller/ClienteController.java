package com.test.pedidos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.pedidos.exceptions.NoContentException;
import com.test.pedidos.exceptions.NotFoundException;
import com.test.pedidos.model.ClienteDTO;
import com.test.pedidos.model.ClienteResponse;
import com.test.pedidos.model.ClienteResponseSave;
import com.test.pedidos.service.IClienteService;

import jakarta.validation.Valid;

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
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/clientes/{id}")
	public ClienteResponse readById(@PathVariable Long id) throws NotFoundException{
		return clienteService.readById(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/usuarios")
	public ClienteResponseSave insert(@RequestBody @Valid ClienteDTO clienteDTO) {
		return clienteService.insert(clienteDTO);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping("/usuarios/{id}")
	public ClienteResponseSave update(@PathVariable Long id, @RequestBody @Valid ClienteDTO clienteDTO) throws NotFoundException {
		clienteDTO.setId(id);
		return clienteService.update(id, clienteDTO);
	} 
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/usuarios/{id}")
	public ClienteResponseSave deleteById(@PathVariable Long id) throws NotFoundException {
		return clienteService.deleteById(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/usuarios/email")
	public ClienteResponse findByEmail(@RequestParam @Valid String email) throws NotFoundException {
		return clienteService.findByEmail(email);
	}

}
