package com.test.pedidos.entity;

import java.time.LocalDate;

import com.test.pedidos.utils.ClienteConstantes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
public class Cliente {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = ClienteConstantes.REQUIRED_NOMBRE)
	@Column(name = "nombre")
	private String nombreCliente;
	
	@NotNull(message = ClienteConstantes.REQUIRED_APELLIDO)
	@Column(name = "apellido_paterno")
	private String apellidoPaterno;
	
	@Column(name = "apellido_materno")
	private String apellidoMaterno;
	
	@NotNull(message = ClienteConstantes.REQUIRED_EMAIL)
	@Email(message = ClienteConstantes.INVALID_EMAIL)
	@Column(name = "email")
	private String emailUsuario;
	
	@NotNull(message = ClienteConstantes.REQUIRED_DIRECCION)
	@Column(name = "direccion_envio")
	private String direccionEnvio;
	
	@NotNull
	@Column(name = "fecha_creacion",updatable = false)
	private LocalDate fechaCreacion;
	
	@NotNull
	@Column(name = "fecha_modificacion")
	private LocalDate fechaModificacion;
	
	@NotNull
	@Column(name = "is_active")
	private Boolean isActive;
	
	@PrePersist
	private void onCreate() {
		this.fechaCreacion=LocalDate.now();
		this.fechaModificacion = LocalDate.now();
	}
	
	@PreUpdate
	private void onUpdate() {
		this.fechaModificacion = LocalDate.now();
	}


	
	

	

}
