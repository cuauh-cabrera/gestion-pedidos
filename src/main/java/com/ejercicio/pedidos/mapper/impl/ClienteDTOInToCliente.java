package com.ejercicio.pedidos.mapper.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.ejercicio.pedidos.entity.Cliente;
import com.ejercicio.pedidos.mapper.IMapper;
import com.ejercicio.pedidos.model.ClienteDTO;

@Component
public class ClienteDTOInToCliente implements IMapper<ClienteDTO, Cliente>{

	@Override
	public Cliente map(ClienteDTO clienteDTO) {
		Cliente cliente = new Cliente();
		if (clienteDTO.getId()!=null) {
			cliente.setId(clienteDTO.getId());
		}
		cliente.setId(cliente.getId());
		cliente.setNombreCliente(clienteDTO.getNombreCliente());
		cliente.setApellidoPaterno(clienteDTO.getApellidoPaterno());
		cliente.setApellidoMaterno(clienteDTO.getApellidoMaterno());
		cliente.setEmailUsuario(clienteDTO.getEmailUsuario());
		cliente.setDireccionEnvio(clienteDTO.getDireccionEnvio());
		if (cliente.getFechaCreacion()==null) {
			cliente.setFechaCreacion(LocalDate.now());
		}
		cliente.setFechaModificacion(LocalDate.now());
		cliente.setIsActive(true);
		return cliente;
	}
	
	

}


