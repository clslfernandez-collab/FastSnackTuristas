/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Cliente extends Persona {

    private String direccion;
    private boolean activo;

    public Cliente(String nombre,
                   String telefono,
                   String direccion,
                   boolean activo) {

        super(nombre, telefono);
        this.direccion = direccion;
        this.activo = activo;
    }

    public void mostrarCliente() {

        System.out.println("Nombre: " + getNombre());
        System.out.println("Telefono: " + getTelefono());
        System.out.println("Direccion: " + direccion);
        System.out.println("Activo: " + activo);
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}