/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 * Excepcion PERSONALIZADA. Se lanza (throw) cuando un documento
 * (cedula, RUC, etc.) no cumple el formato requerido.
 * Hereda de Exception, por eso es una excepcion propia del sistema.
 */
public class DocumentoInvalidoException extends Exception {

    public DocumentoInvalidoException(String mensaje) {
        super(mensaje);
    }
}