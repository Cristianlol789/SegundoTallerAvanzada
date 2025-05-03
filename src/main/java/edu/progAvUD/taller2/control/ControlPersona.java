
package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.Crupier;
import edu.progAvUD.taller2.modelo.Jugador;
import edu.progAvUD.taller2.modelo.Persona;
import edu.progAvUD.taller2.modelo.Serializacion;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author Andres Felipe
 */

public class ControlPersona {

    private ControlPrincipal controlPrincipal;
    private Serializacion serializacion;
    private ArrayList<Persona> personas;
    private Random random;

    public ControlPersona(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.personas = new ArrayList<Persona>();
        random = new Random();
    }

    public void crearPersona(String identificador, String nombre, String cedula, String apellido, String telefono, String direccion, double dineroDouble, double cantidadFichasDouble) {
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
                    controlPrincipal.mostrarMensajeError("No se puede crear el crupier ya que no tiene cedula");
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
                    Persona jugador = new Jugador(nombre, cedula, apellido, direccion, telefono, dineroDouble, cantidadFichasDouble);
                    personas.add(jugador);
                }
            }
        }
    }

    public void contarCantidadPersonas() {
        int cantidadCrupieres = 0;
        int cantidadJugadores = 0;
        for (Persona personaEncontrada : personas) {
            if (personaEncontrada instanceof Crupier) {
                cantidadCrupieres += 1;
            } else {
                cantidadJugadores += 1;
            }
        }
        if (cantidadCrupieres < 1 || cantidadJugadores < 6) {
            controlPrincipal.mostrarMensajeError("No hay suficientes personas para empezar el juego verifique que minimo todas las personas tengan cedula para poder ser creadas (jugadores y crupier)");
            System.exit(0);
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

    public void escribirArchivoSerializado() {
        for (Persona personaEncontrada : personas) {
            if (personaEncontrada instanceof Crupier) {
                if (serializacion.getSalidaSerializacion() != null) {
                    try {
                        serializacion.escribirArchivoSerializado(personaEncontrada);
                    } catch (IOException ex) {
                        controlPrincipal.mostrarMensajeError("No se puede serializar la persona");
                    }
                }
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

    public void deserializacion() {
        Persona personaDeserializada = new Persona();
        personaDeserializada = (Persona) leerArchivoSerializado();
        if (personaDeserializada.getApellido() == null || personaDeserializada.getApellido().equals("") || personaDeserializada.getNombre() == null || personaDeserializada.getNombre().equals("")) {
            controlPrincipal.mostrarMensajeExito("Se ha deserializado la persona solo con cedula: \n La cedula es: " + personaDeserializada.getCedula());
            personas.add(personaDeserializada);
        } else {
            controlPrincipal.mostrarMensajeExito("Se ha deserializado la persona con nombre, cedula, apellido: \n La cedula es: " + personaDeserializada.getCedula() + " y su nombre es " + personaDeserializada.getNombre() + " " + personaDeserializada.getApellido());
            personas.add(personaDeserializada);
        }
    }

    public String jugadoresAleatoriosAJugar() {
        int posicionJugadorAleatorio = random.nextInt(personas.size());
        String cedulaPersonaAleatoria = personas.get(posicionJugadorAleatorio).getCedula();
        for (Persona personaEncontrada : personas) {
            if (cedulaPersonaAleatoria.equals(personaEncontrada.getCedula()) && personaEncontrada instanceof Jugador) {
                return cedulaPersonaAleatoria;
            }
        }
        return "Crupier";
    }

    public String darInformacionPersona(String cedula) {
        for (Persona persona : personas) {
            if (persona.getCedula().equals(cedula)) {
                String nombrePersona = persona.getNombre();
                String cedulaPersona = persona.getCedula();
                String apellidoPersona = persona.getApellido();
                if (nombrePersona == null || nombrePersona.equals("") || apellidoPersona == null || apellidoPersona.equals("")) {
                    return "La cedula del jugador es" + cedulaPersona;
                } else {
                    return "El nombre de la persona es " + nombrePersona + " " + apellidoPersona + " con cedula " + cedulaPersona;
                }
            }
        }
        return null;
    }

    public Persona buscarPersonaPorCedula(String cedula) {
        for (Persona persona : personas) {
            if (persona.getCedula().equals(cedula)) {
                return persona;
            }
        }
        return null;
    }

    public void comprarFichas(String cedula, int cantidadFichas) {
        double dinero = darCantidadDineroJugador(cedula);
        double dineroActual = dinero - (cantidadFichas * 1000);
        ((Jugador) buscarPersonaPorCedula(cedula)).setDinero(dineroActual);
        ((Jugador) buscarPersonaPorCedula(cedula)).setCantidadFichas(cantidadFichas);

    }

    public double darCantidadFichasJugador(String cedula) {
        for (Persona persona : personas) {
            if (persona.getCedula().equals(cedula)) {
                return ((Jugador) persona).getCantidadFichas();
            }
        }
        return 0;
    }

    public double darCantidadDineroJugador(String cedula) {
        for (Persona persona : personas) {
            if (persona.getCedula().equals(cedula)) {
                return ((Jugador) persona).getDinero();
            }
        }
        return 0;
    }

    public void personasCon0Dinero() {
        for (Persona persona : personas) {
            if (persona instanceof Jugador) {
                if (((Jugador) persona).getDinero() < 1000) {
                    controlPrincipal.mostrarMensajeError("No puede existir un jugador con menos de 1000 de dinero ya que este no podria jugar");
                    System.exit(0);
                }
            }
        }
    }
    
    public void cambiarNumeroFichasJugadorPorCedula(String cedula, double numeroDeFichas){
        for (Persona persona : personas) {
            if (persona instanceof Jugador) {
                ((Jugador) persona).setCantidadFichas(numeroDeFichas);
            }
        }
    }

}
