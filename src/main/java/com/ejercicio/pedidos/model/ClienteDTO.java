package com.ejercicio.pedidos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
	private Long id;
	private String nombreCliente;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String emailCliente;
	private String direccionEnvio;
}
