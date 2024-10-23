package com.ejercicio.pedidos.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ejercicio.pedidos.entity.Cliente;
import com.ejercicio.pedidos.exceptions.NoContentException;
import com.ejercicio.pedidos.exceptions.NotFoundException;
import com.ejercicio.pedidos.exceptions.ServerErrorException;
import com.ejercicio.pedidos.mapper.impl.ClienteDTOInToCliente;
import com.ejercicio.pedidos.mapper.impl.ClienteInToClienteDTO;
import com.ejercicio.pedidos.model.ClienteDTO;
import com.ejercicio.pedidos.model.ClienteResponse;
import com.ejercicio.pedidos.model.ClienteResponseSave;
import com.ejercicio.pedidos.repository.ClienteRepository;
import com.ejercicio.pedidos.service.IClienteService;
import com.ejercicio.pedidos.utils.ClienteConstantes;

import lombok.extern.slf4j.Slf4j;


/**
 * Servicio que implementa la lógica de negocio para la gestión de Clientes.
 * Esta clase proporciona operaciones CRUD y búsquedas específicas para la entidad Cliente.
 *
 * <p>Utiliza un repositorio para el acceso a datos y mappers para la conversión entre
 * entidades y DTOs. Implementa soft delete para el borrado lógico de registros.
 *
 * @author Cuau Cabrera
 * @version 1.0
 * @see ClienteRepository
 * @see ClienteInToClienteDTO
 * @see ClienteDTOInToCliente
 */


@Service
@Slf4j
public class ClienteService implements IClienteService{
	
	/**
     * Constructor que inicializa las dependencias necesarias para el servicio.
     *
     * @param clienteRepository repositorio para operaciones de base de datos
     * @param mapperRead mapper para convertir de Cliente a ClienteDTO
     * @param mapperInsert mapper para convertir de ClienteDTO a Cliente
     */
	
	
	private final ClienteRepository clienteRepository;
	private final ClienteInToClienteDTO mapperRead;
	private final ClienteDTOInToCliente mapperInsert;
	
	public ClienteService(ClienteRepository clienteRepository, ClienteInToClienteDTO mapperRead, ClienteDTOInToCliente mapperInsert) {
		this.clienteRepository = clienteRepository;
		this.mapperRead = mapperRead;
		this.mapperInsert = mapperInsert;
	}
	
