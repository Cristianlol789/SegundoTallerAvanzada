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
 * Controlador principal para la gestión de entidades Persona (Crupier y
 * Jugador). Se encarga de crear, buscar, serializar/deserializar y realizar
 * acciones sobre las personas.
 *
 * @author Andres Felipe
 */
public class ControlPersona {

    /**
     * Referencia al controlador principal de la aplicación para mostrar
     * mensajes y acceder a recursos.
     */
    private ControlPrincipal controlPrincipal;

    /**
     * Manejador de serialización para guardar y cargar objetos Persona desde un
     * archivo.
     */
    private Serializacion serializacion;

    /**
     * Lista interna de personas (Crupier y Jugador) gestionadas por este
     * controlador.
     */
    private ArrayList<Persona> personas;

    /**
     * Generador de números aleatorios para seleccionar jugadores al azar.
     */
    private Random random;

    /**
     * Inicializa el controlador con la referencia al ControlPrincipal y prepara
     * la lista de personas.
     *
     * @param controlPrincipal Instancia de ControlPrincipal para interacción
     * con la UI.
     */
    public ControlPersona(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.personas = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Crea una nueva Persona (Crupier o Jugador) según el identificador y
     * parámetros dados. Evita duplicados por cédula y muestra mensajes de error
     * o éxito según corresponda.
     *
     * @param identificador "Crupier" o "Jugador" para determinar el tipo.
     * @param nombre Nombre de la persona (puede quedar vacío para carga
     * parcial).
     * @param cedula Documento de identidad (obligatorio).
     * @param apellido Apellido de la persona.
     * @param telefono Teléfono de contacto (solo Jugador).
     * @param direccion Dirección de residencia (solo Jugador).
     * @param dineroDouble Dinero disponible (solo Jugador).
     * @param cantidadFichasDouble Cantidad de fichas (solo Jugador).
     */
    public void crearPersona(String identificador,
            String nombre,
            String cedula,
            String apellido,
            String telefono,
            String direccion,
            double dineroDouble,
            double cantidadFichasDouble) {
        boolean flag = true;
        // Verifica existencias por cédula
        for (Persona p : personas) {
            if (cedula.equals(p.getCedula())) {
                controlPrincipal.mostrarMensajeError(
                        "No se puede crear la persona porque ya existe alguien con esta cédula");
                flag = false;
                break;
            }
        }
        if (!flag) {
            return;
        }

        // Crear Crupier
        if ("Crupier".equals(identificador)) {
            if (cedula == null || cedula.isEmpty()) {
                controlPrincipal.mostrarMensajeError(
                        "No se puede crear el crupier ya que no tiene cédula");
            } else if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty()) {
                personas.add(new Crupier(cedula));
                controlPrincipal.mostrarMensajeExito(
                        "Se creó el crupier con solo cédula");
            } else {
                personas.add(new Crupier(nombre, cedula, apellido));
            }

            // Crear Jugador
        } else if ("Jugador".equals(identificador)) {
            if (cedula == null || cedula.isEmpty()) {
                controlPrincipal.mostrarMensajeError(
                        "No se puede crear el jugador ya que no tiene cédula");
            } else if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty()
                    || telefono == null || telefono.isEmpty() || direccion == null || direccion.isEmpty()) {
                personas.add(new Jugador(cedula));
                controlPrincipal.mostrarMensajeExito(
                        "Se creó el jugador con solo cédula");
            } else {
                personas.add(new Jugador(nombre, cedula, apellido,
                        direccion, telefono,
                        dineroDouble, cantidadFichasDouble));
            }
        }
    }

    /**
     * Verifica que haya al menos un crupier y seis jugadores antes de iniciar
     * el juego. Muestra un error y finaliza la aplicación si no se cumple.
     */
    public void contarCantidadPersonas() {
        int countC = 0, countJ = 0;
        for (Persona p : personas) {
            if (p instanceof Crupier) {
                countC++;
            } else {
                countJ++;
            }
        }
        if (countC < 1 || countJ < 6) {
            controlPrincipal.mostrarMensajeError(
                    "No hay suficientes personas para empezar el juego."
                    + " Verifique que todas tengan cédula (mínimo un crupier y 6 jugadores).");
            System.exit(0);
        }
    }

    /**
     * Prepara la serialización cargando o creando el archivo correspondiente.
     */
    public void crearPersonaSerializacion() {
        try {
            serializacion = new Serializacion(controlPrincipal.archivoSerializado());
        } catch (FileNotFoundException ex) {
            controlPrincipal.mostrarMensajeError("El archivo no ha sido encontrado");
        } catch (IOException ex) {
            controlPrincipal.mostrarMensajeError("No se pudo cargar el archivo serializado");
        }
    }

    /**
     * Cierra el stream de salida de objetos si está abierto.
     */
    public void cerrarArchivoSerializadoOut() {
        if (serializacion != null && serializacion.getSalidaSerializacion() != null) {
            try {
                serializacion.cerrarArchivoSerializadoOut();
            } catch (IOException ex) {
                controlPrincipal.mostrarMensajeError("No se puede cerrar la salida");
            }
        }
    }

    /**
     * Cierra el stream de entrada de objetos si está abierto.
     */
    public void cerrarArchivoSerializadoIn() {
        if (serializacion != null && serializacion.getEntradaSerializacion() != null) {
            try {
                serializacion.cerrarArchivoSerializadoIn();
            } catch (IOException ex) {
                controlPrincipal.mostrarMensajeError("No se puede cerrar la entrada");
            }
        }
    }

    /**
     * Serializa en el archivo solo las instancias de Crupier.
     */
    public void escribirArchivoSerializado() {
        for (Persona p : personas) {
            if (p instanceof Crupier && serializacion.getSalidaSerializacion() != null) {
                try {
                    serializacion.escribirArchivoSerializado(p);
                } catch (IOException ex) {
                    controlPrincipal.mostrarMensajeError("No se puede serializar la persona");
                }
            }
        }
    }

    /**
     * Lee y devuelve una instancia de Persona desde el archivo serializado.
     *
     * @return Persona deserializada o null si ocurre un error o fin de archivo.
     */
    public Persona leerArchivoSerializado() {
        if (serializacion == null || serializacion.getEntradaSerializacion() == null) {
            return null;
        }
        try {
            return (Persona) serializacion.leerArchivoSerializado();
        } catch (EOFException eof) {
            controlPrincipal.mostrarMensajeError("Fin de archivo inesperado al leer la persona.");
        } catch (IOException | ClassNotFoundException ex) {
            controlPrincipal.mostrarMensajeError("Error al leer el archivo serializado: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Deserializa una persona y la agrega a la lista, mostrando un mensaje de
     * éxito.
     */
    public void deserializacion() {
        Persona p = leerArchivoSerializado();
        if (p != null) {
            if (p.getNombre() == null || p.getNombre().isEmpty()
                    || p.getApellido() == null || p.getApellido().isEmpty()) {
                controlPrincipal.mostrarMensajeExito(
                        "Se ha deserializado la persona solo con cédula: " + p.getCedula());
            } else {
                controlPrincipal.mostrarMensajeExito(
                        "Se ha deserializado la persona: " + p.getNombre()
                        + " " + p.getApellido() + ", cédula: " + p.getCedula());
            }
            personas.add(p);
        }
    }

    /**
     * Elige aleatoriamente una cédula de Jugador para jugar. Si no hay Jugador,
     * retorna "Crupier".
     *
     * @return Cédula del jugador elegido o "Crupier" si no se encuentra.
     */
    public String jugadoresAleatoriosAJugar() {
        int idx = random.nextInt(personas.size());
        Persona elegido = personas.get(idx);
        return (elegido instanceof Jugador) ? elegido.getCedula() : "Crupier";
    }

    /**
     * Devuelve información formateada de la persona con la cédula dada.
     *
     * @param cedula Documento de identidad a buscar.
     * @return Texto con nombre y cédula, o solo cédula si falta información.
     */
    public String darInformacionPersona(String cedula) {
        Persona p = buscarPersonaPorCedula(cedula);
        if (p == null) {
            return null;
        }
        String nom = p.getNombre(), ape = p.getApellido();
        if (nom == null || nom.isEmpty() || ape == null || ape.isEmpty()) {
            return "La cédula del jugador es " + p.getCedula();
        }
        return "El nombre de la persona es " + nom + " " + ape + " con cédula " + p.getCedula();
    }

    /**
     * Busca y devuelve la instancia Persona con la cédula indicada.
     *
     * @param cedula Cédula a buscar.
     * @return Persona encontrada o null.
     */
    public Persona buscarPersonaPorCedula(String cedula) {
        for (Persona p : personas) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Actualiza el dinero y fichas de un jugador tras comprar fichas.
     *
     * @param cedula Cédula del jugador.
     * @param cantidadFichas Número de fichas a comprar.
     */
    public void comprarFichas(String cedula, int cantidadFichas) {
        Jugador j = (Jugador) buscarPersonaPorCedula(cedula);
        if (j == null) {
            return;
        }
        double nuevoDinero = j.getDinero() - cantidadFichas * 1000;
        j.setDinero(nuevoDinero);
        j.setCantidadFichas(cantidadFichas);
    }

    /**
     * Retorna la cantidad de fichas que posee el jugador.
     *
     * @param cedula Cédula del jugador.
     * @return Número de fichas o 0 si no existe.
     */
    public double getCantidadFichasJugadorPorCedula(String cedula) {
        Persona p = buscarPersonaPorCedula(cedula);
        return (p instanceof Jugador) ? ((Jugador) p).getCantidadFichas() : 0;
    }

    /**
     * Retorna el dinero disponible del jugador.
     *
     * @param cedula Cédula del jugador.
     * @return Dinero disponible o 0 si no existe.
     */
    public double getCantidadDineroJugadorPorCedula(String cedula) {
        Persona p = buscarPersonaPorCedula(cedula);
        return (p instanceof Jugador) ? ((Jugador) p).getDinero() : 0;
    }

    /**
     * Verifica que ningún jugador tenga menos de 1000 de dinero; si lo hay,
     * muestra error y termina.
     */
    public void personasCon0Dinero() {
        for (Persona p : personas) {
            if (p instanceof Jugador && ((Jugador) p).getDinero() < 1000) {
                controlPrincipal.mostrarMensajeError(
                        "No puede existir un jugador con menos de 1000 de dinero ya que este no podría jugar");
                System.exit(0);
            }
        }
    }

    /**
     * Cambia la cantidad de fichas de un jugador identificado por cédula.
     *
     * @param cedula Cédula del jugador.
     * @param numeroFichas Nueva cantidad de fichas.
     */
    public void setFichasJugadorPorCedula(String cedula, double numeroFichas) {
        Persona p = buscarPersonaPorCedula(cedula);
        if (p instanceof Jugador) {
            ((Jugador) p).setCantidadFichas(numeroFichas);
        }
    }
}
