/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Arrays;
import java.util.List;

public class Catalogo {

    public static List<Producto> getProductos() {
        return Arrays.asList(
            new Producto("Hamburguesa Clasica", 4.50, "hamburguesa.jpg"),
            new Producto("Combo Hamburguesa + Papas + Bebida", 6.99, "combo.png"),
            new Producto("Hot Dog Especial", 3.25, "hotdog.jpg"),
            new Producto("Papas Fritas Grandes", 2.50, "papas.jpg"),
            new Producto("Pizza Personal", 5.75, "pizza.jpg"),
            new Producto("Bebida 500ml", 1.50, "bebida.jpg"),
            new Producto("Agua sin gas 500ml", 1.00, "agua.png"),
            new Producto("Postre del dia", 2.75, "postre.jpg")
        );
    }
}