	/**
     * Recupera todos los clientes activos del sistema.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Consulta todos los clientes de la base de datos</li>
     *   <li>Filtra solo los clientes activos</li>
     *   <li>Convierte las entidades a DTOs</li>
     *   <li>Construye la respuesta con los datos encontrados</li>
     * </ul>
     *
     * @return ClienteResponse conteniendo la lista de clientes activos
     * @throws NoContentException si no se encuentran clientes activos
     * @throws ServerErrorException si ocurre un error durante el proceso
     */
	@Override
	public ClienteResponse readAll() throws NoContentException {
		try {
			List<Cliente> clienteList = clienteRepository.findAll().stream().filter(
					cliente -> cliente.getIsActive() != ClienteConstantes.FILTER && cliente.getIsActive() != null)
					.toList();
			if (clienteList.isEmpty()) {
				log.error(ClienteConstantes.NO_CONTENT_LOG);
				throw new NoContentException(ClienteConstantes.NO_CONTENT_MSG);
			} else {
				List<ClienteDTO> cListDtos = clienteList.stream().map(cliente -> {
					return mapperRead.map(cliente);
				}).toList();
				ClienteResponse clienteResponse = new ClienteResponse();
				clienteResponse.setMensaje(ClienteConstantes.SUCCESS_MESSAGE);
				clienteResponse.setCodigo(200);
				clienteResponse.setClientes(cListDtos);
				return clienteResponse;
			}

		} catch (ServerErrorException e) {
			log.error(ClienteConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(ClienteConstantes.SERVER_ERROR_MSG);
		}
	}
	
	/**
     * Busca un cliente específico por su ID.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Busca el cliente por ID en la base de datos</li>
     *   <li>Verifica que el cliente exista y esté activo</li>
     *   <li>Convierte la entidad a DTO</li>
     *   <li>Construye la respuesta con los datos encontrados</li>
     * </ul>
     *
     * @param id identificador único del cliente a buscar
     * @return ClienteResponse conteniendo la información del cliente encontrado
     * @throws NotFoundException si el cliente no existe o está inactivo
     * @throws ServerErrorException si ocurre un error durante el proceso
     */
	@Override
	public ClienteResponse readById(Long id) throws NotFoundException {
		try {
			Optional<Cliente> clOptional = clienteRepository.findById(id);

			if (clOptional.isEmpty() || clOptional.get().getIsActive() == ClienteConstantes.FILTER
					|| clOptional.get().getIsActive() == null) {
				log.error(ClienteConstantes.NOT_FOUND_LOG);
				throw new NotFoundException(ClienteConstantes.NOT_FOUND_MSG);
			} else {
				Cliente cliente = clOptional.get();
				List<ClienteDTO> clienteDTO = Stream.of(cliente).map(cl -> {
					return mapperRead.map(cl);
				}).toList();
				ClienteResponse clienteResponse = new ClienteResponse();
				clienteResponse.setMensaje(ClienteConstantes.SUCCESS_MESSAGE);
				clienteResponse.setCodigo(200);
				clienteResponse.setClientes(clienteDTO);
				log.info(ClienteConstantes.SUCCESS_LOG);
				return clienteResponse;

			}
		} catch (ServerErrorException e) {
			log.error(ClienteConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(ClienteConstantes.SERVER_ERROR_MSG);
		}
	}
	
	/**
     * Crea un nuevo cliente en el sistema.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Convierte el DTO a entidad Cliente</li>
     *   <li>Persiste el nuevo cliente en la base de datos</li>
     *   <li>Construye la respuesta con los datos del cliente creado</li>
     * </ul>
     *
     * @param clienteDTO datos del cliente a crear
     * @return ClienteResponseSave conteniendo la confirmación de la creación
     * @throws ServerErrorException si ocurre un error durante el proceso
     */
	@Override
	@Transactional
	public ClienteResponseSave insert(ClienteDTO clienteDTO) {
		try {
			 Cliente cliente = mapperInsert.map(clienteDTO);
			 clienteRepository.save(cliente);
			 ClienteResponseSave clienteResponseSave = new ClienteResponseSave();
			 clienteResponseSave.setEmailCliente(cliente.getEmailCliente());
			 clienteResponseSave.setCodigo(201);
			 clienteResponseSave.setMensaje(ClienteConstantes.CREATED_MSG);
			 log.info(ClienteConstantes.SUCCESS_LOG);
			 return clienteResponseSave;	
				
		} catch (ServerErrorException e) {
			log.error(ClienteConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(ClienteConstantes.SERVER_ERROR_MSG);
		}
	}
	
	/**
     * Actualiza la información de un cliente existente.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Verifica que el cliente exista y esté activo</li>
     *   <li>Convierte el DTO a entidad Cliente</li>
     *   <li>Actualiza los datos en la base de datos</li>
     *   <li>Construye la respuesta con la confirmación de la actualización</li>
     * </ul>
     *
     * @param id identificador único del cliente a actualizar
     * @param clienteDTO nuevos datos del cliente
     * @return ClienteResponseSave conteniendo la confirmación de la actualización
     * @throws NotFoundException si el cliente no existe o está inactivo
     * @throws ServerErrorException si ocurre un error durante el proceso
     */
	@Override
	@Transactional
	public ClienteResponseSave update(Long id, ClienteDTO clienteDTO) throws NotFoundException {
		try {
			Optional<Cliente> clOptional = clienteRepository.findById(id);
			
			if (clOptional.isEmpty() || clOptional.get()
					.getIsActive()==ClienteConstantes.FILTER || clOptional.get()
					.getIsActive()==null) {
				log.error(ClienteConstantes.NOT_FOUND_LOG);
				throw new NotFoundException(ClienteConstantes.NOT_FOUND_MSG);
			} else {
				Cliente cliente = mapperInsert.map(clienteDTO);
				clienteRepository.save(cliente);
				ClienteResponseSave clienteResponseSave = new ClienteResponseSave();
				clienteResponseSave.setEmailCliente(cliente.getEmailCliente());
				clienteResponseSave.setMensaje(ClienteConstantes.UPDATED_MSG);
				clienteResponseSave.setCodigo(201);
				log.info(ClienteConstantes.CREATED_MSG);
				return clienteResponseSave;				
			}
			
		} catch (ServerErrorException e) {
			log.error(ClienteConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(ClienteConstantes.SERVER_ERROR_MSG);
		}
	}
	
	/**
     * Realiza un borrado lógico de un cliente por su ID.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Verifica que el cliente exista y esté activo</li>
     *   <li>Marca el cliente como inactivo en la base de datos</li>
     *   <li>Construye la respuesta con la confirmación del borrado</li>
     * </ul>
     *
     * @param id identificador único del cliente a eliminar
     * @return ClienteResponseSave conteniendo la confirmación del borrado
     * @throws NotFoundException si el cliente no existe o ya está inactivo
     * @throws ServerErrorException si ocurre un error durante el proceso
     */
	@Override
	@Transactional
	public ClienteResponseSave deleteById(Long id) throws NotFoundException {
		try {
			Optional<Cliente> clOptional = clienteRepository.findById(id);
			
			if (clOptional.isEmpty() || clOptional.get()
					.getIsActive()==ClienteConstantes.FILTER || clOptional.get()
					.getIsActive()==null) {
				log.error(ClienteConstantes.NOT_FOUND_LOG);
				throw new NotFoundException(ClienteConstantes.NOT_FOUND_MSG);	
			} else {
				Cliente cliente = clOptional.get();
				cliente.setIsActive(ClienteConstantes.FILTER);
				clienteRepository.save(cliente);
				ClienteResponseSave clienteResponseSave = new ClienteResponseSave();
				clienteResponseSave.setEmailCliente(cliente.getEmailCliente());
				clienteResponseSave.setCodigo(200);
				clienteResponseSave.setMensaje(ClienteConstantes.DELETED_MSG);
				log.info(ClienteConstantes.SUCCESS_LOG);
				return clienteResponseSave;
			}
			
		} catch (ServerErrorException e) {
			log.error(ClienteConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(ClienteConstantes.SERVER_ERROR_MSG);
		}
	}
	
	   /**
     * Busca un cliente por su dirección de email.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Busca el cliente por email en la base de datos</li>
     *   <li>Verifica que el cliente exista y esté activo</li>
     *   <li>Convierte la entidad a DTO</li>
     *   <li>Construye la respuesta con los datos encontrados</li>
     * </ul>
     *
     * @param email dirección de email del cliente a buscar
     * @return ClienteResponse conteniendo la información del cliente encontrado
     * @throws NotFoundException si el cliente no existe o está inactivo
     * @throws ServerErrorException si ocurre un error durante el proceso
     */
	@Override
	public ClienteResponse findByEmail(String email) throws NotFoundException {
		try {
			Optional<Cliente> clOptional = clienteRepository.findClienteByEmailClienteAndIsActiveTrue(email);

			if (clOptional.isEmpty() || clOptional.get().getIsActive() == ClienteConstantes.FILTER
					|| clOptional.get().getIsActive() == null) {
				log.error(ClienteConstantes.SERVER_ERROR_LOG);
				throw new NotFoundException(ClienteConstantes.NOT_FOUND_MSG);
			} else {
				Cliente cliente = clOptional.get();
				List<ClienteDTO> clienteDTOs = Stream.of(cliente).map(c -> {
					return mapperRead.map(cliente);
				}).toList();
				ClienteResponse clienteResponse = new ClienteResponse();
				clienteResponse.setMensaje(ClienteConstantes.SUCCESS_MESSAGE);
				clienteResponse.setCodigo(200);
				clienteResponse.setClientes(clienteDTOs);
				log.info(ClienteConstantes.SUCCESS_LOG);
				return clienteResponse;
			}

		} catch (ServerErrorException e) {
			log.error(ClienteConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(ClienteConstantes.SERVER_ERROR_MSG);
		}
	}

}
