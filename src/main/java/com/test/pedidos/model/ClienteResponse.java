package com.test.pedidos.model;

import java.util.List;

import com.test.pedidos.entity.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {
	private String mensaje;
	private int codigo;
	private List<ClienteDTO> clientes;
}
