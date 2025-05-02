
package edu.progAvUD.taller2.control;

import edu.progAvUD.taller2.modelo.ConexionArchivoAleatorio;
import edu.progAvUD.taller2.modelo.Carta;
import edu.progAvUD.taller2.modelo.ConexionPropiedades;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class ControlPrincipal {

    private ControlGrafico controlGrafico;
    private ControlCarta controlCarta;
    private ControlPersona controlPersona;
    private ConexionPropiedades conexionPropiedades;
    private Properties propiedadesJugadores;
    private Properties propiedadesCrupier;
    private ConexionArchivoAleatorio archivoAleatorio;

    private ArrayList<Carta> mazo;
    private int contadorRondas;
    private ArrayList<String> cedulasJugadoresEnMano;
    private ArrayList<Carta> cartasJugador1;
    private ArrayList<Carta> cartasJugador2;
    private ArrayList<Carta> cartasCrupier;
    private ArrayList<Carta> dividirCartasJugador1;
    private ArrayList<Carta> dividirCartasJugador2;
    private HashMap<Integer, String> ganadorRonda;
    private double fichasApostadasJugador1;
    private double fichasApostadasJugador2;
    private String turnoJugador;
    private String[] datosGanador;

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
        ganadorRonda = new HashMap<Integer, String>();
        datosGanador = new String[3];
    }

    public void crearConexionPropiedades() {
        try {
            conexionPropiedades = new ConexionPropiedades(controlGrafico.pedirArchivo());
        } catch (Exception ex) {
            controlGrafico.mostrarMensajeError("No se pudo crear la conexion correctamente");
            crearConexionPropiedades();
        }
    }

    public File archivoSerializado() {
        File archivo = null;
        try {
            archivo = controlGrafico.pedirArchivoSerializado();
        } catch (Exception ex) {
            controlGrafico.mostrarMensajeError("No se ha podido encontrar el archivo");
            archivoSerializado();
        }
        return archivo;
    }

    public void crearArchivoAleatorio() {
        try {
            archivoAleatorio = new ConexionArchivoAleatorio(controlGrafico.pedirArchivoAleatorio());
        } catch (FileNotFoundException fnfe) {
            controlGrafico.mostrarMensajeError("No se ha encontrado el archivo");
            crearArchivoAleatorio();
        }
    }

    public void escrituraArchivoAleatorio() {
        try {
            for (Map.Entry<Integer, String> entry : ganadorRonda.entrySet()) {
                String ronda = "Se jugó la ronda " + entry.getKey() + " y esta es la información de los ganadores:";
                archivoAleatorio.escribirArchivoAleatorio(ronda, entry.getValue());
            }
        } catch (FileNotFoundException fnfe) {
            controlGrafico.mostrarMensajeError("El archivo no ha sido encontrado");
        } catch (IOException ioe) {
            controlGrafico.mostrarMensajeError("Hay un error al momento de escribir el archivo");
        }
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
                controlPersona.crearPersona(identificador, nombre, cedula, apellido, telefono, direccion, dineroDouble, 0);
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

    public String[] darcedulasJugadoresSeleccionados() {
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
            String[] cedulasLocal = darcedulasJugadoresSeleccionados();
            String cedula1 = cedulasLocal[0];
            String cedula2 = cedulasLocal[1];
            boolean flag1 = buscarCedulasRepetidas(cedula1);
            boolean flag2 = buscarCedulasRepetidas(cedula2);
            if (flag1 && flag2) {
                cedulasJugadoresEnMano.add(cedula1);
                cedulasJugadoresEnMano.add(cedula2);
                darInformacionJugadores();
                flag = false;
            }
        } while (flag);
    }

    public void darInformacionJugadores() {
        if (contadorRondas == 1) {
            String persona1 = cedulasJugadoresEnMano.get(0);
            String persona2 = cedulasJugadoresEnMano.get(1);
            controlGrafico.mostrarMensajeExito("El jugador uno va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona1));
            controlGrafico.mostrarMensajeExito("El jugador dos va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona2));
            controlGrafico.mostraDatosJugador1(persona1, darCantidadFichasJugador(persona1));
            controlGrafico.mostraDatosJugador2(persona2, darCantidadFichasJugador(persona2));
        } else if (contadorRondas == 2) {
            String persona1 = cedulasJugadoresEnMano.get(2);
            String persona2 = cedulasJugadoresEnMano.get(3);
            controlGrafico.mostrarMensajeExito("El jugador uno va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona1));
            controlGrafico.mostrarMensajeExito("El jugador dos va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona2));
            controlGrafico.mostraDatosJugador1(persona1, darCantidadFichasJugador(persona1));
            controlGrafico.mostraDatosJugador2(persona2, darCantidadFichasJugador(persona2));
        } else {
            String persona1 = cedulasJugadoresEnMano.get(4);
            String persona2 = cedulasJugadoresEnMano.get(5);
            controlGrafico.mostrarMensajeExito("El jugador uno va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona1));
            controlGrafico.mostrarMensajeExito("El jugador dos va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona2));
            controlGrafico.mostraDatosJugador1(persona1, darCantidadFichasJugador(persona1));
            controlGrafico.mostraDatosJugador2(persona2, darCantidadFichasJugador(persona2));
        }
    }

    public String darCedulaJugadoresEnPartida(String jugador) {
        if (contadorRondas == 1) {
            String persona1 = cedulasJugadoresEnMano.get(0);
            String persona2 = cedulasJugadoresEnMano.get(1);
            if ("Jugador1".equals(jugador)) {
                return persona1;
            } else if ("Jugador2".equals(jugador)) {
                return persona2;
            }
        } else if (contadorRondas == 2) {
            String persona1 = cedulasJugadoresEnMano.get(2);
            String persona2 = cedulasJugadoresEnMano.get(3);
            if ("Jugador1".equals(jugador)) {
                return persona1;
            } else if ("Jugador2".equals(jugador)) {
                return persona2;
            }
        } else {
            String persona1 = cedulasJugadoresEnMano.get(4);
            String persona2 = cedulasJugadoresEnMano.get(5);
            if ("Jugador1".equals(jugador)) {
                return persona1;
            } else if ("Jugador2".equals(jugador)) {
                return persona2;
            }
        }
        return null;
    }

    public void hacerPagosBlackJack(int valorCartasCrupier,
            int valorCartasJugador1, double apuestaJugador1, String seguroJugador1, double seguroApostado1,
            int valorCartasJugador2, double apuestaJugador2, String seguroJugador2, double seguroApostado2) {
        double unidades1 = 0;
        double unidades2 = 0;
        double netoSeguro1 = 0;
        double netoSeguro2 = 0;

        boolean crupierBlackJack = valorCartasCrupier == 21;
        boolean jugador1BlackJack = valorCartasJugador1 == 21;
        boolean jugador2BlackJack = valorCartasJugador2 == 21;

        // Cálculo del seguro
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

        // Comparación de resultados
        if (valorCartasCrupier > 21) {
            unidades1 = (valorCartasJugador1 <= 21) ? 1 : -1;
            unidades2 = (valorCartasJugador2 <= 21) ? 1 : -1;
        } else {
            unidades1 = calcularUnidades(valorCartasJugador1, valorCartasCrupier, jugador1BlackJack, crupierBlackJack);
            unidades2 = calcularUnidades(valorCartasJugador2, valorCartasCrupier, jugador2BlackJack, crupierBlackJack);
        }

        // Pagos en fichas y dinero
        double pagoFichas1 = unidades1 * apuestaJugador1 + netoSeguro1;
        double pagoFichas2 = unidades2 * apuestaJugador2 + netoSeguro2;
        double dinero1 = pagoFichas1 * 1000;
        double dinero2 = pagoFichas2 * 1000;

        // Determinar ganador(es)
        String resultado;
        boolean gana1 = pagoFichas1 > 0;
        boolean gana2 = pagoFichas2 > 0;

        if (gana1 && gana2) {
            resultado = String.format("Ganan Jugador 1 y Jugador 2:\nJugador 1 gana %.2f fichas (%.0f en dinero)\nJugador 2 gana %.2f fichas (%.0f en dinero)",
                    pagoFichas1, dinero1, pagoFichas2, dinero2);
        } else if (gana1) {
            resultado = String.format("Gana Jugador 1: %.2f fichas (%.0f en dinero)", pagoFichas1, dinero1);
        } else if (gana2) {
            resultado = String.format("Gana Jugador 2: %.2f fichas (%.0f en dinero)", pagoFichas2, dinero2);
        } else {
            resultado = "Gana el Crupier";
        }
        datosGanador[0] = resultado;
        ganadorRonda.put(contadorRondas, resultado);
    }

    private double calcularUnidades(int valorJ, int valorC, boolean jBlackJack, boolean cBlackJack) {
        if (valorJ > 21) {
            return -1;
        }
        if (valorJ > valorC) {
            return (jBlackJack && !cBlackJack) ? 1.5 : 1;
        }
        if (valorJ == valorC) {
            return 0;
        }
        return -1;
    }

    public void darCartas(String nombrePropietarioCarta) {
        Carta cartaAleatoria = mazo.getFirst();
        if (nombrePropietarioCarta.equals("Jugador1")) {
            cartasJugador1.add(cartaAleatoria);
        } else if (nombrePropietarioCarta.equals("Jugador2")) {
            cartasJugador2.add(cartaAleatoria);
        } else if (nombrePropietarioCarta.equals("Jugador1NuevoMazo")) {
            dividirCartasJugador1.add(cartaAleatoria);
        } else if (nombrePropietarioCarta.equals("Jugador2NuevoMazo")) {
            dividirCartasJugador2.add(cartaAleatoria);
        } else if (nombrePropietarioCarta.equals("Crupier")) {
            cartasCrupier.add(cartaAleatoria);
        }
        controlGrafico.mostrarCarta(cartaAleatoria.getPalo().name(), cartaAleatoria.getDenominacion().name(), nombrePropietarioCarta);
        mazo.removeFirst();
    }

    public void dividirMazoJugador(String nombrePropietarioCarta) {
        Carta cartaAleatoria = mazo.getFirst();
        if (nombrePropietarioCarta.equals("Jugador1Division")) {
            dividirCartasJugador1.add(cartaAleatoria);
            cartasJugador1.removeLast();
        } else if (nombrePropietarioCarta.equals("Jugador2Division")) {
            dividirCartasJugador2.add(cartaAleatoria);
            cartasJugador2.removeLast();
        }
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

    public boolean verificarFichasAComprar(String cedula, int cantidadDeFichasAComprar) {
        double dinero = controlPersona.darCantidadDineroJugador(cedula);

        if (dinero >= (cantidadDeFichasAComprar * 1000)) {
            controlPersona.comprarFichas(cedula, cantidadDeFichasAComprar);
            controlGrafico.mostraDatosJugador1(darCedulaJugadoresEnPartida("Jugador1"), darCantidadFichasJugador(darCedulaJugadoresEnPartida("Jugador1")));
            controlGrafico.mostraDatosJugador2(darCedulaJugadoresEnPartida("Jugador2"), darCantidadFichasJugador(darCedulaJugadoresEnPartida("Jugador2")));
            return true;
        }
        return false;
    }

    public boolean verificarFichasAApostar(String cedula, int cantidadDeFichasAApostar) {
        double fichas = controlPersona.darCantidadFichasJugador(cedula);
        if (cantidadDeFichasAApostar <= fichas) {
            return true;
        }
        return false;
    }

    public boolean verificarCartarIguales(String jugador) {
        if ("Jugador1".equals(jugador)) {
            String carta1 = cartasJugador1.get(0).getDenominacion().name();
            String carta2 = cartasJugador1.get(1).getDenominacion().name();
            if (carta1.equals(carta2)) {
                return true;
            }
        } else if ("Jugador2".equals(jugador)) {
            String carta1 = cartasJugador2.get(0).getDenominacion().name();
            String carta2 = cartasJugador2.get(1).getDenominacion().name();
            if (carta1.equals(carta2)) {
                return true;
            }
        }
        return false;
    }

    public boolean sumarCantidadCartasJugadorActivo(String jugadorActivo) {
        int sumaCartas = 0;
        if (jugadorActivo.equals("Jugador1")) {
            sumaCartas = sumarCartas(cartasJugador1);
        } else if (jugadorActivo.equals("Jugador2")) {
            sumaCartas = sumarCartas(cartasJugador2);
        } else if (jugadorActivo.equals("Jugador1Mazo2")) {
            sumaCartas = sumarCartas(dividirCartasJugador1);
        } else if (jugadorActivo.equals("Jugador2Mazo2")) {
            sumaCartas = sumarCartas(dividirCartasJugador2);
        }
        if (sumaCartas > 21) {
            controlGrafico.mostrarMensajeError("Ya no se puede jugar mas cartas el limite ha sido exedido");
            return true;
        }
        return false;
    }

    public int sumarCartasCrupier() {
        int sumaCartas = sumarCartas(cartasCrupier);
        if (sumaCartas >= 17 && sumaCartas <21){
            controlGrafico.mostrarMensajeExito("El crupier ha decidido detenerse");
            return 1;
        }
        else if (sumaCartas > 21){
            controlGrafico.mostrarMensajeExito("El crupier se ha pasado por ende los jugadores que se han plantado han ganado esta ronda");
            return 2;
        }
        return 3;
    }

    public double darCantidadFichasJugador(String cedula) {
        return controlPersona.darCantidadFichasJugador(cedula);
    }

    public double darCantidadDineroJugador(String cedula) {
        return controlPersona.darCantidadDineroJugador(cedula);
    }

    public void conteoJugadores() {
        controlPersona.personasCon0Dinero();
        controlPersona.contarCantidadPersonas();
    }

    public void mostrarMensajeError(String mensaje) {
        controlGrafico.mostrarMensajeError(mensaje);
    }

    public void mostrarMensajeExito(String mensaje) {
        controlGrafico.mostrarMensajeExito(mensaje);
    }

    public int getContadorRondas() {
        return contadorRondas;
    }

    public void setContadorRondas(int contadorRondas) {
        this.contadorRondas = contadorRondas;
    }

    public double getFichasApostadasJugador1() {
        return fichasApostadasJugador1;
    }

    public void setFichasApostadasJugador1(double fichasApostadasJugador1) {
        this.fichasApostadasJugador1 = fichasApostadasJugador1;
    }

    public double getFichasApostadasJugador2() {
        return fichasApostadasJugador2;
    }

    public void setFichasApostadasJugador2(double fichasApostadasJugador2) {
        this.fichasApostadasJugador2 = fichasApostadasJugador2;
    }

    public String getTurnoJugador() {
        return turnoJugador;
    }

    public void setTurnoJugador(String turnoJugador) {
        this.turnoJugador = turnoJugador;
    }

}
