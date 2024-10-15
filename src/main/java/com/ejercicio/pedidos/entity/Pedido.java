package com.ejercicio.pedidos.entity;

import java.time.LocalDate;

import com.ejercicio.pedidos.utils.PedidoConstantes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotBlank(message = PedidoConstantes.REQUIRED_CODIGO )
	@Column(name = "codigo_producto")
	private Long codidoProducto;
	
	@NotBlank(message = PedidoConstantes.REQUIRED_EMAIL)
	@Column(name = "email_cliente")
	private String emailCliente;
	
	@NotBlank(message = PedidoConstantes.REQUIRED_ID_CLIENTE)
	@Column(name = "id_cliente")
	private Long idCliente;
	
	
	@NotBlank(message = PedidoConstantes.REQUIRED_CANTIDAD)
	@Column(name = "cantidad")
	private int cantidad;
	
	@NotBlank(message = PedidoConstantes.REQUIRED_PRECIO)
	@Column(name = "precio")
	private Double precio;
	
	@NotBlank
	@Column(name = "fecha_creacion",updatable = false)
	private LocalDate fechaCreacion;
	
	@NotBlank
	@Column(name = "fecha_modificacion")
	private LocalDate fechaModificacion;
	
	@NotBlank
	@Column(name = "is_active")
	private Boolean isActive;
	
	@PrePersist
	private void onCreate() {
		this.fechaCreacion = LocalDate.now();
		this.fechaModificacion = LocalDate.now();
	}
	
	@PreUpdate
	private void onUpdate() {
		this.fechaModificacion = LocalDate.now();
	}
	
}
