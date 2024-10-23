/**
 * Mapper que se encarga de transformar una entidad {@link Pedido} a un objeto de transferencia de datos {@link PedidoDTO}.
 * Esta implementación maneja específicamente la conversión de la relación entre Pedido y Cliente a través del campo emailCliente.
 *
 * <p>La clase implementa la interfaz {@link IMapper} para mantener una estructura consistente en el mapeo de objetos.
 * 
 * @author Cuau Cabrera
 * @version 1.0
 * @see Pedido
 * @see PedidoDTO
 * @see IMapper
 */

package com.ejercicio.pedidos.mapper.impl;

import org.springframework.stereotype.Component;

import com.ejercicio.pedidos.entity.Pedido;
import com.ejercicio.pedidos.mapper.IMapper;
import com.ejercicio.pedidos.model.PedidoDTO;

@Component
public class PedidoInToPedidoDTO implements IMapper<Pedido, PedidoDTO>{
	
/**
  * Convierte una entidad Pedido en su correspondiente DTO, incluyendo la relación con Cliente.
  * 
  * <p>Este método realiza las siguientes transformaciones:
  * <ul>
  *   <li>Mapea los campos básicos del pedido (id, codigoProducto, cantidad, precio, etc.)</li>
  *   <li>Mapea la relación con Cliente a través del campo emailCliente</li>
  *   <li>Persiste la fecha de creación original del pedido</li>
  * </ul>
  *
  * @param pedido la entidad {@link Pedido} que será convertida a DTO
  * @return un objeto {@link PedidoDTO} con todos los campos mapeados desde la entidad
  * @throws NullPointerException si el parámetro pedido es null
  */
	
	private final ClienteInToClienteDTO clienteMapper;

	private PedidoInToPedidoDTO(ClienteInToClienteDTO clienteMapper) {
		super();
		this.clienteMapper = clienteMapper;
	}

	@Override
	public PedidoDTO map(Pedido pedido) {
		PedidoDTO pedidoDTO = new PedidoDTO();
		pedidoDTO.setId(pedido.getId());
		pedidoDTO.setCodidoProducto(pedido.getCodidoProducto());
		
		if (pedido.getIdCliente()!=null) {
			pedidoDTO.setIdCliente(clienteMapper.map(pedido.getIdCliente()));

		}

		pedidoDTO.setCantidad(pedido.getCantidad());
		pedidoDTO.setPrecio(pedido.getPrecio());
		pedidoDTO.setFechaCreacion(pedido.getFechaCreacion());
		return pedidoDTO;
	}
}
