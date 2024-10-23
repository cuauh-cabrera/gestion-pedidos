package com.ejercicio.pedidos.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.ejercicio.pedidos.entity.Cliente;
import com.ejercicio.pedidos.entity.Pedido;
import com.ejercicio.pedidos.exceptions.NoContentException;
import com.ejercicio.pedidos.exceptions.NotFoundException;
import com.ejercicio.pedidos.exceptions.ServerErrorException;
import com.ejercicio.pedidos.mapper.impl.PedidoDTOInToPedido;
import com.ejercicio.pedidos.mapper.impl.PedidoInToPedidoDTO;
import com.ejercicio.pedidos.model.PedidoDTO;
import com.ejercicio.pedidos.model.PedidoResponse;
import com.ejercicio.pedidos.model.PedidoResponseSave;
import com.ejercicio.pedidos.repository.PedidoRepository;
import com.ejercicio.pedidos.service.IPedidoService;
import com.ejercicio.pedidos.utils.PedidoConstantes;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio que implementa la lógica de negocio para la gestión de Pedidos.
 * Proporciona operaciones CRUD y búsquedas específicas para la entidad Pedido,
 * incluyendo búsquedas por cliente, email y fecha de creación.
 *
 * <p>Esta clase maneja el mapeo entre entidades y DTOs, así como la gestión
 * de estados activos/inactivos para implementar borrado lógico.
 *
 * @author Cuau Cabrera
 * @version 1.0
 * @see PedidoRepository
 * @see PedidoInToPedidoDTO
 * @see PedidoDTOInToPedido
 */


@Service
@Slf4j
public class PedidoService implements IPedidoService {
	
	private final PedidoRepository pedidoRepository;
	private final PedidoInToPedidoDTO mapperRead;
	private final PedidoDTOInToPedido mapperSave;
	
	/**
     * Constructor que inicializa las dependencias necesarias para el servicio.
     *
     * @param pedidoRepository repositorio para operaciones de base de datos
     * @param mapperRead mapper para convertir de Pedido a PedidoDTO
     * @param mapperSave mapper para convertir de PedidoDTO a Pedido
     */
	public PedidoService(PedidoRepository pedidoRepository, 
			PedidoInToPedidoDTO mapperRead, 
			PedidoDTOInToPedido mapperSave) {
		this.pedidoRepository = pedidoRepository;
		this.mapperRead = mapperRead;
		this.mapperSave = mapperSave;
	}
	
