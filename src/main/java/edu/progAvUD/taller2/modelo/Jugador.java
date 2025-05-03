package edu.progAvUD.taller2.modelo;

/**
 * La clase Jugador representa a un participante en el juego que compite
 * contra el crupier. Extiende de Persona e incluye atributos específicos
 * como dirección, teléfono, dinero disponible y cantidad de fichas.
 *
 * @author Andres Felipe
 */
public class Jugador extends Persona {

    /**
     * Dirección de residencia del jugador.
     */
    private String direccion;

    /**
     * Número de teléfono de contacto del jugador.
     */
    private String telefono;

    /**
     * Cantidad de dinero disponible para apostar.
     */
    private double dinero;

    /**
     * Número de fichas que posee el jugador.
     */
    private double cantidadFichas;

    /**
     * Construye un Jugador con todos los datos personales y de juego.
     *
     * @param nombre         Nombre de pila.
     * @param cedula         Documento de identidad.
     * @param apellido       Apellido.
     * @param direccion      Dirección de residencia.
     * @param telefono       Número de teléfono.
     * @param dinero         Dinero disponible para apuestas.
     * @param cantidadFichas Cantidad de fichas en posesión.
     */
    public Jugador(String nombre, String cedula, String apellido,
                   String direccion, String telefono,
                   double dinero, double cantidadFichas) {
        super(nombre, cedula, apellido);
        this.direccion = direccion;
        this.telefono = telefono;
        this.dinero = dinero;
        this.cantidadFichas = cantidadFichas;
    }

    /**
     * Construye un Jugador únicamente con su cédula.
     * Útil para crear instancias parciales donde solo se requiera el identificador.
     *
     * @param cedula Documento de identidad.
     */
    public Jugador(String cedula) {
        super(cedula);
    }

    /**
     * Obtiene la dirección del jugador.
     *
     * @return Dirección de residencia.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Asigna la dirección del jugador.
     *
     * @param direccion Nueva dirección de residencia.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el teléfono de contacto.
     *
     * @return Número de teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Asigna el teléfono de contacto.
     *
     * @param telefono Nuevo número de teléfono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la cantidad de dinero disponible.
     *
     * @return Dinero para apuestas.
     */
    public double getDinero() {
        return dinero;
    }

    /**
     * Asigna la cantidad de dinero disponible.
     *
     * @param dinero Nueva cantidad de dinero.
     */
    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    /**
     * Obtiene la cantidad de fichas en posesión.
     *
     * @return Número de fichas.
     */
    public double getCantidadFichas() {
        return cantidadFichas;
    }

    /**
     * Asigna la cantidad de fichas en posesión.
     *
     * @param cantidadFichas Nueva cantidad de fichas.
     */
    public void setCantidadFichas(double cantidadFichas) {
        this.cantidadFichas = cantidadFichas;
    }
}