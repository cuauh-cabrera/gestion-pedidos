package com.ejercicio.pedidos.utils;

public class PedidoConstantes {

	public static final Boolean FILTER = false;
	public static final String REQUIRED_CODIGO = "El campo 'codigo de producto' es obligatorio";
	public static final String REQUIRED_ID_CLIENTE = "El campo 'id del cliente' es obligatorio";
	public static final String REQUIRED_EMAIL = "El campo 'email' es obligatorio";
	public static final String INVALID_EMAIL = "Por favor ingrese una direccion de correo valida";
	public static final String REQUIRED_CANTIDAD = "El campo 'cantidad' es obligatorio";
	public static final String REQUIRED_PRECIO = "El campo 'precio' es obligatorio";
	public static final String SUCCESS_MESSAGE = "Consulta exitosa";
	public static final String SUCCESS_LOG = "Operacion exitosa";
	public static final String CREATED_MSG = "El Pedido fue creado con exito";
	public static final String UPDATED_MSG = "El Pedido fue actualizado con exito";
	public static final String DELETED_MSG = "El Pedido fue eliminado con exito";
	public static final String SERVER_ERROR_LOG = "Error: Revisar la conexion a la base de datos y correcta implementacion de la capa Repository";
	public static final String SERVER_ERROR_MSG = "No es posible procesar la solicitud en este momento, intente mas tarde";
    public static final String NO_CONTENT_LOG = "Sin contenido";
    public static final String NO_CONTENT_MSG = "La consulta no arrojo ningun resultado";
    public static final String NOT_FOUND_LOG = "No encontrado";
    public static final String NOT_FOUND_MSG = "No fue posible encontrar el Pedido solicitado";
    public static final String DATE_NOT_FOUND_LOG = "Fecha no encontrada";
    public static final String DATE_NOT_FOUND_MSG = "No se enontraron pedidos creados en las fechas consultadas";
    public static final String CLIENTE_IF_NOT_FOUND_LOG = "Id de Cliente no encontrado";
    public static final String CLIENTE_ID_NOT_FOUND_MSG = "No se enontraron pedidos asociados al Id de Cliente consultado";
    public static final String EMAIL_CLIENTE_NOT_FOUND_LOG = "Email de Cliente no encontrado";
    public static final String EMAIL_CLIENTE_NOT_FOUND_MSG = "No se enontraron pedidos asociados al email del Cliente consultado";
    
	private PedidoConstantes() {}
}