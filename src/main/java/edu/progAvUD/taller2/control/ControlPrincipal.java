package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.Carta;
import edu.progAvUD.taller2.modelo.ConexionPropiedades;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class ControlPrincipal {

    private ControlGrafico controlGrafico;
    private ControlCarta controlCarta;
    private ControlPersona controlPersona;
    private ConexionPropiedades conexionPropiedades;
    private Properties propiedadesJugadores;
    private Properties propiedadesCrupier;
    private ArrayList<Carta> mazo;
    private ArrayList<String> cedulas;

    public ControlPrincipal() {
        controlGrafico = new ControlGrafico(this);
        controlPersona = new ControlPersona(this);
        controlCarta = new ControlCarta(this);
        mazo = new ArrayList<Carta>();
        cedulas = new ArrayList<String>();
    }

    public void crearConexionPropiedades() {
        try {
            conexionPropiedades = new ConexionPropiedades(controlGrafico.pedirArchivo());
        } catch (Exception ex) {
            mostrarMensajeError("No se pudo crear la conexion correctamente");
            crearConexionPropiedades();
        }
    }

    public File archivoSerializado() {
        File archivo = null;
        try {
            archivo = controlGrafico.pedirArchivo();
        } catch (Exception ex) {
            mostrarMensajeError("No se ha podido encontrar el archivo");
            archivoSerializado();
        }
        return archivo;
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
                String dinero = propiedadesJugadores.getProperty("jugador" + i + ".dinero");
                double dineroDouble = Double.parseDouble(dinero);
                String cantidadFichas = propiedadesJugadores.getProperty("jugador" + i + ".cantidadFichas");
                double cantidadFichasDouble = Double.parseDouble(cantidadFichas);
                controlPersona.crearPersona(identificador, nombre, cedula, apellido, telefono, direccion, dineroDouble, cantidadFichasDouble);
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
            controlPersona.crearPersona(identificador, nombre, cedula, apellido, null, null, 0, 0);
            mostrarMensajeExito("Crupier \n"
                    + "nombre :" + nombre + "\n"
                    + "apellido :" + apellido + "\n"
                    + "cedula :" + cedula + "\n");
        } catch (Exception ex) {
            mostrarMensajeError("Algun dato del jugador no corresponde");
        }
    }

    public void crearMazo() {
        for (Carta.Palo palo : Carta.Palo.values()) {
            for (Carta.Denominacion denominacion : Carta.Denominacion.values()) {
                Carta carta = controlCarta.crearCarta(palo.name(), denominacion.name());
                mazo.add(carta);
            }
        }
        Collections.shuffle(mazo);
    }

    public boolean buscarCedulasRepetidas(String cedula) {
        boolean flag = true;
        for (String cedulaBuscada : cedulas) {
            if (cedulaBuscada.equals(cedula)) {
                flag = false;
                return flag;
            }
        }
        return flag;
    }

    public String[] cedulasJugadores() {
        String cedula1;
        String cedula2;
        do {
            cedula1 = controlPersona.jugadoresAleatoriosAJugar();
        } while (cedula1.equals("Crupier"));
        do {
            cedula2 = controlPersona.jugadoresAleatoriosAJugar();
        } while (cedula2.equals("Crupier") || cedula2.equals(cedula1));
        String[] cedulasLocal = new String[2];
        cedulasLocal[0] = cedula1;
        cedulasLocal[1] = cedula2;
        return cedulasLocal;
    }

    public void seleccionarJugadores() {
        boolean flag = true;
        do {
            String[] cedulasLocal = cedulasJugadores();
            String cedula1 = cedulasLocal[0];
            String cedula2 = cedulasLocal[1];
            boolean flag1 = buscarCedulasRepetidas(cedula1);
            boolean flag2 = buscarCedulasRepetidas(cedula2);
            if (flag1 && flag2) {
                cedulas.add(cedula1);
                cedulas.add(cedula2);
                flag = false;
            }
        } while (flag);
    }
}