	/**
     * Recupera todos los pedidos activos del sistema.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Obtiene todos los pedidos de la base de datos</li>
     *   <li>Filtra solo los pedidos activos</li>
     *   <li>Mapea las entidades a DTOs</li>
     *   <li>Construye una respuesta con la lista de pedidos</li>
     * </ul>
     *
     * @return PedidoResponse conteniendo la lista de pedidos activos
     * @throws NoContentException si no se encuentran pedidos activos
     * @throws ServerErrorException si ocurre un error en el servidor
     */
	@Override
	public PedidoResponse readAll() throws NoContentException {
		try {
			List<Pedido> pedidoList = pedidoRepository.findAll().stream()
					.filter(pedido -> pedido.getIsActive()!=PedidoConstantes.FILTER && pedido.getIsActive()!=null)
					.toList();
			
			if (pedidoList.isEmpty()) {
				log.error(PedidoConstantes.NO_CONTENT_LOG);
				throw new NoContentException(PedidoConstantes.NO_CONTENT_MSG);
			} else {
					List<PedidoDTO> pedidoDTOs = pedidoList.stream().map(pedido -> {
					return mapperRead.map(pedido);
				}).toList();
				PedidoResponse pedidoResponse = new PedidoResponse();
				pedidoResponse.setMensaje(PedidoConstantes.SUCCESS_MESSAGE);
				pedidoResponse.setCodigo(200);
				pedidoResponse.setPedidos(pedidoDTOs);
				log.info(PedidoConstantes.SUCCESS_LOG);
				return pedidoResponse;
			}
			
		} catch (ServerErrorException e) {
			log.error(PedidoConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(PedidoConstantes.SERVER_ERROR_MSG);
		}
	}
	
	/**
     * Busca un pedido específico por su ID.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Busca el pedido por ID en la base de datos</li>
     *   <li>Verifica que el pedido exista y esté activo</li>
     *   <li>Mapea la entidad a DTO</li>
     *   <li>Construye la respuesta con el pedido encontrado</li>
     * </ul>
     *
     * @param id identificador único del pedido a buscar
     * @return PedidoResponse conteniendo la información del pedido
     * @throws NotFoundException si el pedido no existe o está inactivo
     * @throws ServerErrorException si ocurre un error en el servidor
     */
	@Override
	public PedidoResponse readById(Long id) throws NotFoundException {
		try {
			Optional<Pedido> pedidoOptional = pedidoRepository.findByIdAndIsActiveTrue(id);
			
			if (pedidoOptional.isEmpty() || pedidoOptional.get().getIsActive()==null) {
				log.error(PedidoConstantes.NOT_FOUND_LOG);
				throw new NotFoundException(PedidoConstantes.NOT_FOUND_MSG);
			} else {
				Pedido pedido = pedidoOptional.get();
				List<PedidoDTO> pedidoDTOs = Stream.of(pedido).map(p -> {
					return mapperRead.map(pedido);
				}).toList();
				PedidoResponse pedidoResponse = new PedidoResponse();
				pedidoResponse.setMensaje(PedidoConstantes.SUCCESS_MESSAGE);
				pedidoResponse.setCodigo(200);
				pedidoResponse.setPedidos(pedidoDTOs);
				log.info(PedidoConstantes.SUCCESS_LOG);
				return pedidoResponse;
			}
			
		} catch (ServerErrorException e) {
			log.error(PedidoConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(PedidoConstantes.SERVER_ERROR_MSG);
		}
	}
	
	/**
     * Crea un nuevo pedido en el sistema.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Convierte el DTO a entidad Pedido</li>
     *   <li>Persiste el nuevo pedido en la base de datos</li>
     *   <li>Construye la respuesta con la confirmación de creación</li>
     * </ul>
     *
     * @param pedidoDTO datos del pedido a crear
     * @return PedidoResponseSave conteniendo la confirmación de la creación
     * @throws ServerErrorException si ocurre un error en el servidor
     */
	@Override
	public PedidoResponseSave insert(PedidoDTO pedidoDTO) {
		try {
			Pedido pedido = mapperSave.map(pedidoDTO);
			pedido = pedidoRepository.save(pedido);
			PedidoResponseSave pedidoResponseSave = new PedidoResponseSave();
			pedidoResponseSave.setId(pedido.getId());
			pedidoResponseSave.setCodigo(201);
			pedidoResponseSave.setMensaje(PedidoConstantes.CREATED_MSG);
			log.info(PedidoConstantes.SUCCESS_LOG);
			return pedidoResponseSave;

		} catch (ServerErrorException e) {
			log.error(PedidoConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(PedidoConstantes.SERVER_ERROR_MSG);
		}
	}
	
	/**
     * Actualiza un pedido existente.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Verifica que el pedido exista y esté activo</li>
     *   <li>Actualiza los datos del pedido</li>
     *   <li>Persiste los cambios en la base de datos</li>
     *   <li>Construye la respuesta con la confirmación de actualización</li>
     * </ul>
     *
     * @param id identificador único del pedido a actualizar
     * @param pedidoDTO nuevos datos del pedido
     * @return PedidoResponseSave conteniendo la confirmación de la actualización
     * @throws NotFoundException si el pedido no existe o está inactivo
     * @throws ServerErrorException si ocurre un error en el servidor
     */
	@Override
	public PedidoResponseSave update(Long id, PedidoDTO pedidoDTO) throws NotFoundException {
		try {
			Optional<Pedido> pedidoOptional = pedidoRepository.findByIdAndIsActiveTrue(id);
			
			if (pedidoOptional.isEmpty() || pedidoOptional.get().getIsActive() == null) {
				log.error(PedidoConstantes.NOT_FOUND_LOG);
				throw new NotFoundException(PedidoConstantes.NOT_FOUND_MSG);
			} else {
				Pedido pedido = mapperSave.map(pedidoDTO);
				pedido = pedidoRepository.save(pedido);
				PedidoResponseSave pedidoResponseSave = new PedidoResponseSave();
				pedidoResponseSave.setId(pedido.getId());
				pedidoResponseSave.setCodigo(201);
				pedidoResponseSave.setMensaje(PedidoConstantes.UPDATED_MSG);
				log.info(PedidoConstantes.SUCCESS_LOG);
				return pedidoResponseSave;
			}
			
		} catch (ServerErrorException e) {
			log.error(PedidoConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(PedidoConstantes.SERVER_ERROR_MSG);
		}
	}
	
	/**
     * Realiza un borrado lógico de un pedido.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Verifica que el pedido exista y esté activo</li>
     *   <li>Marca el pedido como inactivo</li>
     *   <li>Persiste el cambio en la base de datos</li>
     *   <li>Construye la respuesta con la confirmación del borrado</li>
     * </ul>
     *
     * @param id identificador único del pedido a eliminar
     * @return PedidoResponseSave conteniendo la confirmación del borrado
     * @throws NotFoundException si el pedido no existe o está inactivo
     * @throws ServerErrorException si ocurre un error en el servidor
     */
	@Override
	public PedidoResponseSave deleteById(Long id) throws NotFoundException {
		try {
			Optional<Pedido> pedidoOptional = pedidoRepository.findByIdAndIsActiveTrue(id);

			if (pedidoOptional.isEmpty() || pedidoOptional.get().getIsActive() == null) {
				log.error(PedidoConstantes.NOT_FOUND_LOG);
				throw new NotFoundException(PedidoConstantes.NOT_FOUND_MSG);
			} else {
				Pedido pedido = pedidoOptional.get();
				pedido.setIsActive(PedidoConstantes.FILTER);
				pedidoRepository.save(pedido);
				PedidoResponseSave pedidoResponseSave = new PedidoResponseSave();
				pedidoResponseSave.setId(pedido.getId());
				pedidoResponseSave.setCodigo(200);
				pedidoResponseSave.setMensaje(PedidoConstantes.DELETED_MSG);
				log.info(PedidoConstantes.DELETED_MSG);
				return pedidoResponseSave;
			}

		} catch (ServerErrorException e) {
			log.error(PedidoConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(PedidoConstantes.SERVER_ERROR_MSG);
		}
	}
	
	 /**
     * Busca todos los pedidos asociados a un cliente específico.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Busca pedidos por ID de cliente</li>
     *   <li>Filtra solo pedidos activos</li>
     *   <li>Mapea las entidades a DTOs</li>
     *   <li>Construye la respuesta con los pedidos encontrados</li>
     * </ul>
     *
     * @param idCliente cliente cuyos pedidos se buscan
     * @return PedidoResponse conteniendo la lista de pedidos del cliente
     * @throws NotFoundException si no se encuentran pedidos para el cliente
     * @throws ServerErrorException si ocurre un error en el servidor
     */
	@Override
	public PedidoResponse readByIdCliente(Cliente idCliente) throws NotFoundException {
		try {
			List<Pedido> pedidoList = pedidoRepository.findByIdClienteAndIsActiveTrue(idCliente).stream()
					.filter(pedido -> pedido.getIsActive() != null).toList();

			if (pedidoList.isEmpty()) {
				log.error(PedidoConstantes.CLIENTE_IF_NOT_FOUND_LOG);
				throw new NotFoundException(PedidoConstantes.CLIENTE_ID_NOT_FOUND_MSG);
			} else {
				List<PedidoDTO> pedidoDTOs = pedidoList.stream().map(pedido -> {
					return mapperRead.map(pedido);
				}).toList();
				PedidoResponse pedidoResponse = new PedidoResponse();
				pedidoResponse.setMensaje(PedidoConstantes.SUCCESS_MESSAGE);
				pedidoResponse.setCodigo(200);
				pedidoResponse.setPedidos(pedidoDTOs);
				log.info(PedidoConstantes.SUCCESS_LOG);
				return pedidoResponse;
			}

		} catch (ServerErrorException e) {
			log.error(PedidoConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(PedidoConstantes.SERVER_ERROR_MSG);
		}
	}
	
	/**
     * Busca todos los pedidos asociados a un email de cliente.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Busca pedidos por email del cliente</li>
     *   <li>Filtra solo pedidos activos</li>
     *   <li>Mapea las entidades a DTOs</li>
     *   <li>Construye la respuesta con los pedidos encontrados</li>
     * </ul>
     *
     * @param emailCliente email del cliente cuyos pedidos se buscan
     * @return PedidoResponse conteniendo la lista de pedidos asociados al email
     * @throws NotFoundException si no se encuentran pedidos para el email
     * @throws ServerErrorException si ocurre un error en el servidor
     */
	@Override
	public PedidoResponse readByemailCliente(String emailCliente) throws NotFoundException {
		try {
			List<Pedido> pedidoList = pedidoRepository.findByEmailClienteAndIsActiveTrue(emailCliente).stream()
					.filter(pedido -> pedido.getIsActive() != null).toList();

			if (pedidoList.isEmpty()) {
				log.error(PedidoConstantes.EMAIL_CLIENTE_NOT_FOUND_LOG);
				throw new NotFoundException(PedidoConstantes.EMAIL_CLIENTE_NOT_FOUND_MSG);
			} else {
				List<PedidoDTO> pedidoDTOs = pedidoList.stream().map(pedido -> {
					return mapperRead.map(pedido);
				}).toList();
				PedidoResponse pedidoResponse = new PedidoResponse();
				pedidoResponse.setMensaje(PedidoConstantes.SUCCESS_MESSAGE);
				pedidoResponse.setCodigo(200);
				pedidoResponse.setPedidos(pedidoDTOs);
				log.info(PedidoConstantes.SUCCESS_LOG);
				return pedidoResponse;
			}
		} catch (ServerErrorException e) {
			log.error(PedidoConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(PedidoConstantes.SERVER_ERROR_MSG);
		}
	}
	
	/**
     * Busca todos los pedidos creados en una fecha específica.
     * 
     * <p>El método realiza las siguientes operaciones:
     * <ul>
     *   <li>Busca pedidos por fecha de creación</li>
     *   <li>Filtra solo pedidos activos</li>
     *   <li>Mapea las entidades a DTOs</li>
     *   <li>Construye la respuesta con los pedidos encontrados</li>
     * </ul>
     *
     * @param fechaCreacion fecha de creación de los pedidos a buscar
     * @return PedidoResponse conteniendo la lista de pedidos de la fecha
     * @throws NotFoundException si no se encuentran pedidos para la fecha
     * @throws ServerErrorException si ocurre un error en el servidor
     */
	@Override
	public PedidoResponse readByFechaCreacion(LocalDate fechaCreacion) throws NotFoundException {
		try {
			List<Pedido> pedidoList = pedidoRepository.findByFechaCreacionAndIsActiveTrue(fechaCreacion).stream()
					.filter(pedido -> pedido.getIsActive() != null && pedido.getFechaCreacion() != null).toList();

			if (pedidoList.isEmpty()) {
				log.error(PedidoConstantes.DATE_NOT_FOUND_LOG);
				throw new NotFoundException(PedidoConstantes.DATE_NOT_FOUND_MSG);
			} else {
				List<PedidoDTO> pedidoDTOs = pedidoList.stream().map(pedido -> {
					return mapperRead.map(pedido);
				}).toList();
				PedidoResponse pedidoResponse = new PedidoResponse();
				pedidoResponse.setMensaje(PedidoConstantes.SUCCESS_MESSAGE);
				pedidoResponse.setCodigo(200);
				pedidoResponse.setPedidos(pedidoDTOs);
				log.info(PedidoConstantes.SUCCESS_LOG);
				return pedidoResponse;
			}

		} catch (ServerErrorException e) {
			log.error(PedidoConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(PedidoConstantes.SERVER_ERROR_MSG);
		}
	}

}
