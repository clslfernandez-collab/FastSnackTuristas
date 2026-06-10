/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PedidoDAO {

    public String generarNumeroPedido() {
        String sql = "SELECT COUNT(*) AS total FROM pedidos";
        int siguiente = 1;
        try (Connection con = Conexion.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                siguiente = rs.getInt("total") + 1;
            }
        } catch (SQLException e) {
            System.err.println("Error al generar numero de pedido: " + e.getMessage());
        }
        return String.format("#%04d", siguiente);
    }

    public boolean guardar(String numero, String docCliente, String detalle,
                           double subtotal, double iva, double envio, double total,
                           String metodoPago, String personalizacion, String tiempoEstimado) {

        String sql = "INSERT INTO pedidos "
                + "(numero_pedido, documento_cli, detalle, subtotal, iva, envio, total, "
                + " metodo_pago, personalizacion, tiempo_estimado) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (Connection con = Conexion.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, numero);
            ps.setString(2, docCliente);
            ps.setString(3, detalle);
            ps.setDouble(4, subtotal);
            ps.setDouble(5, iva);
            ps.setDouble(6, envio);
            ps.setDouble(7, total);
            ps.setString(8, metodoPago);
            ps.setString(9, personalizacion);
            ps.setString(10, tiempoEstimado);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar pedido: " + e.getMessage());
            return false;
        }
    }
    
    // Trae todos los pedidos para mostrarlos en el panel del empleado.
    // Cada fila es un arreglo de objetos (numero, cliente, total, metodo, fecha).
    public java.util.List<Object[]> listarPedidos() {
        java.util.List<Object[]> lista = new java.util.ArrayList<>();
        String sql = "SELECT numero_pedido, documento_cli, detalle, total, metodo_pago, "
                   + "tiempo_estimado, fecha FROM pedidos ORDER BY id DESC";
        try (Connection con = Conexion.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("numero_pedido"),
                    rs.getString("documento_cli"),
                    rs.getString("detalle"),
                    "$" + String.format("%.2f", rs.getDouble("total")),
                    rs.getString("metodo_pago"),
                    rs.getString("tiempo_estimado"),
                    rs.getString("fecha")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
        }
        return lista;
    }
    
}
