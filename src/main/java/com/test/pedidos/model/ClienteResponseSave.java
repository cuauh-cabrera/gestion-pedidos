package com.test.pedidos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseSave {
	private String mensaje;
	private int codigo;
	private String emailUsuario;
}
