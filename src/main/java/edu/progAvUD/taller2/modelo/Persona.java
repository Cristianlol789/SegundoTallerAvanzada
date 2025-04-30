/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.taller2.modelo;

/**
 *
 * @author Andres Felipe
 */
public class Persona {
    
    protected String nombre;
    protected String cedula;
    protected String apellido;

    public Persona(String nombre, String cedula, String apellido) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.apellido = apellido;
    }

    public Persona(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    
}
