package com.ejercicio.pedidos.service;

import com.ejercicio.pedidos.exceptions.NoContentException;
import com.ejercicio.pedidos.exceptions.NotFoundException;
import com.ejercicio.pedidos.model.ClienteDTO;
import com.ejercicio.pedidos.model.ClienteResponse;
import com.ejercicio.pedidos.model.ClienteResponseSave;

public interface IClienteService {
	public ClienteResponse readAll() throws NoContentException;
	
	public ClienteResponse readById(Long id) throws NotFoundException;
	
	public ClienteResponseSave insert(ClienteDTO clienteDTO);
	
	public ClienteResponseSave update(Long id, ClienteDTO clienteDTO) throws NotFoundException;
	
	public ClienteResponseSave deleteById(Long id) throws NotFoundException;
	
	public ClienteResponse findByEmail(String email) throws NotFoundException; 
	
	
	

}
