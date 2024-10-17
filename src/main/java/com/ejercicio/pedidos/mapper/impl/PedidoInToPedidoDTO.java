package com.ejercicio.pedidos.mapper.impl;

import org.springframework.stereotype.Component;

import com.ejercicio.pedidos.entity.Pedido;
import com.ejercicio.pedidos.mapper.IMapper;
import com.ejercicio.pedidos.model.PedidoDTO;

@Component
public class PedidoInToPedidoDTO implements IMapper<Pedido, PedidoDTO>{

	@Override
	public PedidoDTO map(Pedido pedido) {
		PedidoDTO pedidoDTO = new PedidoDTO();
		pedidoDTO.setId(pedido.getId());
		pedidoDTO.setCodidoProducto(pedido.getCodidoProducto());
		pedidoDTO.setEmailCliente(pedido.getEmailCliente());
		pedidoDTO.setIdCliente(pedido.getIdCliente());
		pedidoDTO.setCantidad(pedido.getCantidad());
		pedidoDTO.setPrecio(pedido.getPrecio());
		pedidoDTO.setFechaCreacion(pedido.getFechaCreacion());
		return pedidoDTO;
	}
}
