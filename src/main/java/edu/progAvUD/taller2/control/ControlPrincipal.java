package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.Conexion;
import java.io.IOException;
import java.util.Properties;

public class ControlPrincipal {

    private ControlGrafico controlGrafico;
    private ControlCarta controlCarta;
    private ControlPersona controlPersona;
    private Conexion conexion;
    private Properties propiedadesJugadores;

    public ControlPrincipal() {
        controlGrafico = new ControlGrafico(this);
        controlPersona = new ControlPersona(this);
        controlCarta = new ControlCarta(this);
        try {
            conexion = new Conexion(controlGrafico.pedirArchivo());
        } catch (Exception ex) {
            mostrarMensajeError("No se pudo crear la conexion correctamente");
            System.exit(0);
        }
    }

    public void mostrarMensajeError(String mensaje) {
        controlGrafico.mostrarMensajeError(mensaje);
    }

    public void mostrarMensajeExito(String mensaje) {
        controlGrafico.mostrarMensajeExito(mensaje);
    }

    public void cargarJugadores() {
        try {
            propiedadesJugadores = conexion.cargarPropiedades();
            int cantidadJugadores = Integer.parseInt(propiedadesJugadores.getProperty("cantidadJugadores"));
            for (int i = 1; i <= cantidadJugadores; i++) {
                String identificador = "Jugador";
                String nombre = propiedadesJugadores.getProperty("jugador" + i + ".nombre");
                String apellido = propiedadesJugadores.getProperty("jugador" + i + ".apellido");
                String cedula = propiedadesJugadores.getProperty("jugador" + i + ".cedula");
                double cedulaDouble = Double.parseDouble(cedula);
                String telefono = propiedadesJugadores.getProperty("jugador" + i + ".telefono");
                String direccion = propiedadesJugadores.getProperty("jugador" + i + ".direccion");
                controlPersona.crearPersona(identificador, nombre, cedula, apellido, telefono, direccion);
            }
        } catch (IOException ex) {
            mostrarMensajeError("No se pudo cargar el archivo propiedades de los jugadores");
        } catch (NumberFormatException ex) {
            mostrarMensajeError("El texto no es un valor valido");
        } catch (Exception ex) {
            mostrarMensajeError("Algun dato del jugador no corresponde");
        }
    }
    
    public void cargarCrupier(){
        
    }

}