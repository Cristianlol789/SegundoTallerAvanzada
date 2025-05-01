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
    private int contadorRondas;
    private ArrayList<String> cedulasJugadoresEnMano;
    private ArrayList<Carta> cartasJugador1;
    private ArrayList<Carta> cartasJugador2;
    private ArrayList<Carta> cartasCrupier;
    private ArrayList<Carta> dividirCartasJugador1;
    private ArrayList<Carta> dividirCartasJugador2;

    public ControlPrincipal() {
        controlGrafico = new ControlGrafico(this);
        controlPersona = new ControlPersona(this);
        controlCarta = new ControlCarta(this);
        mazo = new ArrayList<Carta>();
        cedulasJugadoresEnMano = new ArrayList<String>();
        cartasJugador1 = new ArrayList<Carta>();
        cartasJugador2 = new ArrayList<Carta>();
        cartasCrupier = new ArrayList<Carta>();
        dividirCartasJugador1 = new ArrayList<Carta>();
        dividirCartasJugador2 = new ArrayList<Carta>();
        contadorRondas = 1;
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
            }
            controlGrafico.mostrarMensajeExito("Se han creado correctamente los jugadores");
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
            mostrarMensajeExito("Se ha creado correctamente el crupier");
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
        for (String cedulaBuscada : cedulasJugadoresEnMano) {
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
                cedulasJugadoresEnMano.add(cedula1);
                cedulasJugadoresEnMano.add(cedula2);
                informacionJugadores();
                flag = false;
            }
        } while (flag);
    }

    public void informacionJugadores() {
        if (contadorRondas == 1) {
            String persona1 = cedulasJugadoresEnMano.get(0);
            String persona2 = cedulasJugadoresEnMano.get(1);
            controlGrafico.mostrarMensajeExito("El jugador uno va ha tomar el papel de: \n" + controlPersona.buscarPersonaPorCedula(persona1));
            controlGrafico.mostrarMensajeExito("El jugador dos va ha tomar el papel de: \n" + controlPersona.buscarPersonaPorCedula(persona2));
        } else if (contadorRondas == 2) {
            String persona1 = cedulasJugadoresEnMano.get(2);
            String persona2 = cedulasJugadoresEnMano.get(3);
            controlGrafico.mostrarMensajeExito("El jugador uno va ha tomar el papel de: \n" + controlPersona.buscarPersonaPorCedula(persona1));
            controlGrafico.mostrarMensajeExito("El jugador dos va ha tomar el papel de: \n" + controlPersona.buscarPersonaPorCedula(persona2));
        } else {
            String persona1 = cedulasJugadoresEnMano.get(4);
            String persona2 = cedulasJugadoresEnMano.get(5);
            controlGrafico.mostrarMensajeExito("El jugador uno va ha tomar el papel de: \n" + controlPersona.buscarPersonaPorCedula(persona1));
            controlGrafico.mostrarMensajeExito("El jugador dos va ha tomar el papel de: \n" + controlPersona.buscarPersonaPorCedula(persona2));
        }
    }

    public void conteoJugadores() {
        controlPersona.contarCantidadPersonas();
    }

    public void pagosBlackJack(int valorCartasCrupier, int valorCartasJugador1, double apuestaJugador1, String seguroJugador1, double seguroApostado1, int valorCartasJugador2, double apuestaJugador2, String seguroJugador2, double seguroApostado2) {
        double unidades1 = 0;
        double unidades2 = 0;
        double netoSeguro1 = 0;
        double netoSeguro2 = 0;

        boolean crupierBlackJack = valorCartasCrupier == 21;
        boolean jugador1BlackJack = valorCartasJugador1 == 21;
        boolean jugador2BlackJack = valorCartasJugador2 == 21;

        if (crupierBlackJack) {
            if ("SI".equalsIgnoreCase(seguroJugador1)) {
                netoSeguro1 = seguroApostado1 * 2;
            }
            if ("SI".equalsIgnoreCase(seguroJugador2)) {
                netoSeguro2 = seguroApostado2 * 2;
            }
        } else {
            if ("SI".equalsIgnoreCase(seguroJugador1)) {
                netoSeguro1 = -seguroApostado1;
            }
            if ("SI".equalsIgnoreCase(seguroJugador2)) {
                netoSeguro2 = -seguroApostado2;
            }
        }
        if (valorCartasCrupier > 21) {
            if (valorCartasJugador1 <= 21) {
                unidades1 = 1;
            } else {
                unidades1 = -1;
            }
            if (valorCartasJugador2 <= 21) {
                unidades2 = 1;
            } else {
                unidades2 = -1;
            }
        } else {
            if (valorCartasJugador1 > 21) {
                unidades1 = -1;
            } else if (valorCartasJugador1 > valorCartasCrupier) {
                if (jugador1BlackJack && !crupierBlackJack) {
                    unidades1 = 1.5;
                } else {
                    unidades1 = 1;
                }
            } else if (valorCartasJugador1 == valorCartasCrupier) {
                unidades1 = 0;
            } else {
                unidades1 = -1;
            }
            if (valorCartasJugador2 > 21) {
                unidades2 = -1;
            } else if (valorCartasJugador2 > valorCartasCrupier) {
                if (jugador2BlackJack && !crupierBlackJack) {
                    unidades2 = 1.5;
                } else {
                    unidades2 = 1;
                }
            } else if (valorCartasJugador2 == valorCartasCrupier) {
                unidades2 = 0;
            } else {
                unidades2 = -1;
            }
        }

        double pago1 = unidades1 * apuestaJugador1 + netoSeguro1;
        double pago2 = unidades2 * apuestaJugador2 + netoSeguro2;
    }

    public void obtenerPosicion(String nombrePropietarioCarta) {
        Carta cartaAleatoria = mazo.getFirst();
        if (nombrePropietarioCarta.equals("Jugador1")) {
            cartasJugador1.add(cartaAleatoria);
        } else if (nombrePropietarioCarta.equals("Jugador2")) {
            cartasJugador2.add(cartaAleatoria);
        } else if (nombrePropietarioCarta.equals("Jugador1Division")) {
            dividirCartasJugador1.add(cartaAleatoria);
            cartasJugador1.removeLast();
        } else if (nombrePropietarioCarta.equals("Jugador2Division")) {
            dividirCartasJugador2.add(cartaAleatoria);
            cartasJugador2.removeLast();
        } else if (nombrePropietarioCarta.equals("Jugador1NuevoMazo")) {
            dividirCartasJugador1.add(cartaAleatoria);
        } else if (nombrePropietarioCarta.equals("Jugador2NuevoMazo")) {
            dividirCartasJugador2.add(cartaAleatoria);
        } else if (nombrePropietarioCarta.equals("Crupier")) {
            cartasCrupier.add(cartaAleatoria);
        }
        mazo.removeFirst();
    }

    public int sumarCartas(ArrayList<Carta> mano) {
        int suma = 0;
        int cantidadAses = 0;
        for (Carta carta : mano) {
            String denominacion = carta.getDenominacion().name();
            switch (denominacion) {
                case "J":
                case "Q":
                case "K":
                    suma += 10;
                    break;
                case "A":
                    suma += 11;
                    cantidadAses++;
                    break;
                default:
                    suma += Integer.parseInt(denominacion);
            }
        }
        while (suma > 21 && cantidadAses > 0) {
            suma -= 10;
            cantidadAses--;
        }
        return suma;
    }
}
