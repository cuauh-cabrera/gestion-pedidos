package com.ejercicio.pedidos.mapper.impl;

import org.springframework.stereotype.Component;

import com.ejercicio.pedidos.entity.Cliente;
import com.ejercicio.pedidos.mapper.IMapper;
import com.ejercicio.pedidos.model.ClienteDTO;

@Component
public class ClienteInToClienteDTO implements IMapper<Cliente, ClienteDTO>{

	@Override
	public ClienteDTO map(Cliente cliente) {
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(cliente.getId());
		clienteDTO.setNombreCliente(cliente.getNombreCliente());
		clienteDTO.setApellidoPaterno(cliente.getApellidoPaterno());
		clienteDTO.setApellidoMaterno(cliente.getApellidoMaterno());
		clienteDTO.setEmailUsuario(cliente.getEmailUsuario());
		clienteDTO.setDireccionEnvio(cliente.getDireccionEnvio());
		return clienteDTO;
	}
	

}
