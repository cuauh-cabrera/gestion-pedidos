package com.ejercicio.pedidos.service;

import java.time.LocalDate;

import com.ejercicio.pedidos.exceptions.NoContentException;
import com.ejercicio.pedidos.exceptions.NotFoundException;
import com.ejercicio.pedidos.model.PedidoDTO;
import com.ejercicio.pedidos.model.PedidoResponse;
import com.ejercicio.pedidos.model.PedidoResponseSave;

public interface IPedidoService {
	public PedidoResponse readAll() throws NoContentException;
	
	public PedidoResponse readById(Long id) throws NotFoundException;
	
	public PedidoResponseSave insert(PedidoDTO pedidoDTO);
	
	public PedidoResponseSave update(Long id, PedidoDTO pedidoDTO) throws NotFoundException;
	
	public PedidoResponseSave deleteById(Long id) throws NotFoundException;
	
	public PedidoResponse readByIdCliente(Long idCliente) throws NotFoundException;
	
	public PedidoResponse readByemailCliente(String emailCliente) throws NotFoundException;
	
	public PedidoResponse readByFechaCreacion(LocalDate fechaCreacion) throws NotFoundException;

}
