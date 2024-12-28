/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tapir.goose;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named
@SessionScoped
public class BeanEjemplo implements Serializable {

    private static final Logger logger = LogManager.getLogger(BeanEjemplo.class);
    
    private String nombre;
    private String mensaje;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void saludar() {
        logger.info("fn saludar param nombre: {}", nombre);
        this.mensaje = "¡Hola, " + nombre + "!";
    }
}
