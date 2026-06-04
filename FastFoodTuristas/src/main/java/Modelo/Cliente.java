/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Cliente extends Persona {

    private String direccion;
    private String genero;
    private String ciudad;
    private boolean activo;

    public Cliente() {
    }

    public Cliente(String nombre,
                   String telefono,
                   String direccion,
                   String genero,
                   String ciudad,
                   boolean activo) {

        super(nombre, telefono);

        this.direccion = direccion;
        this.genero = genero;
        this.ciudad = ciudad;
        this.activo = activo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return getNombre() + " - " + ciudad;
    }
}