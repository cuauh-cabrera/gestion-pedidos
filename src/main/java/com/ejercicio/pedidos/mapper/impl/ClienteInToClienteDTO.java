package com.ejercicio.pedidos.mapper.impl;

import org.springframework.stereotype.Component;

import com.ejercicio.pedidos.entity.Cliente;
import com.ejercicio.pedidos.mapper.IMapper;
import com.ejercicio.pedidos.model.ClienteDTO;



/**
 * Mapper que convierte entidades Cliente a objetos ClienteDTO.
 * Esta clase se encarga de transformar los datos de la entidad persistente
 * en un objeto de transferencia de datos (DTO) para su uso en la capa de presentación.
 *
 * <p>Implementa la interfaz {@link IMapper} para mantener una estructura consistente
 * en el mapeo de objetos del dominio.
 *
 * <p>Características principales:
 * <ul>
 *   <li>Mapeo directo de atributos sin transformaciones complejas</li>
 *   <li>No incluye campos de auditoría en el DTO</li>
 *   <li>Mantiene la integridad de los datos del cliente</li>
 * </ul>
 *
 * @author Cuau Cabrera
 * @version 1.0
 * @see Cliente
 * @see ClienteDTO
 * @see IMapper
 */

@Component
public class ClienteInToClienteDTO implements IMapper<Cliente, ClienteDTO>{
	
	/**
     * Convierte una entidad Cliente en un ClienteDTO.
     * 
     * <p>El método realiza un mapeo directo de los siguientes campos:
     * <ul>
     *   <li>ID del cliente</li>
     *   <li>Nombre del cliente</li>
     *   <li>Apellido paterno</li>
     *   <li>Apellido materno</li>
     *   <li>Email del cliente</li>
     *   <li>Dirección de envío</li>
     * </ul>
     *
     * <p>Nota: Los campos de auditoría (fechaCreacion, fechaModificacion) y estado (isActive)
     * no se incluyen en el DTO resultante.
     *
     * @param cliente la entidad fuente que contiene los datos a mapear
     * @return un nuevo objeto {@link ClienteDTO} con los campos básicos mapeados
     * @throws NullPointerException si cliente es null
     */

	@Override
	public ClienteDTO map(Cliente cliente) {
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(cliente.getId());
		clienteDTO.setNombreCliente(cliente.getNombreCliente());
		clienteDTO.setApellidoPaterno(cliente.getApellidoPaterno());
		clienteDTO.setApellidoMaterno(cliente.getApellidoMaterno());
		clienteDTO.setEmailCliente(cliente.getEmailCliente());
		clienteDTO.setDireccionEnvio(cliente.getDireccionEnvio());
		return clienteDTO;
	}
}
