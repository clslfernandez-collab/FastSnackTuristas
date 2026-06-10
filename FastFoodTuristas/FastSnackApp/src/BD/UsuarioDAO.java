/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Modelo.DocumentoInvalidoException;

public class UsuarioDAO {

    public boolean registrar(Usuario u) {
        String sql = "INSERT INTO usuarios "
                + "(tipo_usuario, nombre, correo, contrasena, telefono, tipo_doc, documento, direccion) "
                + "VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = Conexion.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getTipoUsuario());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getContrasena());
            ps.setString(5, u.getTelefono());
            ps.setString(6, u.getTipoDoc());
            ps.setString(7, u.getDocumento());
            ps.setString(8, u.getDireccion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    public Usuario login(String documento, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE documento = ? AND contrasena = ?";
        try (Connection con = Conexion.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, documento);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setTipoUsuario(rs.getString("tipo_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contrasena"));
                u.setTelefono(rs.getString("telefono"));
                u.setTipoDoc(rs.getString("tipo_doc"));
                u.setDocumento(rs.getString("documento"));
                u.setDireccion(rs.getString("direccion"));
                return u;
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
        }
        return null;
    }

    public boolean existeDocumento(String documento) {
        String sql = "SELECT id FROM usuarios WHERE documento = ?";
        try (Connection con = Conexion.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error al verificar documento: " + e.getMessage());
            return false;
        }
    }

    public static boolean validarDocumento(String tipoDoc, String doc) {
        if (doc == null) return false;
        doc = doc.trim();
        switch (tipoDoc) {
            case "CEDULA":
                return doc.matches("\\d{10}");
            case "RUC":
                return doc.matches("\\d{13}");
            case "PASAPORTE":
            case "OTRO":
                return doc.matches("[A-Za-z0-9]{5,}");
            default:
                return false;
        }
    }

    public static boolean validarCorreo(String correo) {
        return correo != null && correo.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
    }
    
    // Valida el documento y LANZA (throw) una excepcion personalizada si es invalido.
    // Demuestra el uso de throw con una excepcion propia.
    public static void validarDocumentoConExcepcion(String tipoDoc, String doc)
            throws DocumentoInvalidoException {

        if (!validarDocumento(tipoDoc, doc)) {
            switch (tipoDoc) {
                case "CEDULA":
                    throw new DocumentoInvalidoException("La cedula debe tener exactamente 10 digitos.");
                case "RUC":
                    throw new DocumentoInvalidoException("El RUC debe tener exactamente 13 digitos.");
                default:
                    throw new DocumentoInvalidoException("El documento debe tener al menos 5 caracteres.");
            }
        }
        
    }
    
    // Trae todos los usuarios de un tipo ("CLIENTE" o "EMPLEADO") para el panel del empleado.
    public java.util.List<Usuario> listarPorTipo(String tipo) {
        java.util.List<Usuario> lista = new java.util.ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE tipo_usuario = ?";
        try (Connection con = Conexion.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setTipoUsuario(rs.getString("tipo_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setTelefono(rs.getString("telefono"));
                u.setTipoDoc(rs.getString("tipo_doc"));
                u.setDocumento(rs.getString("documento"));
                u.setDireccion(rs.getString("direccion"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }
    
}