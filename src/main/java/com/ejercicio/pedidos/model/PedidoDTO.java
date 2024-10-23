package com.ejercicio.pedidos.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
	private Long id;
	private Long codidoProducto;
	private ClienteDTO idCliente;
	private int cantidad;
	private Double precio;
	private LocalDate fechaCreacion;
}
