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

/**
 * Este control es el encargado de gestionar la partida, ademas se encarga de
 * delegar cada funcion especifica a los demas controles y de comunicarse con el
 * controlGrafico
 *
 * @author Cristianlol789
 */
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
    private double fichasAseguradasJugador1;
    private double fichasAseguradasJugador2;
    private String turnoJugador;
    private String[] datosGanador;
    private String turnoMazoJugador1;
    private String turnoMazoJugador2;

    /**
     * Este es el metodo constructor donde se crea el control, ademas se crean
     * los demas controles y se inicializan las variables a utilizar durante la
     * simulacion;
     */
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
        turnoMazoJugador1 = "";
        turnoMazoJugador2 = "";
    }

    /**
     * Aqui se crea la conexion con las propiedades para poder precargar los
     * jugadores y el crupier
     */
    public void crearConexionPropiedades() {
        try {
            conexionPropiedades = new ConexionPropiedades(controlGrafico.pedirArchivo());
        } catch (Exception ex) {
            controlGrafico.mostrarMensajeError("No se pudo crear la conexion correctamente");
            crearConexionPropiedades();
        }
    }

    /**
     * Este metodo es el encargado de pedir el archivo a la ventana para
     * serializar la persona
     *
     * @return devuelve el archivo necesario
     */
    /**
     * Este metodo es el encargado de pedir el archivo a la ventana para
     * serializar la persona
     *
     * @return devuelve el archivo necesario
     */
    public File archivoSerializado() {
        File archivo = null;
        try {
            File carpeta = controlGrafico.pedirArchivoSerializado();
            archivo = new File(carpeta, "personas.bin");
            archivo.createNewFile();
        } catch (IOException ex) {
            controlGrafico.mostrarMensajeError("No se ha podido crear el archivo");
        }
        return archivo;
    }

    /**
     * Crea el archivo aleatorio donde se guardaran los datos de la partida
     */
    public void crearArchivoAleatorio() {
        try {
            File carpetaSeleccionada = controlGrafico.pedirArchivoAleatorio();
            if (carpetaSeleccionada == null || !carpetaSeleccionada.isDirectory()) {
                controlGrafico.mostrarMensajeError("Debe seleccionar una carpeta válida.");
                return;
            }
            File archivo = new File(carpetaSeleccionada, "ArchivoAleatorio.dat");
            if (!archivo.exists()) {
                boolean creado = archivo.createNewFile();
                if (!creado) {
                    controlGrafico.mostrarMensajeError("No se pudo crear el archivo.");
                    return;
                }
            }
            archivoAleatorio = new ConexionArchivoAleatorio(archivo);
            escrituraArchivoAleatorio();
        } catch (IOException ioe) {
            controlGrafico.mostrarMensajeError("Error al crear o abrir el archivo: " + ioe.getMessage());
        }
    }

    /**
     * Escribe sobre el archivo anteriormente creado
     */
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

    /**
     * Precarga los jugadores que van ha estar en la partida
     */
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

    /**
     * Precarga el crupier desde las propiedades o desde un archivo .bin
     */
    public void cargarCrupier() {
        boolean seDeserializo = false;
        try {
            controlPersona.crearSerializacion("buscarDocumento");
            seDeserializo = controlPersona.deserializacion();
            controlPersona.cerrarArchivoSerializadoIn();
        } catch (Exception ex) {
            mostrarMensajeError("No se pudo cargar el archivo crupier serializado intente en la siguiente ejecucion");
        }
        if (!seDeserializo) {
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
                mostrarMensajeError("Algún dato del jugador no corresponde");
            }
        }
    }

    /**
     * Buscar el archivo de crupier si existe
     *
     * @return el archivo encontrado
     * @throws FileNotFoundException devuelve un error
     */
    public File buscarArchivoCrupier() throws FileNotFoundException {
        return controlGrafico.buscarArchivoCrupier();
    }

    /**
     * Crea la baraja para empezar el juego
     */
    public void crearMazo() {
        for (Carta.Palo palo : Carta.Palo.values()) {
            for (Carta.Denominacion denominacion : Carta.Denominacion.values()) {
                Carta carta = controlCarta.crearCarta(palo.name(), denominacion.name());
                mazo.add(carta);
            }
        }
        Collections.shuffle(mazo);
    }

    /**
     * Se encarga de verificar que las cedulas no sean iguales para el momento
     * de empezar cada ronda
     *
     * @param cedula identificador para diferenciar las personas
     * @return una flag que indica si estan o no repetidos
     */
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

    /**
     * Este metodo se encarga de coger las personas al azar y guardarlas para
     * empezar la ronda
     *
     * @return el arreglo con todas las cedulas guardadas
     */
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

    /**
     * Selecciona al azar dos jugadores para empezar la ronda
     */
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

    /**
     * Consigue la informacion de cada jugador para poder comenzar la partida
     */
    public void darInformacionJugadores() {
        if (contadorRondas == 1) {
            String persona1 = cedulasJugadoresEnMano.get(0);
            String persona2 = cedulasJugadoresEnMano.get(1);
            controlGrafico.mostrarMensajeExito("El jugador uno va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona1));
            controlGrafico.mostrarMensajeExito("El jugador dos va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona2));
            controlGrafico.mostraDatosJugador1(persona1, getCantidadFichasJugadorPorCedula(persona1));
            controlGrafico.mostraDatosJugador2(persona2, getCantidadFichasJugadorPorCedula(persona2));
        } else if (contadorRondas == 2) {
            String persona1 = cedulasJugadoresEnMano.get(2);
            String persona2 = cedulasJugadoresEnMano.get(3);
            controlGrafico.mostrarMensajeExito("El jugador uno va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona1));
            controlGrafico.mostrarMensajeExito("El jugador dos va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona2));
            controlGrafico.mostraDatosJugador1(persona1, getCantidadFichasJugadorPorCedula(persona1));
            controlGrafico.mostraDatosJugador2(persona2, getCantidadFichasJugadorPorCedula(persona2));
        } else {
            String persona1 = cedulasJugadoresEnMano.get(4);
            String persona2 = cedulasJugadoresEnMano.get(5);
            controlGrafico.mostrarMensajeExito("El jugador uno va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona1));
            controlGrafico.mostrarMensajeExito("El jugador dos va ha tomar el papel de: \n" + controlPersona.darInformacionPersona(persona2));
            controlGrafico.mostraDatosJugador1(persona1, getCantidadFichasJugadorPorCedula(persona1));
            controlGrafico.mostraDatosJugador2(persona2, getCantidadFichasJugadorPorCedula(persona2));
        }
    }

    /**
     * Sirve para ir haciendo cambios de los atributos de las personas y
     * identificar a cada una
     *
     * @param jugador es el parametro que nos indica quien es
     * @return la cedula de la persona
     */
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

    /**
     * Aqui se berifica quien gano y cuanto y se almacena la informacion
     */
    public void hacerPagosBlackJack() {

        double unidades1 = 0;
        double unidades2 = 0;
        double unidades1Div = 0;
        double unidades2Div = 0;

        int valorCartasCrupier = sumarCartas(cartasCrupier);
        int valorCartasJugador1 = sumarCartas(cartasJugador1);
        int valorCartasJugador2 = sumarCartas(cartasJugador2);
        int valorCartasJugador1MazoDividido = sumarCartas(dividirCartasJugador1);
        int valorCartasJugador2MazoDividido = sumarCartas(dividirCartasJugador2);

        double apuestaJugador1 = fichasApostadasJugador1;
        double apuestaJugador2 = fichasApostadasJugador2;
        double netoSeguro1 = fichasAseguradasJugador1;
        double netoSeguro2 = fichasAseguradasJugador2;

        boolean crupierBlackJack = (valorCartasCrupier == 21);
        boolean jugador1BlackJack = (valorCartasJugador1 == 21);
        boolean jugador2BlackJack = (valorCartasJugador2 == 21);
        boolean jugador1DivBlackJack = (valorCartasJugador1MazoDividido == 21);
        boolean jugador2DivBlackJack = (valorCartasJugador2MazoDividido == 21);

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

            if (valorCartasJugador1MazoDividido <= 21) {
                unidades1Div = 1;
            } else {
                unidades1Div = -1;
            }

            if (valorCartasJugador2MazoDividido <= 21) {
                unidades2Div = 1;
            } else {
                unidades2Div = -1;
            }
        } else {
            unidades1 = calcularUnidades(valorCartasJugador1, valorCartasCrupier, jugador1BlackJack, crupierBlackJack);
            unidades2 = calcularUnidades(valorCartasJugador2, valorCartasCrupier, jugador2BlackJack, crupierBlackJack);
            unidades1Div = calcularUnidades(valorCartasJugador1MazoDividido, valorCartasCrupier, jugador1DivBlackJack, crupierBlackJack);
            unidades2Div = calcularUnidades(valorCartasJugador2MazoDividido, valorCartasCrupier, jugador2DivBlackJack, crupierBlackJack);
        }

        double pagoFichas1 = unidades1 * apuestaJugador1 + netoSeguro1;
        double pagoFichas1Div = unidades1Div * apuestaJugador1;
        double pagoFichas2 = unidades2 * apuestaJugador2 + netoSeguro2;
        double pagoFichas2Div = unidades2Div * apuestaJugador2;

        double dinero1 = pagoFichas1 * 1000;
        double dinero1Div = pagoFichas1Div * 1000;
        double dinero2 = pagoFichas2 * 1000;
        double dinero2Div = pagoFichas2Div * 1000;

        StringBuilder sb = new StringBuilder();

        if (pagoFichas1 > 0) {
            sb.append(String.format("Jugador 1 gana %.2f fichas (%.0f en dinero)\n", pagoFichas1, dinero1));
        } else if (pagoFichas1 < 0) {
            sb.append(String.format("Jugador 1 pierde %.2f fichas (%.0f en dinero)\n", -pagoFichas1, -dinero1));
        } else {
            sb.append("Jugador 1 empata (push)\n");
        }

        if (valorCartasJugador1MazoDividido != 0) {
            sb.append(" └─ Split 1: ");
            if (pagoFichas1Div > 0) {
                sb.append(String.format("gana %.2f fichas (%.0f en dinero)\n", pagoFichas1Div, dinero1Div));
            } else if (pagoFichas1Div < 0) {
                sb.append(String.format("pierde %.2f fichas (%.0f en dinero)\n", -pagoFichas1Div, -dinero1Div));
            } else {
                sb.append("empata (push)\n");
            }
        }

        if (pagoFichas2 > 0) {
            sb.append(String.format("Jugador 2 gana %.2f fichas (%.0f en dinero)\n", pagoFichas2, dinero2));
        } else if (pagoFichas2 < 0) {
            sb.append(String.format("Jugador 2 pierde %.2f fichas (%.0f en dinero)\n", -pagoFichas2, -dinero2));
        } else {
            sb.append("Jugador 2 empata (push)\n");
        }

        if (valorCartasJugador2MazoDividido != 0) {
            sb.append(" └─ Split 2: ");
            if (pagoFichas2Div > 0) {
                sb.append(String.format("gana %.2f fichas (%.0f en dinero)\n", pagoFichas2Div, dinero2Div));
            } else if (pagoFichas2Div < 0) {
                sb.append(String.format("pierde %.2f fichas (%.0f en dinero)\n", -pagoFichas2Div, -dinero2Div));
            } else {
                sb.append("empata (push)\n");
            }
        }

        setTurnoJugador("Jugador1");
        String resultado = sb.toString().trim();
        datosGanador[0] = resultado;
        ganadorRonda.put(contadorRondas, resultado);
        controlGrafico.mostrarMensajeError(resultado);
        contadorRondas += 1;

        controlGrafico.limpiarEspacioInicioDeRonda();
        limpiarVariableInicioDeRonda();
        conteoJugadores();
        seleccionarJugadores();

        if (contadorRondas == 4) {
            crearArchivoAleatorio();
            controlPersona.crearSerializacion("crearDocumento");
            System.exit(0);
        }

    }

    /**
     * Se calcula el valor a pagar por conseguir cierta accion
     *
     * @param valorJugador el valor de sus cartas
     * @param valorCrupier el valor de sus cartas
     * @param jugadorBlackJack el valor de sus cartas
     * @param crupierBlackJack el valor de sus cartas
     * @return
     */
    public double calcularUnidades(int valorJugador, int valorCrupier, boolean jugadorBlackJack, boolean crupierBlackJack) {
        double unidades;

        if (jugadorBlackJack && crupierBlackJack) {
            unidades = 0;
        } else if (jugadorBlackJack) {
            unidades = 1.5;
        } else if (crupierBlackJack) {
            unidades = -1;
        } else if (valorJugador > 21) {
            unidades = -1;
        } else if (valorCrupier > 21) {
            unidades = 1;
        } else if (valorJugador > valorCrupier) {
            unidades = 1;
        } else if (valorJugador < valorCrupier) {
            unidades = -1;
        } else {
            unidades = 0;
        }

        return unidades;
    }

    /**
     * Este metodo verifica si la persona tiene fondos para doblar su apuesta y
     * solo recibe una carta
     */
    public void doblarApuesta() {
        if (turnoJugador.equals("Jugador1")) {
            darCartas(turnoJugador, turnoMazoJugador1, turnoMazoJugador2);
            sumarCantidadCartasJugadorActivo(turnoJugador);
            double fichasTotales = (controlPersona.getCantidadFichasJugadorPorCedula(turnoJugador)) - (fichasApostadasJugador1);
            String cedulaJugador = darCedulaJugadoresEnPartida(turnoJugador);
            controlPersona.setFichasJugadorPorCedula(cedulaJugador, fichasTotales);
            fichasApostadasJugador1 = fichasApostadasJugador1 * 2;
            controlGrafico.actulizarFichasApostadas(fichasApostadasJugador1, fichasApostadasJugador2);
        } else if (turnoJugador.equals("Jugador2")) {
            darCartas(turnoJugador, turnoMazoJugador1, turnoMazoJugador2);
            sumarCantidadCartasJugadorActivo(turnoJugador);
            double fichasTotales = (controlPersona.getCantidadFichasJugadorPorCedula(turnoJugador)) - (fichasApostadasJugador2);
            String cedulaJugador = darCedulaJugadoresEnPartida(turnoJugador);
            controlPersona.setFichasJugadorPorCedula(cedulaJugador, fichasTotales);
            fichasApostadasJugador2 = fichasApostadasJugador2 * 2;
            controlGrafico.actulizarFichasApostadas(fichasApostadasJugador1, fichasApostadasJugador2);
        }
    }

    /**
     * Este metodo da las cartas segun el jugador que se encuentre activo en el
     * momento
     *
     * @param nombrePropietarioCarta es a quien se le sumara la carta
     * @param turnoMazoJugador1 al mazo donde se sumara
     * @param turnoMazoJugador2 al mazo donde se sumara
     */
    public void darCartas(String nombrePropietarioCarta, String turnoMazoJugador1, String turnoMazoJugador2) {
        Carta cartaAleatoria = mazo.getFirst();
        if (nombrePropietarioCarta.equals("Jugador1")) {
            if ("".equals(turnoMazoJugador1)) {
                cartasJugador1.add(cartaAleatoria);
                controlGrafico.mostrarCarta(cartaAleatoria.getPalo().name(), cartaAleatoria.getDenominacion().name(), nombrePropietarioCarta, turnoMazoJugador1, turnoMazoJugador2);
            } else if ("Jugador1".equals(turnoMazoJugador1)) {
                cartasJugador1.add(cartaAleatoria);
                controlGrafico.mostrarCarta(cartaAleatoria.getPalo().name(), cartaAleatoria.getDenominacion().name(), nombrePropietarioCarta, turnoMazoJugador1, turnoMazoJugador2);
            } else if ("Jugador1NuevoMazo".equals(turnoMazoJugador1)) {
                dividirCartasJugador1.add(cartaAleatoria);
                controlGrafico.mostrarCarta(cartaAleatoria.getPalo().name(), cartaAleatoria.getDenominacion().name(), nombrePropietarioCarta, turnoMazoJugador1, turnoMazoJugador2);
            }
        } else if (nombrePropietarioCarta.equals("Jugador2")) {
            if ("".equals(turnoMazoJugador2)) {
                cartasJugador2.add(cartaAleatoria);
                controlGrafico.mostrarCarta(cartaAleatoria.getPalo().name(), cartaAleatoria.getDenominacion().name(), nombrePropietarioCarta, turnoMazoJugador1, turnoMazoJugador2);
            } else if ("Jugador2".equals(turnoMazoJugador2)) {
                cartasJugador2.add(cartaAleatoria);
                controlGrafico.mostrarCarta(cartaAleatoria.getPalo().name(), cartaAleatoria.getDenominacion().name(), nombrePropietarioCarta, turnoMazoJugador1, turnoMazoJugador2);
            } else if ("Jugador2NuevoMazo".equals(turnoMazoJugador2)) {
                dividirCartasJugador2.add(cartaAleatoria);
                controlGrafico.mostrarCarta(cartaAleatoria.getPalo().name(), cartaAleatoria.getDenominacion().name(), nombrePropietarioCarta, turnoMazoJugador1, turnoMazoJugador2);
            }
        } else if (nombrePropietarioCarta.equals("Crupier")) {
            cartasCrupier.add(cartaAleatoria);
            if (cartasCrupier.size() != 2) {
                controlGrafico.mostrarCarta(cartaAleatoria.getPalo().name(), cartaAleatoria.getDenominacion().name(), nombrePropietarioCarta, turnoMazoJugador1, turnoMazoJugador2);
            } else {
                controlGrafico.mostrarCartaOculta();
            }
        }

        mazo.removeFirst();
    }

    /**
     * Este metodo se encarga de dividir los mazos en caso de que la persona
     * saque el mismo valor de cartas
     *
     * @param nombrePropietarioCarta nombre del jugador que decidio dividir
     */
    public void dividirMazoJugador(String nombrePropietarioCarta) {
        if (nombrePropietarioCarta.equals("Jugador1")) {
            Carta carta1 = cartasJugador1.getFirst();
            Carta carta2 = cartasJugador1.getLast();
            Carta cartaAleatoria1 = mazo.getLast();
            mazo.removeLast();
            Carta cartaAleatoria2 = mazo.getLast();
            mazo.removeLast();
            dividirCartasJugador1.add(carta2);
            dividirCartasJugador1.add(cartaAleatoria2);
            cartasJugador1.removeLast();
            cartasJugador1.add(cartaAleatoria1);
            controlGrafico.mostrarCarta(carta1.getPalo().name(), carta1.getDenominacion().name(), "Jugador1", "Jugador1", "");
            controlGrafico.mostrarCarta(carta2.getPalo().name(), carta2.getDenominacion().name(), "Jugador1", "Jugador1NuevoMazo", "");
            controlGrafico.mostrarCarta(cartaAleatoria1.getPalo().name(), cartaAleatoria1.getDenominacion().name(), "Jugador1", "Jugador1", "");
            controlGrafico.mostrarCarta(cartaAleatoria2.getPalo().name(), cartaAleatoria2.getDenominacion().name(), "Jugador1", "Jugador1NuevoMazo", "");
        } else if (nombrePropietarioCarta.equals("Jugador2")) {
            Carta carta1 = cartasJugador2.getFirst();
            Carta carta2 = cartasJugador2.getLast();
            Carta cartaAleatoria1 = mazo.getLast();
            mazo.removeLast();
            Carta cartaAleatoria2 = mazo.getLast();
            mazo.removeLast();
            dividirCartasJugador2.add(carta2);
            dividirCartasJugador2.add(cartaAleatoria2);
            cartasJugador2.removeLast();
            cartasJugador2.add(cartaAleatoria1);
            controlGrafico.mostrarCarta(carta1.getPalo().name(), carta1.getDenominacion().name(), "Jugador2", "", "Jugador2");
            controlGrafico.mostrarCarta(carta2.getPalo().name(), carta2.getDenominacion().name(), "Jugador2", "", "Jugador2NuevoMazo");
            controlGrafico.mostrarCarta(cartaAleatoria1.getPalo().name(), cartaAleatoria1.getDenominacion().name(), "Jugador2", "", "Jugador2");
            controlGrafico.mostrarCarta(cartaAleatoria2.getPalo().name(), cartaAleatoria2.getDenominacion().name(), "Jugador2", "", "Jugador2NuevoMazo");

        }
    }

    /**
     * Este metodo suma las cartas de cada jugador y para el juego en caso de
     * pasarse de 21
     *
     * @param mano es la mano de cartas que tiene el jugador en ese momento
     * @return el valor que lleva de momento
     */
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
                case "DOS":
                    suma += 2;
                    break;
                case "TRES":
                    suma += 3;
                    break;
                case "CUATRO":
                    suma += 4;
                    break;
                case "CINCO":
                    suma += 5;
                    break;
                case "SEIS":
                    suma += 6;
                    break;
                case "SIETE":
                    suma += 7;
                    break;
                case "OCHO":
                    suma += 8;
                    break;
                case "NUEVE":
                    suma += 9;
                    break;
                case "DIEZ":
                    suma += 10;
                    break;
                default:
                    try {
                        suma += Integer.parseInt(denominacion);
                    } catch (NumberFormatException e) {
                    }
            }
        }
        while (suma > 21 && cantidadAses > 0) {
            suma -= 10;
            cantidadAses--;
        }
        return suma;
    }

    /**
     * Verifica que el usuario si tenga suficiente dienro para las compras
     *
     * @param cedula es la persona que desea comprar
     * @param cantidadDeFichasAComprar es la cantidad de fichas que quiere
     * @return devuelve si puede o no comprar la cantidad pedida
     */
    public boolean verificarFichasAComprar(String cedula, int cantidadDeFichasAComprar) {
        double dinero = controlPersona.getCantidadDineroJugadorPorCedula(cedula);

        if (dinero >= (cantidadDeFichasAComprar * 1000)) {
            controlPersona.comprarFichas(cedula, cantidadDeFichasAComprar);
            controlGrafico.mostraDatosJugador1(darCedulaJugadoresEnPartida("Jugador1"), getCantidadFichasJugadorPorCedula(darCedulaJugadoresEnPartida("Jugador1")));
            controlGrafico.mostraDatosJugador2(darCedulaJugadoresEnPartida("Jugador2"), getCantidadFichasJugadorPorCedula(darCedulaJugadoresEnPartida("Jugador2")));
            return true;
        }
        return false;
    }

    /**
     * Este metodo se encarga de verificar si tiene las suficientes fichas para
     * poder apostar en una ronda
     *
     * @param cedula es el parametro para identificar a la persona
     * @param cantidadDeFichasAApostar es el valor que desea apostar
     * @return devuelve si es posible o no apostar
     */
    public boolean verificarFichasAApostar(String cedula, int cantidadDeFichasAApostar) {
        double fichas = controlPersona.getCantidadFichasJugadorPorCedula(cedula);
        if (cantidadDeFichasAApostar <= fichas) {
            return true;
        }
        return false;
    }

    /**
     * Si las cartas son iguales da la posibilidad de dividir la mano en dos
     * barajas
     *
     * @param jugador es la persona que esta en ese turno
     * @return si es posible o no la accion
     */
    public boolean verificarCartasIguales(String jugador) {
        if (cartasJugador1.isEmpty() || cartasJugador2.isEmpty()) {
            return false;
        }

        String carta1, carta2;
        if ("Jugador1".equals(jugador)) {
            carta1 = cartasJugador1.get(0).getDenominacion().name();
            carta2 = cartasJugador1.get(1).getDenominacion().name();
        } else if ("Jugador2".equals(jugador)) {
            carta1 = cartasJugador2.get(0).getDenominacion().name();
            carta2 = cartasJugador2.get(1).getDenominacion().name();
        } else {
            return false; // Jugador inválido
        }

        int valorCarta1 = obtenerValorCarta(carta1);
        int valorCarta2 = obtenerValorCarta(carta2);

        return valorCarta1 == valorCarta2;
    }

    /**
     * Se encarga de dar el valor de la carta que fue seleccionada
     *
     * @param denominacion es el valor de la carta
     * @return devuelve el valor de la carta
     */
    public int obtenerValorCarta(String denominacion) {
        switch (denominacion) {
            case "J":
            case "Q":
            case "K":
                return 10;
            case "A":
                return 11;
            case "DOS":
                return 2;
            case "TRES":
                return 3;
            case "CUATRO":
                return 4;
            case "CINCO":
                return 5;
            case "SEIS":
                return 6;
            case "SIETE":
                return 7;
            case "OCHO":
                return 8;
            case "NUEVE":
                return 9;
            case "DIEZ":
                return 10;
            default:
                return -1;

        }
    }

    /**
     * Este metodo verifica si es posible doblar la apuesta y que jugador esta
     * pidiendo esto
     *
     * @param jugador es la persona que desidio doblar
     * @param cantidadDeFichasApostadas es la cantidad de fichas que aposto en
     * la primera ronda
     * @return si es posible hacer esto o no
     */
    public boolean verificarDoblarApueta(String jugador, double cantidadDeFichasApostadas) {
        String cedulaJugador = darCedulaJugadoresEnPartida(jugador);
        if (controlPersona.getCantidadFichasJugadorPorCedula(cedulaJugador) >= (cantidadDeFichasApostadas * 2)) {
            return true;
        }
        return false;
    }

    /**
     * Este metodo es el encargado de mostrar el boton del seguro en caso de que
     * el crupier tenga un A
     *
     * @return si deberia aparecer o no el boton
     */
    public boolean verificarBotonSeguro() {
        if (cartasCrupier.isEmpty()) {
            return false;
        }
        if ("A".equals(cartasCrupier.get(0).getDenominacion().name())) {
            return true;
        }
        return false;
    }

    /**
     * Este metodo recibe si la persona desidio pagar seguro y cuanto fue
     *
     * @param seguro es el valor pagado por el jugador
     * @return si perdio o gano con el seguo y si se pudo realizar la accion
     */
    public boolean verificarRealizarSeguro(int seguro) {
        int valorCartasCrupier = sumarCartas(cartasCrupier);
        boolean crupierBlackJack = (valorCartasCrupier == 21);
        String cedulajugador = darCedulaJugadoresEnPartida(turnoJugador);
        double fichasJugador = controlPersona.getCantidadFichasJugadorPorCedula(cedulajugador);

        if ("Jugador1".equals(turnoJugador) && seguro <= (fichasApostadasJugador1 / 2) && fichasJugador >= seguro) {
            fichasApostadasJugador1 += seguro;
            controlPersona.setFichasJugadorPorCedula(turnoJugador, controlPersona.getCantidadFichasJugadorPorCedula(turnoJugador) - seguro);
            controlGrafico.actulizarFichasApostadas(fichasApostadasJugador1, fichasApostadasJugador2);
            if (crupierBlackJack && seguro != 0) {
                fichasAseguradasJugador1 = seguro * 2;
                String cedula = darCedulaJugadoresEnPartida(turnoJugador);
                controlPersona.setFichasJugadorPorCedula(cedula, controlPersona.getCantidadFichasJugadorPorCedula(cedula) + fichasAseguradasJugador1);
            } else {
                fichasAseguradasJugador1 = -seguro;
            }
            return true;

        } else if ("Jugador2".equals(turnoJugador) && seguro <= (fichasApostadasJugador2 / 2) && fichasJugador >= seguro) {
            fichasApostadasJugador2 += seguro;
            controlPersona.setFichasJugadorPorCedula(turnoJugador, controlPersona.getCantidadFichasJugadorPorCedula(turnoJugador) - seguro);
            controlGrafico.actulizarFichasApostadas(fichasApostadasJugador1, fichasApostadasJugador2);
            if (crupierBlackJack && seguro != 0) {
                fichasAseguradasJugador2 = seguro * 2;
                String cedula = darCedulaJugadoresEnPartida(turnoJugador);
                controlPersona.setFichasJugadorPorCedula(cedula, controlPersona.getCantidadFichasJugadorPorCedula(cedula) + fichasAseguradasJugador2);
            } else {
                fichasAseguradasJugador2 = -seguro;
            }
            return true;
        }
        return false;

    }

    /**
     * Suma las cantidades de cada carta del jugador que se encuentra activo
     *
     * @param jugadorActivo es el jugador que esta jugando la ronda
     * @return si se paso o no de 21
     */
    public boolean sumarCantidadCartasJugadorActivo(String jugadorActivo) {
        int sumaCartas = 0;
        if (jugadorActivo.equals("Jugador1")) {
            if ("".equals(turnoMazoJugador1)) {
                sumaCartas = sumarCartas(cartasJugador1);
            } else if ("Jugador1".equals(turnoMazoJugador1)) {
                sumaCartas = sumarCartas(cartasJugador1);
            } else if ("Jugador1NuevoMazo".equals(turnoMazoJugador1)) {
                sumaCartas = sumarCartas(dividirCartasJugador1);
            }
        } else if (jugadorActivo.equals("Jugador2")) {
            if ("".equals(turnoMazoJugador2)) {
                sumaCartas = sumarCartas(cartasJugador2);
            } else if ("Jugador2".equals(turnoMazoJugador2)) {
                sumaCartas = sumarCartas(cartasJugador2);
            } else if ("Jugador2NuevoMazo".equals(turnoMazoJugador2)) {
                sumaCartas = sumarCartas(dividirCartasJugador2);
            }
        }
        if (sumaCartas > 21) {
            controlGrafico.mostrarMensajeError("Ya no se puede jugar mas cartas el limite ha sido exedido");
            return true;
        }
        return false;
    }

    /**
     * Este metodo da inicio a que el crupier muestre su carta y ademas coja
     * cartas hasta cumplir con las reglas del casino
     */
    public void darTurnoCrupier() {
        boolean flag = true;
        controlGrafico.mostrarCarta(cartasCrupier.get(1).getPalo().name(), cartasCrupier.get(1).getDenominacion().name(), "Crupier", "", "");
        controlGrafico.ocultarCartaOculta();
        do {
            int sumaCartas = sumarCartas(cartasCrupier);
            if (sumarCartas(cartasJugador1) > 21 && sumarCartas(cartasJugador2) > 21 && sumarCartas(dividirCartasJugador1) > 21 && sumarCartas(dividirCartasJugador2) > 21) {
                controlGrafico.mostrarMensajeExito("El crupier ha ganado ya que todos se han pasado");
                setTurnoJugador("Jugador1");
                flag = false;
            } else if (sumarCartas(cartasJugador1) > 21 && sumarCartas(cartasJugador2) > 21 && sumarCartas(dividirCartasJugador1) > 21) {
                controlGrafico.mostrarMensajeExito("El crupier ha ganado ya que todos se han pasado");
                setTurnoJugador("Jugador1");
                flag = false;
            } else if (sumarCartas(cartasJugador1) > 21 && sumarCartas(cartasJugador2) > 21 && sumarCartas(dividirCartasJugador2) > 21) {
                controlGrafico.mostrarMensajeExito("El crupier ha ganado ya que todos se han pasado");
                setTurnoJugador("Jugador1");
                flag = false;
            } else if (sumarCartas(cartasJugador1) > 21 && sumarCartas(cartasJugador2) > 21) {
                controlGrafico.mostrarMensajeExito("El crupier ha ganado ya que todos se han pasado");
                setTurnoJugador("Jugador1");
                flag = false;
            } else if (sumaCartas >= 17 && sumaCartas < 21) {
                controlGrafico.mostrarMensajeExito("El crupier ha decidido detenerse");
                setTurnoJugador("Jugador1");
                flag = false;
            } else if (sumaCartas > 21) {
                controlGrafico.mostrarMensajeExito("El crupier se ha pasado por ende los jugadores que se han plantado han ganado esta ronda");
                setTurnoJugador("Jugador1");
                flag = false;
            } else if (sumaCartas < 17) {
                darCartas("Crupier", turnoMazoJugador1, turnoMazoJugador2);
            }
        } while (flag);
        hacerPagosBlackJack();
    }

    /**
     * Limpia los campos para la siguiente ronda
     */
    public void limpiarVariableInicioDeRonda() {
        cartasJugador1.clear();
        cartasJugador2.clear();
        cartasCrupier.clear();
        dividirCartasJugador1.clear();
        dividirCartasJugador2.clear();
        fichasApostadasJugador1 = 0;
        fichasApostadasJugador2 = 0;
        fichasAseguradasJugador1 = 0;
        fichasAseguradasJugador2 = 0;
        turnoMazoJugador1 = "";
        turnoMazoJugador2 = "";
    }

    /**
     * Permite obtener la fichas de un jugador por su cedula
     *
     * @param cedula parametro identificador
     * @return la cantidad que tiene
     */
    public double getCantidadFichasJugadorPorCedula(String cedula) {
        return controlPersona.getCantidadFichasJugadorPorCedula(cedula);
    }

    /**
     * Permite saber el dinero que tiene el jugador
     *
     * @param cedula parametro para identificar la persona
     * @return cuanto tiene
     */
    public double getCantidadDineroJugadorPorCedula(String cedula) {
        return controlPersona.getCantidadDineroJugadorPorCedula(cedula);
    }

    /**
     * Cuenta si es posible empezar el juego o si falta precargar personas
     */
    public void conteoJugadores() {
        controlPersona.personasCon0Dinero();
        controlPersona.contarCantidadPersonas();
    }

    /**
     * Se encarga de comunicarle al controlgrafico de un error para que este lo
     * pase a la vista y lo muestre
     *
     * @param mensaje es el mensaje a mostrar
     */
    public void mostrarMensajeError(String mensaje) {
        controlGrafico.mostrarMensajeError(mensaje);
    }

    /**
     * Se encarga de comunicarle al controlgrafico de un error para que este lo
     * pase a la vista y lo muestre
     *
     * @param mensaje es el mensaje a mostrar
     */
    public void mostrarMensajeExito(String mensaje) {
        controlGrafico.mostrarMensajeExito(mensaje);
    }

    /**
     * Cuenta las rondas que van pasando para que sea un maximo de 3
     *
     * @return el valor que lleva hasta el momento
     */
    public int getContadorRondas() {
        return contadorRondas;
    }

    /**
     * Cambia el valor de las rondas si es necesario
     *
     * @param contadorRondas es el valor nuevo de rondas
     */
    public void setContadorRondas(int contadorRondas) {
        this.contadorRondas = contadorRondas;
    }

    /**
     * Es para saber cuantas fichas aposto el primer jugador
     *
     * @return el valor de fichas
     */
    public double getFichasApostadasJugador1() {
        return fichasApostadasJugador1;
    }

    /**
     * Cambia el valor de fichas del jugador en caso de que decida asegurar o
     * doblar
     *
     * @param fichasApostadasJugador1 la nueva cantidad de fichas
     */
    public void setFichasApostadasJugador1(double fichasApostadasJugador1) {
        this.fichasApostadasJugador1 = fichasApostadasJugador1;
    }

    /**
     * Cambias las fichas dependiendo de la cedula de la persona
     *
     * @param cedula parametro para identificar a la persona
     * @param numeroFichas cantidad nueva de fichas
     */
    public void setFichasJugadorPorCedula(String cedula, double numeroFichas) {
        controlPersona.setFichasJugadorPorCedula(cedula, numeroFichas);
    }

    /**
     * Es para saber cuantas fichas aposto el segundo jugador
     *
     * @return el valor de fichas
     */
    public double getFichasApostadasJugador2() {
        return fichasApostadasJugador2;
    }

    /**
     * Cambia el valor de fichas del jugador en caso de que decida asegurar o
     * doblar
     *
     * @param fichasApostadasJugador2 la nueva cantidad de fichas
     */
    public void setFichasApostadasJugador2(double fichasApostadasJugador2) {
        this.fichasApostadasJugador2 = fichasApostadasJugador2;
    }

    /**
     * Permite saber quien esta jugando en el momento
     *
     * @return la persona que esta jugando
     */
    public String getTurnoJugador() {
        return turnoJugador;
    }

    /**
     * Cambia la persona que juega
     *
     * @param turnoJugador persona que entro a jugar
     */
    public void setTurnoJugador(String turnoJugador) {
        this.turnoJugador = turnoJugador;
    }

    /**
     * Obtiene el identificador del mazo que tiene el turno actual del Jugador
     * 1.
     *
     * @return Una cadena que representa el turno del mazo del Jugador 1.
     */
    public String getTurnoMazoJugador1() {
        return turnoMazoJugador1;
    }

    /**
     * Establece cuál es el mazo que tiene el turno actual del Jugador 1.
     *
     * @param turnoMazoJugador1 Una cadena que representa el nuevo turno del
     * mazo del Jugador 1.
     */
    public void setTurnoMazoJugador1(String turnoMazoJugador1) {
        this.turnoMazoJugador1 = turnoMazoJugador1;
    }

    /**
     * Obtiene el identificador del mazo que tiene el turno actual del Jugador
     * 2.
     *
     * @return Una cadena que representa el turno del mazo del Jugador 2.
     */
    public String getTurnoMazoJugador2() {
        return turnoMazoJugador2;
    }

    /**
     * Establece cuál es el mazo que tiene el turno actual del Jugador 2.
     *
     * @param turnoMazoJugador2 Una cadena que representa el nuevo turno del
     * mazo del Jugador 2.
     */
    public void setTurnoMazoJugador2(String turnoMazoJugador2) {
        this.turnoMazoJugador2 = turnoMazoJugador2;
    }

}
