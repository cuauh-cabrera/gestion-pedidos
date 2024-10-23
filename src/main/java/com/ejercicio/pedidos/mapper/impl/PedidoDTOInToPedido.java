package com.ejercicio.pedidos.mapper.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.ejercicio.pedidos.entity.Pedido;
import com.ejercicio.pedidos.mapper.IMapper;
import com.ejercicio.pedidos.model.PedidoDTO;


/**
 * Mapper responsable de transformar un objeto DTO {@link PedidoDTO} en una entidad {@link Pedido}.
 * Esta implementación gestiona la conversión completa de un pedido, incluyendo la relación con la entidad Cliente
 * y el manejo automático de las fechas de auditoría.
 *
 * <p>La clase implementa la interfaz {@link IMapper} para mantener una estructura consistente 
 * en el mapeo de objetos del dominio.
 * 
 * <p>Características principales:
 * <ul>
 *   <li>Gestiona la relación entre Pedido y Cliente a través del campo emailCliente</li>
 *   <li>Establece automáticamente las fechas de creación y modificación</li>
 *   <li>Inicializa el estado activo del pedido</li>
 *   <li>Preserva el ID en caso de actualizaciones</li>
 * </ul>
 *
 * @author Cuau Cabrera
 * @version 1.0
 * @see Pedido
 * @see PedidoDTO
 * @see IMapper
 */


@Component
public class PedidoDTOInToPedido implements IMapper<PedidoDTO, Pedido>{
	
	/**
     * Convierte un PedidoDTO en una entidad Pedido, estableciendo todos los campos necesarios
     * y manejando las relaciones y campos de auditoría.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Preserva el ID si existe (útil para actualizaciones)</li>
     *   <li>Mapea los campos básicos del pedido (codigoProducto, cantidad, precio)</li>
     *   <li>Establece la relación con Cliente a través del emailCliente</li>
     *   <li>Inicializa o actualiza las fechas de auditoría</li>
     *   <li>Establece el estado activo del pedido</li>
     * </ul>
     *
     * <p>Manejo de fechas:
     * <ul>
     *   <li>Si es un nuevo pedido, establece la fecha de creación al momento actual</li>
     *   <li>Actualiza la fecha de modificación en cada mapeo</li>
     * </ul>
     *
     * @param pedidoDTO el DTO {@link PedidoDTO} que será convertido a entidad
     * @return una nueva instancia de {@link Pedido} con todos los campos mapeados desde el DTO
     * @throws NullPointerException si pedidoDTO es null
     */
	
	private final ClienteDTOInToCliente clienteMapper;

	private PedidoDTOInToPedido(ClienteDTOInToCliente clienteMapper) {
		this.clienteMapper = clienteMapper;
	}

	@Override
	public Pedido map(PedidoDTO pedidoDTO) {
		Pedido pedido = new Pedido();
		if (pedidoDTO.getId() != null) {
			pedido.setId(pedidoDTO.getId());
		}
		pedido.setCodidoProducto(pedidoDTO.getCodidoProducto());
		
		if (pedidoDTO.getIdCliente()!=null) {
			pedido.setIdCliente(clienteMapper.map(pedidoDTO.getIdCliente()));
		}
		
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
