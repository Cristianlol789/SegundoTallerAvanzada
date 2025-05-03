package edu.progAvUD.taller2.modelo;

import java.io.Serializable;

/**
 * La clase Crupier representa al encargado de gestionar el juego, cargado a
 * partir de un archivo de propiedades o mediante deserialización. Extiende de
 * Persona e implementa Serializable para permitir su persistencia.
 *
 * Es la entidad que compite directamente contra los jugadores.
 *
 * @author Andres Felipe
 */
public class Crupier extends Persona implements Serializable {

    /**
     * Crea un Crupier con nombre completo, cédula y apellido.
     *
     * @param nombre Nombre de pila del crupier.
     * @param cedula Documento de identidad del crupier.
     * @param apellido Apellido del crupier.
     */
    public Crupier(String nombre, String cedula, String apellido) {
        super(nombre, cedula, apellido);
    }

    /**
     * Crea un Crupier con únicamente la cédula, útil para carga parcial.
     *
     * @param cedula Documento de identidad del crupier.
     */
    public Crupier(String cedula) {
        super(cedula);
    }

}
