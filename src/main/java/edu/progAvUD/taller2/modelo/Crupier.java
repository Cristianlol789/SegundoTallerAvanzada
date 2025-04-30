/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.taller2.modelo;

import java.io.Serializable;

/**
 *
 * @author Andres Felipe
 */
public class Crupier extends Persona implements Serializable {

    public Crupier(String nombre, String cedula, String apellido) {
        super(nombre, cedula, apellido);
    }

    public Crupier(String cedula) {
        super(cedula);
    }
    
}