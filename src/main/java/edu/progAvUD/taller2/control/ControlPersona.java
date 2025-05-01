package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.Crupier;
import edu.progAvUD.taller2.modelo.Jugador;
import edu.progAvUD.taller2.modelo.Persona;
import edu.progAvUD.taller2.modelo.Serializacion;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Andres Felipe
 */
public class ControlPersona {

    private ControlPrincipal controlPrincipal;
    private Serializacion serializacion;
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
                    personas.add(jugador);
                    controlPrincipal.mostrarMensajeExito("Se creo la persona con solo cedula");
                } else {
                    Persona jugador = new Jugador(direccion, telefono, nombre, cedula, apellido);
                    personas.add(jugador);
                }
            }
        }
    }

    public void crearPersonaSerializacion() {
        try {
            File archivo = controlPrincipal.archivoSerializado();
            serializacion = new Serializacion(archivo);
        } catch (FileNotFoundException ex) {
            controlPrincipal.mostrarMensajeError("El archivo no ha sido encontrado");
        } catch (IOException ex) {
            controlPrincipal.mostrarMensajeError("No se pudo cargar el archivo serializado");
        }
    }

    public void cerrarArchivoSerializadoOut() {
        if (serializacion.getSalidaSerializacion() != null) {
            try {
                serializacion.cerrarArchivoSerializadoOut();
            } catch (IOException ex) {
                controlPrincipal.mostrarMensajeError("No se puede cerrar la salida");
            }
        }
    }

    public void cerrarArchivoSerializadoIn() {
        if (serializacion.getEntradaSerializacion() != null) {
            try {
                serializacion.cerrarArchivoSerializadoIn();
            } catch (IOException ex) {
                controlPrincipal.mostrarMensajeError("No se puede cerrar la entrada");
            }
        }
    }

    public void escribirArchivoSerializado(Persona persona) {
        if (serializacion.getSalidaSerializacion() != null) {
            try {
                serializacion.escribirArchivoSerializado(persona);
            } catch (IOException ex) {
                controlPrincipal.mostrarMensajeError("No se puede serializar la persona");
            }
        }
    }

    public Persona leerArchivoSerializado() {
        Persona persona = null;
        if (serializacion.getEntradaSerializacion() != null) {
            try {
                persona = (Persona) serializacion.leerArchivoSerializado();
            } catch (EOFException eof) {
                controlPrincipal.mostrarMensajeError("Fin de archivo inesperado al leer la persona.");
            } catch (IOException io) {
                controlPrincipal.mostrarMensajeError("Error al leer el archivo serializado: " + io.getMessage());
            } catch (ClassNotFoundException cnfe) {
                controlPrincipal.mostrarMensajeError("No se ha encontrado la clase");
            }
        }
        return persona;
    }

    public void desSerializacion() {
        for (Persona personaEncontrada : personas) {
            escribirArchivoSerializado(personaEncontrada);
            Persona personaDesSerializada = new Persona();
            personaDesSerializada = (Persona) leerArchivoSerializado();
            controlPrincipal.mostrarMensajeExito("Se ha deserializado");
            controlPrincipal.mostrarMensajeExito("Persona DesSerializada: " + personaDesSerializada.getNombre() + " " + personaDesSerializada.getApellido());
        }
    }

}
