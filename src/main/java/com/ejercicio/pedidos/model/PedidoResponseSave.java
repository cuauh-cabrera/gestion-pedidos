package com.ejercicio.pedidos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseSave {
	private Long id;
	private int codigo;
	private String mensaje;
}
