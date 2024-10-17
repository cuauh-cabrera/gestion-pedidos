package com.ejercicio.pedidos.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

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

@Service
@Slf4j
public class PedidoService implements IPedidoService {
	
	private final PedidoRepository pedidoRepository;
	private final PedidoInToPedidoDTO mapperRead;
	private final PedidoDTOInToPedido mapperSave;
	
	public PedidoService(PedidoRepository pedidoRepository, PedidoInToPedidoDTO mapperRead, PedidoDTOInToPedido mapperSave) {
		this.pedidoRepository = pedidoRepository;
		this.mapperRead = mapperRead;
		this.mapperSave = mapperSave;
	}

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

	@Override
	public PedidoResponseSave deleteById(Long id) {
		// TODO Borrar pedido By id
		return null;
	}

	@Override
	public PedidoResponse readByIdCliente(Long idCliente) {
		// TODO Pedido By id de cliente
		return null;
	}

	@Override
	public PedidoResponse readByemailCliente(String emailCliente) {
		// TODO Pedido By email de cliente
		return null;
	}

	@Override
	public PedidoResponse readByFechaCreacion(LocalDate fechaCreacion) {
		// TODO Pedido By fecha de creacion del pedido
		return null;
	}

}
