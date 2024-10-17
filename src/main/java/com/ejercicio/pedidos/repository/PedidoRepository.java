package com.ejercicio.pedidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ejercicio.pedidos.entity.Pedido;

import java.time.LocalDate;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	Optional<Pedido>findByIdAndIsActiveTrue(Long id);
	Optional<Pedido> findByEmailClienteAndIsActiveTrue(String email);
	Optional<Pedido>findByIdClienteAndIsActiveTrue(Long id);
	Optional<Pedido>findByFechaCreacionAndIsActiveTrue(LocalDate fecha);

}
