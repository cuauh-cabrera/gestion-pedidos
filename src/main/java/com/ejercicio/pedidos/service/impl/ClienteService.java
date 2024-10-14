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

@Service
@Slf4j
public class ClienteService implements IClienteService{
	
	
	private final ClienteRepository clienteRepository;
	private final ClienteInToClienteDTO mapperRead;
	private final ClienteDTOInToCliente mapperInsert;
	
	public ClienteService(ClienteRepository clienteRepository, ClienteInToClienteDTO mapperRead, ClienteDTOInToCliente mapperInsert) {
		this.clienteRepository = clienteRepository;
		this.mapperRead = mapperRead;
		this.mapperInsert = mapperInsert;
	}


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

	@Override
	@Transactional
	public ClienteResponseSave insert(ClienteDTO clienteDTO) {
		try {
			 Cliente cliente = mapperInsert.map(clienteDTO);
			 clienteRepository.save(cliente);
			 ClienteResponseSave clienteResponseSave = new ClienteResponseSave();
			 clienteResponseSave.setEmailUsuario(cliente.getEmailUsuario());
			 clienteResponseSave.setCodigo(201);
			 clienteResponseSave.setMensaje(ClienteConstantes.CREATED_MSG);
			 log.info(ClienteConstantes.SUCCESS_LOG);
			 return clienteResponseSave;	
				
		} catch (ServerErrorException e) {
			log.error(ClienteConstantes.SERVER_ERROR_LOG);
			throw new ServerErrorException(ClienteConstantes.SERVER_ERROR_MSG);
		}
	}

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
				clienteResponseSave.setEmailUsuario(cliente.getEmailUsuario());
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
				clienteResponseSave.setEmailUsuario(cliente.getEmailUsuario());
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

	@Override
	public ClienteResponse findByEmail(String email) throws NotFoundException {
		try {
			Optional<Cliente> clOptional = clienteRepository.findClienteByEmailUsuarioAndIsActiveTrue(email);

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
