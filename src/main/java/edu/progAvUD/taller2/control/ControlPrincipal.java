package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.ConexionPropiedades;
import java.io.IOException;
import java.util.Properties;

public class ControlPrincipal {

    private ControlGrafico controlGrafico;
    private ControlCarta controlCarta;
    private ControlPersona controlPersona;
    private ConexionPropiedades conexionPropiedades;
    private Properties propiedadesJugadores;
    private Properties propiedadesCrupier;

    public ControlPrincipal() {
        controlGrafico = new ControlGrafico(this);
        controlPersona = new ControlPersona(this);
        controlCarta = new ControlCarta(this);

    }

    public void crearConexionPropiedades() {
        try {
            conexionPropiedades = new ConexionPropiedades(controlGrafico.pedirArchivo());
        } catch (Exception ex) {
            mostrarMensajeError("No se pudo crear la conexion correctamente");
            crearConexionPropiedades();
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
            propiedadesJugadores = conexionPropiedades.cargarPropiedades();
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
                mostrarMensajeExito("Jugador" + i + "\n"
                        + "nombre :" + nombre + "\n"
                        + "apellido :" + apellido + "\n"
                        + "cedula :" + cedula + "\n"
                        + "telefono :" + telefono + "\n"
                        + "direccion:" + direccion + "");
            }
        } catch (IOException ex) {
            mostrarMensajeError("No se pudo cargar el archivo propiedades de los jugadores");
        } catch (NumberFormatException ex) {
            mostrarMensajeError("El texto no es un valor valido");
        } catch (Exception ex) {
            mostrarMensajeError("Algun dato del jugador no corresponde");
        }
    }

    public void cargarCrupier() {
        try {
            propiedadesCrupier = conexionPropiedades.cargarPropiedades();
            String identificador = "Crupier";
            String nombre = propiedadesCrupier.getProperty("crupier.nombre");
            String apellido = propiedadesCrupier.getProperty("crupier.apellido");
            String cedula = propiedadesCrupier.getProperty("crupier.cedula");
            double cedulaDouble = Double.parseDouble(cedula);
            controlPersona.crearPersona(identificador, nombre, cedula, apellido, null, null);
            mostrarMensajeExito("Crupier \n"
                        + "nombre :" + nombre + "\n"
                        + "apellido :" + apellido + "\n"
                        + "cedula :" + cedula + "\n");
        } catch (Exception ex) {
            mostrarMensajeError("Algun dato del jugador no corresponde");
        }
    }

}
