/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 * Clase ABSTRACTA base. No se puede instanciar directamente (no se puede hacer
 * new Persona()). Define los datos y comportamientos comunes a todas las personas
 * del sistema (clientes y empleados). Las subclases heredan de ella.
 */
public abstract class Persona {

    protected String nombre;
    protected String telefono;

    public Persona() {
    }

    public Persona(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Metodo ABSTRACTO: cada subclase DEBE implementarlo a su manera.
    // Esto demuestra abstraccion y polimorfismo.
    public abstract String getTipoUsuario();
}
