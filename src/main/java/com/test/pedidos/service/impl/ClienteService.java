package com.test.pedidos.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.test.pedidos.entity.Cliente;
import com.test.pedidos.exceptions.NoContentException;
import com.test.pedidos.exceptions.ServerErrorException;
import com.test.pedidos.mapper.impl.ClienteInToClienteDTO;
import com.test.pedidos.model.ClienteDTO;
import com.test.pedidos.model.ClienteResponse;
import com.test.pedidos.model.ClienteResponseSave;
import com.test.pedidos.repository.ClienteRepository;
import com.test.pedidos.service.IClienteService;
import com.test.pedidos.utils.ClienteConstantes;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClienteService implements IClienteService{
	
	
	private final ClienteRepository clienteRepository;
	private final ClienteInToClienteDTO mapperRead;
	
	public ClienteService(ClienteRepository clienteRepository, ClienteInToClienteDTO mapperRead) {
		this.clienteRepository = clienteRepository;
		this.mapperRead = mapperRead;
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
					ClienteDTO clienteDTO = mapperRead.map(cliente);
					return clienteDTO;
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
	public ClienteResponse readById(Long id) {
		return null;
	}

	@Override
	public ClienteResponseSave insert(ClienteDTO clienteDTO) {
		return null;
	}

	@Override
	public ClienteResponseSave update(Long id, ClienteDTO clienteDTO) {
		return null;
	}

	@Override
	public ClienteResponseSave deleteById(Long id) {
		return null;
	}

	@Override
	public ClienteResponse findByEmail(String email) {
		return null;
	}

}
