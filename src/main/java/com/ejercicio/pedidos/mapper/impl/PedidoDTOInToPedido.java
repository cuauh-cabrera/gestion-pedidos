package com.ejercicio.pedidos.mapper.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.ejercicio.pedidos.entity.Pedido;
import com.ejercicio.pedidos.mapper.IMapper;
import com.ejercicio.pedidos.model.PedidoDTO;

@Component
public class PedidoDTOInToPedido implements IMapper<PedidoDTO, Pedido>{

	@Override
	public Pedido map(PedidoDTO pedidoDTO) {
		Pedido pedido = new Pedido();
		if (pedidoDTO.getId() != null) {
			pedido.setId(pedidoDTO.getId());
		}
		pedido.setCodidoProducto(pedidoDTO.getCodidoProducto());
		pedido.setEmailCliente(pedidoDTO.getEmailCliente());
		pedido.setIdCliente(pedidoDTO.getIdCliente());
		pedido.setCantidad(pedidoDTO.getCantidad());
		pedido.setPrecio(pedidoDTO.getPrecio());
		pedido.setFechaCreacion(pedido.getFechaCreacion());
		if (pedido.getFechaCreacion()==null) {
			pedido.setFechaCreacion(LocalDate.now());
		}
		pedido.setFechaModificacion(LocalDate.now());
		pedido.setIsActive(true);
		return pedido;
	}
}
