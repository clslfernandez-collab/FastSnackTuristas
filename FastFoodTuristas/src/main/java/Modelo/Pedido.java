/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Pedido {

    private Cliente cliente;
    private ArrayList<Producto> productos;
    private EstadoPedido estado;
    private LocalDateTime fecha;

    public Pedido(Cliente cliente) {

        this.cliente = cliente;
        this.productos = new ArrayList<>();
        this.estado = EstadoPedido.PENDIENTE;
        this.fecha = LocalDateTime.now();
    }

    public void agregarProducto(Producto p) {

        productos.add(p);
    }

    public double calcularTotal() {

        double total = 0;

        for (Producto p : productos) {
            total += p.getPrecio();
        }

        return total;
    }

    public void cambiarEstado(EstadoPedido estado) {

        this.estado = estado;
    }

    public EstadoPedido getEstado() {

        return estado;
    }

    public void mostrarPedido() {

        System.out.println("Cliente: " + cliente.getNombre());

        for (Producto p : productos) {

            System.out.println(
                    p.getNombre() +
                    " - $" +
                    p.getPrecio());
        }

        System.out.println("Total: $" + calcularTotal());
        System.out.println("Estado: " + estado);
        System.out.println("Fecha: " + fecha);
    }
}