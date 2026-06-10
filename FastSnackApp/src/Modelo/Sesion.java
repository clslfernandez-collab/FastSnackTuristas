/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Sesion {

    public static Usuario usuarioActual = null;
    public static List<ItemPedido> carrito = new ArrayList<>();

    public static void limpiarCarrito() {
        carrito = new ArrayList<>();
    }

    public static int totalUnidades() {
        int t = 0;
        for (ItemPedido it : carrito) t += it.getCantidad();
        return t;
    }

    public static double subtotal() {
        double s = 0;
        for (ItemPedido it : carrito) s += it.getSubtotal();
        return s;
    }
}