package com.ejercicio.pedidos.mapper.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.ejercicio.pedidos.entity.Cliente;
import com.ejercicio.pedidos.mapper.IMapper;
import com.ejercicio.pedidos.model.ClienteDTO;


/**
 * Mapper que convierte objetos ClienteDTO a entidades Cliente.
 * Esta clase se encarga de transformar los datos del DTO en una entidad de persistencia,
 * incluyendo la inicialización de campos de auditoría y estado.
 *
 * <p>Implementa la interfaz {@link IMapper} para mantener una estructura consistente
 * en el mapeo de objetos del dominio.
 *
 * <p>Características principales:
 * <ul>
 *   <li>Preserva el ID existente si está presente en el DTO</li>
 *   <li>Inicializa campos de auditoría (fechaCreacion, fechaModificacion)</li>
 *   <li>Establece el estado activo del cliente</li>
 * </ul>
 *
 * @author Cuau Cabrera
 * @version 1.0
 * @see Cliente
 * @see ClienteDTO
 * @see IMapper
 */

@Component
public class ClienteDTOInToCliente implements IMapper<ClienteDTO, Cliente>{
	
	/**
     * Convierte un ClienteDTO en una entidad Cliente.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Mapea todos los campos básicos del cliente (nombre, apellidos, email, etc.)</li>
     *   <li>Preserva el ID si existe en el DTO (útil para actualizaciones)</li>
     *   <li>Inicializa la fecha de creación si es un nuevo cliente</li>
     *   <li>Actualiza la fecha de modificación</li>
     *   <li>Establece el estado activo del cliente</li>
     * </ul>
     *
     * @param clienteDTO el DTO fuente que contiene los datos del cliente
     * @return una nueva instancia de {@link Cliente} con todos los campos mapeados
     * @throws NullPointerException si clienteDTO es null
     */

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
		cliente.setEmailCliente(clienteDTO.getEmailCliente());
		cliente.setDireccionEnvio(clienteDTO.getDireccionEnvio());
		
		// Inicialización de campos de auditoría
		if (cliente.getFechaCreacion()==null) {
			cliente.setFechaCreacion(LocalDate.now());
		}
		cliente.setFechaModificacion(LocalDate.now());
		cliente.setIsActive(true);
		
		return cliente;
	}
	
	

}


