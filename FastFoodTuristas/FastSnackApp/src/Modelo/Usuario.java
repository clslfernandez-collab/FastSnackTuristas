/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 * Usuario HEREDA de Persona (extends Persona).
 * Reutiliza nombre y telefono de la clase padre, y agrega sus propios datos.
 */
public class Usuario extends Persona {

    private int id;
    private String tipoUsuario;   // "CLIENTE" o "EMPLEADO"
    private String correo;
    private String contrasena;
    private String tipoDoc;
    private String documento;
    private String direccion;

    public Usuario() {
        super();
    }

    public Usuario(String tipoUsuario, String nombre, String correo, String contrasena,
                   String telefono, String tipoDoc, String documento, String direccion) {
        super(nombre, telefono);   // llama al constructor de Persona
        this.tipoUsuario = tipoUsuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.direccion = direccion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Implementa el metodo abstracto de Persona (POLIMORFISMO)
    @Override
    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getTipoDoc() { return tipoDoc; }
    public void setTipoDoc(String tipoDoc) { this.tipoDoc = tipoDoc; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}