package com.ejercicio.pedidos.model;

import java.util.List;

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
