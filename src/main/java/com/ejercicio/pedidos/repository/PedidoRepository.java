package com.ejercicio.pedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ejercicio.pedidos.entity.Pedido;

import java.time.LocalDate;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	Optional<Pedido>findByIdAndIsActiveTrue(Long id);
	List<Pedido> findByEmailClienteAndIsActiveTrue(String email);
	List<Pedido>findByIdClienteAndIsActiveTrue(Long id);
	List<Pedido> findByFechaCreacionAndIsActiveTrue(LocalDate fecha);

}
