package com.test.pedidos.service;

import com.test.pedidos.exceptions.NoContentException;
import com.test.pedidos.exceptions.NotFoundException;
import com.test.pedidos.model.ClienteDTO;
import com.test.pedidos.model.ClienteResponse;
import com.test.pedidos.model.ClienteResponseSave;

public interface IClienteService {
	public ClienteResponse readAll() throws NoContentException;
	
	public ClienteResponse readById(Long id) throws NotFoundException;
	
	public ClienteResponseSave insert(ClienteDTO clienteDTO);
	
	public ClienteResponseSave update(Long id, ClienteDTO clienteDTO) throws NotFoundException;
	
	public ClienteResponseSave deleteById(Long id) throws NotFoundException;
	
	public ClienteResponse findByEmail(String email); 
	
	
	

}
