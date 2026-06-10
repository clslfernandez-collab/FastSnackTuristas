/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO {

    // Trae todos los productos del inventario (de la tabla productos).
    public java.util.List<Object[]> listarProductos() {
        java.util.List<Object[]> lista = new java.util.ArrayList<>();
        String sql = "SELECT id, nombre, precio, stock FROM productos ORDER BY id";
        try (Connection con = Conexion.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    "$" + String.format("%.2f", rs.getDouble("precio")),
                    rs.getInt("stock")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    // Actualiza el stock de un producto por su id.
    public boolean actualizarStock(int id, int nuevoStock) {
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";
        try (Connection con = Conexion.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoStock);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }
}