package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.Crupier;
import edu.progAvUD.taller2.modelo.Jugador;
import edu.progAvUD.taller2.modelo.Persona;
import java.util.ArrayList;

/**
 *
 * @author Andres Felipe
 */
public class ControlPersona {

    private ControlPrincipal controlPrincipal;
    private ArrayList<Persona> personas;

    public ControlPersona(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.personas = new ArrayList<Persona>();
    }

    public void crearPersona(String identificador, String nombre, String cedula, String apellido, String telefono, String direccion) {
        boolean flag = true;
        for (Persona personaEncontrada : personas) {
            if (cedula.equals(personaEncontrada.getCedula())) {
                controlPrincipal.mostrarMensajeError("No se puede crear la persona porque ya existe alguien con esta cedula");
                flag = false;
            }
        }
        if (flag) {
            if (identificador.equals("Crupier")) {
                if (cedula == null || cedula.equals("")) {
                    controlPrincipal.mostrarMensajeError("No se puede crear el jurado ya que no tiene cedula");
                } else if (nombre == null || apellido == null || nombre.equals("") || apellido.equals("")) {
                    Persona crupier = new Crupier(cedula);
                    personas.add(crupier);
                    controlPrincipal.mostrarMensajeExito("Se creo la persona con solo cedula");
                } else {
                    Persona crupier = new Crupier(nombre, cedula, apellido);
                    personas.add(crupier);
                }
            } else if (identificador.equals("Jugador")) {
                if (cedula == null || cedula.equals("")) {
                    controlPrincipal.mostrarMensajeError("No se puede crear el jugador ya que no tiene cedula");
                } else if (nombre == null || apellido == null || telefono == null || direccion == null || nombre.equals("") || apellido.equals("") || telefono.equals("") || direccion.equals("")) {
                    Persona jugador = new Jugador(cedula);
                    controlPrincipal.mostrarMensajeExito("Se creo la persona con solo cedula");
                } else {
                    Persona jugador = new Jugador(direccion, telefono, nombre, cedula, apellido);
                    personas.add(jugador);
                }
            }
        }
    }
}
