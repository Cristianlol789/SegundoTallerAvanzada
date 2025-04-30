package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.Crupier;
import edu.progAvUD.taller2.modelo.Jugador;
import edu.progAvUD.taller2.modelo.Persona;
import edu.progAvUD.taller2.modelo.Serializacion;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Andres Felipe
 */
public class ControlPersona {

    private ControlPrincipal controlPrincipal;
    private Persona crupier;
    private Persona[] jugadores;
    private int contadorJugadores;
    private Serializacion serializar;
    private FileOutputStream fileOut;
    private ObjectOutputStream salida;
    private FileInputStream fileIn;
    private ObjectInputStream entrada;

    public ControlPersona(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.jugadores = new Persona[6];
        this.contadorJugadores = 0;
        crearSerializacion();
    }

    public String crearPersona(String identificador, String nombre, String cedula, String apellido, String telefono, String direccion) {
        if (identificador.equals("Crupier")) {
            if (cedula == null) {
                controlPrincipal.mostrarMensajeError("No se puede crear el jurado ya que no tiene cedula");
            } else if (nombre == null || apellido == null) {
                Persona crupier = new Crupier(cedula);
            } else {
                Persona crupier = new Crupier(nombre, cedula, apellido);
            }
        } else if (identificador.equals("Jugador") && contadorJugadores != 5) {
            if (cedula == null) {
                controlPrincipal.mostrarMensajeError("No se puede crear el jugador ya que no tiene cedula");
            } else if (nombre == null || apellido == null || telefono == null || direccion == null) {
                Persona jugador = new Jugador(cedula);
                jugadores[contadorJugadores] = jugador;
                contadorJugadores += 1;
            } else {
                Persona jugador = new Jugador(direccion, telefono, nombre, cedula, apellido);
                jugadores[contadorJugadores] = jugador;
                contadorJugadores += 1;
            }
        } else if (contadorJugadores == 5) {
            controlPrincipal.mostrarMensajeError("No se puede crear mas de 6 jugadores");
        }
        return "0";
    }

    public String crearSerializacion() {
        serializar = new Serializacion();
        try {
            FileOutputStream fileOut = new FileOutputStream("crupier.bin");
            ObjectOutputStream salida = new ObjectOutputStream(fileOut);
            FileInputStream fileIn = new FileInputStream("crupier.bin");
            ObjectInputStream entrada = new ObjectInputStream(fileIn);
        } catch (FileNotFoundException ex) {
            return "No se ha encontrado el archivo";
        } catch (IOException ex) {
            return "Hay algun problema con el archivo";
        }
        return "0";
    }

    public String serializarCrupier() {
        escribirArchivoSerializado(crupier);
        return "El crupier ha sido serializado";
    }

    public String cerrarArchivoSerializadoOut() {
        if (salida != null) {
            try {
                salida.close();
            } catch (IOException ex) {
                return "No se puede cerrar la salida";
            }
        }
        return "0";
    }

    public String cerrarArchivoSerializadoIn() {
        if (entrada != null) {
            try {
                entrada.close();
            } catch (IOException ex) {
                return "No se puede cerrar la entrada";
            }
        }
        return "0";
    }

    public String escribirArchivoSerializado(Persona crupier) {
        if (salida != null) {
            try {
                salida.writeObject(crupier);
            } catch (IOException ex) {
                return "No se puede serializar la persona";
            }
        }
        return "0";
    }

    public Persona leerArchivoSerializado() {
        Persona persona = null;
        if (entrada != null) {
            try {
                persona = (Persona) entrada.readObject();
            } catch (EOFException eof) {
                controlPrincipal.mostrarMensajeExito("Fin de archivo: no quedan más objetos para leer.");
            } catch (IOException io) {
                controlPrincipal.mostrarMensajeError("Error de lectura/escritura: " + io.getMessage());
            } catch (ClassNotFoundException cnfe) {
                controlPrincipal.mostrarMensajeError("Error de clase: no se encontró la definición de Persona (" + cnfe.getMessage() + ").");
            }
        }
        return persona;
    }
}